package com.bank.view;

import com.bank.entity.User;
import com.bank.service.UserService;
import com.bank.service.impl.UserServiceImpl;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

/**
 * 主功能界面类
 * 提供用户登录后的个人中心功能，包括存款、取款、查询余额、转账和注销账户等操作
 * 继承自JFrame，创建银行管理系统的用户个人中心窗口
 */
public class MainFrame extends JFrame {
    /** 用户服务层对象，用于处理用户相关的业务逻辑 */
    private UserService userService = new UserServiceImpl();
    
    /** 当前登录的用户对象，存储用户信息用于各项业务操作 */
    private User currentUser;
    
    /** 欢迎标签，显示当前用户名 */
    private JLabel welcomeLabel;
    /** 余额标签，显示当前账户余额 */
    private JLabel balanceLabel;
    /** 存款按钮 */
    private JButton depositBtn;
    /** 取款按钮 */
    private JButton withdrawBtn;
    /** 查看余额按钮 */
    private JButton checkBalanceBtn;
    /** 转账按钮 */
    private JButton transferBtn;
    /** 注销账户按钮 */
    private JButton deleteAccountBtn;
    /** 退出系统按钮 */
    private JButton exitBtn;

    /**
     * 构造函数
     * @param user 当前登录的用户对象
     * 初始化主功能窗口，设置窗口属性，创建并显示UI组件
     */
    public MainFrame(User user) {
        this.currentUser = user;
        
        setTitle("银行管理系统 - 个人中心");
        setSize(500, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        setupUITheme();
        
        initComponents();
        addComponents();
        addListeners();
        
        setVisible(true);
    }

    /**
     * 设置UI主题和外观样式
     * 配置系统Look and Feel，并设置自定义颜色主题
     */
    private void setupUITheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        UIManager.put("Panel.background", new Color(245, 247, 250));
        UIManager.put("TextField.background", Color.WHITE);
        UIManager.put("Button.background", new Color(70, 130, 180));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.focus", new Color(70, 130, 180));
        UIManager.put("Button.border", new LineBorder(new Color(70, 130, 180), 1));
    }

    /**
     * 初始化所有UI组件
     * 创建欢迎标签、余额标签和所有功能按钮
     */
    private void initComponents() {
        welcomeLabel = new JLabel("👋 欢迎您，" + currentUser.getName() + "！", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(25, 118, 210));
        welcomeLabel.setBackground(new Color(240, 248, 255));
        welcomeLabel.setOpaque(true);
        welcomeLabel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        balanceLabel = new JLabel("💰 当前余额：¥" + String.format("%.2f", currentUser.getBalance()), SwingConstants.CENTER);
        balanceLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        balanceLabel.setForeground(new Color(46, 125, 50));
        balanceLabel.setBackground(new Color(240, 255, 240));
        balanceLabel.setOpaque(true);
        balanceLabel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        depositBtn = createStyledButton("💰 存款", new Color(0, 191, 255));
        withdrawBtn = createStyledButton("💸 取款", new Color(255, 165, 0));
        checkBalanceBtn = createStyledButton("👁️ 查看余额", new Color(0, 206, 209));
        transferBtn = createStyledButton("💱 转账", new Color(255, 20, 147));
        deleteAccountBtn = createStyledButton("🗑️ 注销账户", new Color(255, 69, 0));
        exitBtn = createStyledButton("❌ 退出系统", new Color(138, 43, 226));
    }

