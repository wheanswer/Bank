package com.bank.view;

import com.bank.entity.Admin;
import com.bank.entity.OperationLog;
import com.bank.entity.User;
import com.bank.service.AdminService;
import com.bank.service.OperationLogService;
import com.bank.service.UserService;
import com.bank.service.impl.AdminServiceImpl;
import com.bank.service.impl.OperationLogServiceImpl;
import com.bank.service.impl.UserServiceImpl;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics2D;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 管理员控制台界面
 * 提供管理员管理用户、查看日志等功能
 */
public class AdminFrame extends JFrame {
    /** 当前登录的管理员 */
    private final Admin currentAdmin;
    /** 管理员服务层 */
    private final AdminService adminService = new AdminServiceImpl();
    /** 用户服务层 */
    private final UserService userService = new UserServiceImpl();
    /** 日志服务层 */
    private final OperationLogService logService = new OperationLogServiceImpl();
    
    /** 用户信息表格 */
    private JTable userTable;
    /** 操作日志表格 */
    private JTable logTable;
    /** 用户表格数据模型 */
    private DefaultTableModel userTableModel;
    /** 日志表格数据模型 */
    private DefaultTableModel logTableModel;

    /**
     * 构造函数
     * @param admin 当前登录的管理员对象
     */
    public AdminFrame(Admin admin) {
        this.currentAdmin = admin;
        
        setTitle("银行管理系统 - 管理员控制台");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setupUITheme();
        
        initComponents();
        addComponents();
        addListeners();
        
        // 加载初始数据
        loadUserData();
        loadLogData();
        
        setVisible(true);
    }

    /**
     * 设置UI主题样式
     * 使用系统默认外观
     */
    private void setupUITheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化界面组件
     * 创建用户表格和日志表格
     */
    private void initComponents() {
        // 初始化用户表格模型和表格
        userTableModel = new DefaultTableModel();
        userTableModel.addColumn("账号");
        userTableModel.addColumn("姓名");
        userTableModel.addColumn("手机号");
        userTableModel.addColumn("余额");
        userTableModel.addColumn("状态");
        userTableModel.addColumn("注册时间");
        userTable = new JTable(userTableModel);
        userTable.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        userTable.setRowHeight(25);
        
        // 初始化日志表格模型和表格
        logTableModel = new DefaultTableModel();
        logTableModel.addColumn("时间");
        logTableModel.addColumn("管理员");
        logTableModel.addColumn("操作类型");
        logTableModel.addColumn("操作描述");
        logTableModel.addColumn("目标账号");
        logTableModel.addColumn("状态");
        logTable = new JTable(logTableModel);
        logTable.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        logTable.setRowHeight(25);
    }