    /**
     * 创建样式化的按钮
     * 设置按钮的字体、背景色、边框、焦点边框等样式
     * 添加鼠标悬停效果，鼠标进入时背景变浅，离开时恢复原色
     * @param text 按钮显示的文本
     * @param bgColor 按钮背景颜色
     * @return 样式化的JButton组件
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.BOLD, 15));
        
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setBorder(new LineBorder(bgColor.darker(), 2));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 42));
        
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorderPainted(true);
        
        Color originalBg = bgColor;
        Border originalBorder = button.getBorder();
        
        Color lighterBg = new Color(
            Math.min(255, bgColor.getRed() + 40),
            Math.min(255, bgColor.getGreen() + 40),
            Math.min(255, bgColor.getBlue() + 40)
        );
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(lighterBg);
                button.setBorder(new LineBorder(lighterBg, 2));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalBg);
                button.setBorder(originalBorder);
            }
        });
        
        return button;
    }

    /**
     * 将所有UI组件添加到窗口中
     * 创建主面板、标题面板和功能面板，并按BorderLayout布局添加
     */
    private void addComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));
        
        JPanel titlePanel = createTitlePanel();
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        JPanel functionPanel = createFunctionPanel();
        mainPanel.add(functionPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }

    /**
     * 创建标题面板
     * 显示银行管理系统标题和个人中心副标题
     * @return 标题面板JPanel
     */
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("🏦 银行管理系统");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 118, 210));
        
        JLabel subtitleLabel = new JLabel("个人中心");
        subtitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(97, 97, 97));
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subtitleLabel);
        
        return panel;
    }

    /**
     * 创建功能面板
     * 包含欢迎标签、余额标签和功能按钮网格
     * @return 功能面板JPanel
     */
    private JPanel createFunctionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(new EmptyBorder(30, 60, 30, 60));
        
        panel.add(welcomeLabel);
        panel.add(Box.createVerticalStrut(15));
        
        panel.add(balanceLabel);
        panel.add(Box.createVerticalStrut(25));
        
        JPanel buttonGrid = new JPanel(new GridLayout(3, 2, 15, 15));
        buttonGrid.setBackground(new Color(245, 247, 250));
        
        buttonGrid.add(depositBtn);
        buttonGrid.add(withdrawBtn);
        buttonGrid.add(checkBalanceBtn);
        buttonGrid.add(transferBtn);
        buttonGrid.add(deleteAccountBtn);
        buttonGrid.add(exitBtn);
        
        panel.add(buttonGrid);
        
        return panel;
    }

    /**
     * 添加事件监听器
     * 为所有功能按钮添加点击事件处理
     */
    private void addListeners() {
        depositBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });
        
        withdrawBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });
        
        checkBalanceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });
        
        transferBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transfer();
            }
        });
        
        deleteAccountBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAccount();
            }
        });
        
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = CustomDialog.showConfirmDialog(MainFrame.this, "确定要退出系统吗？", "提示", 
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    /**
     * 执行存款操作
     * 通过输入对话框获取存款金额，验证后调用服务层完成存款
     * 成功后更新当前用户余额和界面显示
     */
    private void deposit() {
        String amountStr = CustomDialog.showInputDialog(this, "请输入存款金额：", "存款");
        if (amountStr == null) {
            return;
        }
        
        try {
            BigDecimal amount = new BigDecimal(amountStr);
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                CustomDialog.showMessageDialog(this, "存款金额必须大于0！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            boolean success = userService.deposit(currentUser.getAccount(), amount);
            if (success) {
                currentUser.setBalance(currentUser.getBalance().add(amount));
                balanceLabel.setText("当前余额：¥" + currentUser.getBalance());
                CustomDialog.showMessageDialog(this, "存款成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                CustomDialog.showMessageDialog(this, "存款失败！", "提示", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            CustomDialog.showMessageDialog(this, "请输入有效的数字！", "提示", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            CustomDialog.showMessageDialog(this, "存款失败！请检查数据库连接或配置。\n错误信息：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * 执行取款操作
     * 通过输入对话框获取取款金额，验证余额后调用服务层完成取款
     * 成功后更新当前用户余额和界面显示
     */
    private void withdraw() {
        String amountStr = CustomDialog.showInputDialog(this, "请输入取款金额：", "取款");
        if (amountStr == null) {
            return;
        }
        
        try {
            BigDecimal amount = new BigDecimal(amountStr);
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                CustomDialog.showMessageDialog(this, "取款金额必须大于0！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (amount.compareTo(currentUser.getBalance()) > 0) {
                CustomDialog.showMessageDialog(this, "余额不足！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            boolean success = userService.withdraw(currentUser.getAccount(), amount);
            if (success) {
                currentUser.setBalance(currentUser.getBalance().subtract(amount));
                balanceLabel.setText("当前余额：¥" + currentUser.getBalance());
                CustomDialog.showMessageDialog(this, "取款成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                CustomDialog.showMessageDialog(this, "取款失败！", "提示", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            CustomDialog.showMessageDialog(this, "请输入有效的数字！", "提示", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            CustomDialog.showMessageDialog(this, "取款失败！请检查数据库连接或配置。\n错误信息：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * 查询当前账户余额
     * 调用服务层查询最新余额，更新当前用户余额和界面显示
     */
    private void checkBalance() {
        try {
            BigDecimal balance = userService.checkBalance(currentUser.getAccount());
            if (balance != null) {
                currentUser.setBalance(balance);
                balanceLabel.setText("当前余额：¥" + balance);
                CustomDialog.showMessageDialog(this, "当前余额：¥" + balance, "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                CustomDialog.showMessageDialog(this, "查询失败！", "提示", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            CustomDialog.showMessageDialog(this, "查询失败！请检查数据库连接或配置。\n错误信息：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * 执行转账操作
     * 通过输入对话框获取对方账号和转账金额，验证后调用服务层完成转账
     * 成功后更新当前用户余额和界面显示
     */
    private void transfer() {
        String toAccount = CustomDialog.showInputDialog(this, "请输入对方账号：", "转账");
        if (toAccount == null) {
            return;
        }
        
        if (toAccount.length() != 11) {
            CustomDialog.showMessageDialog(this, "对方账号必须是11位数字！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String amountStr = CustomDialog.showInputDialog(this, "请输入转账金额：", "转账");
        if (amountStr == null) {
            return;
        }
        
        try {
            BigDecimal amount = new BigDecimal(amountStr);
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                CustomDialog.showMessageDialog(this, "转账金额必须大于0！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (amount.compareTo(currentUser.getBalance()) > 0) {
                CustomDialog.showMessageDialog(this, "余额不足！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (userService.findByAccount(toAccount) == null) {
                CustomDialog.showMessageDialog(this, "对方账号不存在！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            boolean success = userService.transfer(currentUser.getAccount(), toAccount, amount);
            if (success) {
                currentUser.setBalance(currentUser.getBalance().subtract(amount));
                balanceLabel.setText("当前余额：¥" + currentUser.getBalance());
                CustomDialog.showMessageDialog(this, "转账成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                CustomDialog.showMessageDialog(this, "转账失败！", "提示", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            CustomDialog.showMessageDialog(this, "请输入有效的数字！", "提示", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            CustomDialog.showMessageDialog(this, "转账失败！请检查数据库连接或配置。\n错误信息：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * 注销当前账户
     * 显示确认对话框，用户确认后调用服务层删除账户
     * 删除成功后关闭当前窗口并打开登录窗口
     */
    private void deleteAccount() {
        int result = CustomDialog.showConfirmDialog(this, "确定要注销账户吗？此操作不可恢复！", "提示", 
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                boolean success = userService.deleteAccount(currentUser.getAccount());
                if (success) {
                    CustomDialog.showMessageDialog(this, "账户注销成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new LoginFrame();
                } else {
                    CustomDialog.showMessageDialog(this, "账户注销失败！", "提示", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                CustomDialog.showMessageDialog(this, "注销失败！请检查数据库连接或配置。\n错误信息：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 根据账号查找用户
     * 辅助方法，用于验证转账时对方账号是否存在
     * @param account 待查询的账号
     * @return 找到的用户对象，未找到返回null
     */
    private User findByAccount(String account) {
        return userService.findByAccount(account);
    }
}