    /**
     * 添加组件到窗口
     * 创建主面板、标题面板和Tab面板
     */
    private void addComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));
        
        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        
        JPanel userPanel = createUserPanel();
        tabbedPane.addTab("👥 用户管理", userPanel);
        
        JPanel logPanel = createLogPanel();
        tabbedPane.addTab("📋 操作日志", logPanel);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }

    /**
     * 创建顶部标题面板
     * @return 顶部面板
     */
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(25, 118, 210));
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("🏦 银行管理系统 - 管理员控制台");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.WEST);
        
        JLabel adminLabel = new JLabel("👤 管理员：" + currentAdmin.getName() + " (" + currentAdmin.getUsername() + ")");
        adminLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        adminLabel.setForeground(Color.WHITE);
        panel.add(adminLabel, BorderLayout.EAST);
        
        return panel;
    }

    /**
     * 创建用户管理面板
     * 包含用户列表和操作按钮
     * @return 用户管理面板
     */
    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(new Color(245, 247, 250));
        
        JButton refreshBtn = createButton("🔄 刷新", new Color(0, 191, 255));
        JButton lockBtn = createButton("🔒 锁定用户", new Color(255, 165, 0));
        JButton unlockBtn = createButton("🔓 解锁用户", new Color(50, 205, 50));
        JButton viewBtn = createButton("👁️ 查看详情", new Color(128, 0, 128));
        JButton logoutBtn = createButton("🚪 退出管理", new Color(255, 69, 0));
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(lockBtn);
        buttonPanel.add(unlockBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(Box.createHorizontalStrut(100));
        buttonPanel.add(logoutBtn);
        
        panel.add(buttonPanel, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setPreferredSize(new Dimension(0, 400));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        refreshBtn.addActionListener(e -> loadUserData());
        lockBtn.addActionListener(e -> lockUser());
        unlockBtn.addActionListener(e -> unlockUser());
        viewBtn.addActionListener(e -> viewUserDetail());
        logoutBtn.addActionListener(e -> logout());
        
        return panel;
    }

    /**
     * 创建操作日志面板
     * 包含日志列表和刷新按钮
     * @return 日志面板
     */
    private JPanel createLogPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(new Color(245, 247, 250));
        
        JButton refreshBtn = createButton("🔄 刷新", new Color(0, 191, 255));
        JButton clearBtn = createButton("🗑️ 清空日志", new Color(255, 69, 0));
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(clearBtn);
        
        panel.add(buttonPanel, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(logTable);
        scrollPane.setPreferredSize(new Dimension(0, 400));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        refreshBtn.addActionListener(e -> loadLogData());
        
        return panel;
    }

    /**
     * 创建带样式的按钮
     * @param text 按钮显示文字
     * @param bgColor 按钮背景颜色
     * @return 样式化的按钮
     */
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(getBackground());
                g2d.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("微软雅黑", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 30));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        Color originalBg = bgColor;
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(
                    Math.min(255, originalBg.getRed() + 30),
                    Math.min(255, originalBg.getGreen() + 30),
                    Math.min(255, originalBg.getBlue() + 30)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalBg);
            }
        });
        
        return button;
    }

    /**
     * 添加事件监听器（当前为空实现）
     */
    private void addListeners() {
    }

    /**
     * 加载用户数据到表格
     * 从数据库查询所有用户并显示
     */
    private void loadUserData() {
        userTableModel.setRowCount(0);
        List<User> users = userService.findAllUsers();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        
        for (User user : users) {
            Object[] row = new Object[6];
            row[0] = user.getAccount();
            row[1] = user.getName();
            row[2] = user.getPhone();
            row[3] = "¥" + String.format("%.2f", user.getBalance());
            row[4] = user.isActive() ? "正常" : "已锁定";
            row[5] = user.getCreateTime() != null ? user.getCreateTime().format(formatter) : "";
            userTableModel.addRow(row);
        }
    }

    /**
     * 加载操作日志数据到表格
     * 从数据库查询最近100条日志并显示
     */
    private void loadLogData() {
        logTableModel.setRowCount(0);
        List<OperationLog> logs = logService.getRecentLogs(100);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        for (OperationLog log : logs) {
            Object[] row = new Object[6];
            row[0] = log.getCreateTime() != null ? log.getCreateTime().format(formatter) : "";
            row[1] = log.getAdminName();
            row[2] = log.getOperationType();
            row[3] = log.getOperationDesc();
            row[4] = log.getTargetAccount() != null ? log.getTargetAccount() : "";
            row[5] = log.getStatus() == 1 ? "成功" : "失败";
            logTableModel.addRow(row);
        }
    }

    /**
     * 锁定选中用户
     * 将用户状态设为0（锁定），并记录操作日志
     */
    private void lockUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow < 0) {
            CustomDialog.showMessageDialog(this, "请先选择一个用户！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String account = (String) userTableModel.getValueAt(selectedRow, 0);
        String status = (String) userTableModel.getValueAt(selectedRow, 4);
        
        if ("已锁定".equals(status)) {
            CustomDialog.showMessageDialog(this, "该用户已经被锁定！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int result = CustomDialog.showConfirmDialog(this, "确定要锁定用户 " + account + " 吗？", "确认", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            boolean success = userService.lockAccount(account);
            if (success) {
                logService.logOperation(currentAdmin.getId(), currentAdmin.getName(), 
                    "LOCK_USER", "锁定用户账号", account);
                CustomDialog.showMessageDialog(this, "锁定成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                loadUserData();
            } else {
                CustomDialog.showMessageDialog(this, "锁定失败！", "提示", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * 解锁选中用户
     * 将用户状态设为1（正常），并记录操作日志
     */
    private void unlockUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow < 0) {
            CustomDialog.showMessageDialog(this, "请先选择一个用户！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String account = (String) userTableModel.getValueAt(selectedRow, 0);
        String status = (String) userTableModel.getValueAt(selectedRow, 4);
        
        if ("正常".equals(status)) {
            CustomDialog.showMessageDialog(this, "该用户处于正常状态，无需解锁！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int result = CustomDialog.showConfirmDialog(this, "确定要解锁用户 " + account + " 吗？", "确认", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            boolean success = userService.unlockAccount(account);
            if (success) {
                logService.logOperation(currentAdmin.getId(), currentAdmin.getName(), 
                    "UNLOCK_USER", "解锁用户账号", account);
                CustomDialog.showMessageDialog(this, "解锁成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                loadUserData();
            } else {
                CustomDialog.showMessageDialog(this, "解锁失败！", "提示", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * 查看选中用户的详细信息
     * 弹出对话框显示用户完整信息
     */
    private void viewUserDetail() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow < 0) {
            CustomDialog.showMessageDialog(this, "请先选择一个用户！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String account = (String) userTableModel.getValueAt(selectedRow, 0);
        User user = userService.findByAccount(account);
        
        if (user != null) {
            String message = String.format(
                "账号：%s\n姓名：%s\n手机号：%s\n身份证号：%s\n余额：¥%.2f\n状态：%s\n注册时间：%s",
                user.getAccount(), user.getName(), user.getPhone(), user.getIdCard(),
                user.getBalance(), user.isActive() ? "正常" : "已锁定",
                user.getCreateTime() != null ? user.getCreateTime().toString() : ""
            );
            CustomDialog.showMessageDialog(this, message, "用户详情", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 退出管理员系统
     * 记录登出日志，返回登录界面
     */
    private void logout() {
        int result = CustomDialog.showConfirmDialog(this, "确定要退出管理员系统吗？", "确认", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            logService.logOperation(currentAdmin.getId(), currentAdmin.getName(), 
                "ADMIN_LOGOUT", "管理员退出系统", null);
            dispose();
            new LoginFrame();
        }
    }
}
