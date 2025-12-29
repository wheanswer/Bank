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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * 登录界面类
 * 提供银行管理系统的用户登录功能
 * 继承自JFrame，创建银行管理系统的登录窗口
 */
public class LoginFrame extends JFrame {
    /** 用户服务层对象，用于处理用户相关的业务逻辑 */
    private UserService userService = new UserServiceImpl();
    
    /** 账号输入文本框，用于用户输入登录账号 */
    private JTextField accountField;
    /** 密码输入框，用于用户输入登录密码 */
    private JPasswordField passwordField;
    /** 登录按钮，点击后执行登录操作 */
    private JButton loginBtn;
    /** 注册按钮，点击后打开注册窗口 */
    private JButton registerBtn;
    /** 退出按钮，点击后退出系统 */
    private JButton exitBtn;

    /**
     * 构造函数
     * 初始化登录窗口，设置窗口属性，创建并显示UI组件
     */
    public LoginFrame() {
        setTitle("银行管理系统 - 用户登录");
        setSize(450, 400);
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
     * 配置系统外观、颜色主题和组件样式
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
     * 初始化UI组件
     * 创建账号输入框、密码输入框和功能按钮
     */
    private void initComponents() {
        accountField = createStyledTextField("请输入账号");
        passwordField = createStyledPasswordField("请输入密码");
        
        loginBtn = createStyledButton("🔑 登录", new Color(0, 191, 255));
        registerBtn = createStyledButton("📝 注册", new Color(50, 205, 50));
        exitBtn = createStyledButton("❌ 退出", new Color(255, 69, 0));
    }

    /**
     * 创建样式化文本框
     * @param placeholder 占位符文本，提示用户输入内容
     * @return 配置好的JTextField组件
     */
    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(15);
        field.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(Color.WHITE);
        
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(70, 130, 180), 2),
                    new EmptyBorder(7, 11, 7, 11)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(200, 200, 200), 1),
                    new EmptyBorder(8, 12, 8, 12)
                ));
            }
        });
        
        return field;
    }

    /**
     * 创建样式化密码输入框
     * @param placeholder 占位符文本，提示用户输入内容
     * @return 配置好的JPasswordField组件
     */
    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(15);
        field.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(Color.WHITE);
        
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(70, 130, 180), 2),
                    new EmptyBorder(7, 11, 7, 11)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(200, 200, 200), 1),
                    new EmptyBorder(8, 12, 8, 12)
                ));
            }
        });
        
        return field;
    }

    /**
     * 创建样式化按钮
     * @param text 按钮显示文本
     * @param bgColor 按钮背景颜色
     * @return 配置好的JButton组件，包含悬停效果
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.BOLD, 13));
        
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setBorder(new LineBorder(bgColor.darker(), 2));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 38));
        
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
     * 将UI组件添加到窗口
     * 使用边界布局组织主面板、标题面板、表单面板和按钮面板
     */
    private void addComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));
        
        JPanel titlePanel = createTitlePanel();
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }

    /**
     * 创建标题面板
     * 包含银行管理系统标题和用户登录副标题
     * @return 配置好的标题面板
     */
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("🏦 银行管理系统");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 118, 210));
        
        JLabel subtitleLabel = new JLabel("用户登录");
        subtitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(97, 97, 97));
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subtitleLabel);
        
        return panel;
    }

    /**
     * 创建表单面板
     * 包含账号和密码输入字段，以及操作提示信息
     * @return 配置好的表单面板
     */
    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(new EmptyBorder(20, 60, 20, 60));
        
        panel.add(createInputRow("账号", accountField));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createPasswordRow("密码", passwordField));
        panel.add(Box.createVerticalStrut(20));
        
        JLabel hintLabel = new JLabel("💡 提示：首次使用请先注册账户");
        hintLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        hintLabel.setForeground(new Color(97, 97, 97));
        hintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(hintLabel);
        
        return panel;
    }

    /**
     * 创建输入行组件
     * @param label 标签文本，显示在输入框左侧
     * @param field 输入字段组件
     * @return 配置好的输入行面板
     */
    private JPanel createInputRow(String label, JTextField field) {
        JPanel rowPanel = new JPanel(new BorderLayout(10, 0));
        rowPanel.setBackground(new Color(245, 247, 250));
        
        JLabel labelComponent = new JLabel(label + "：");
        labelComponent.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        labelComponent.setPreferredSize(new Dimension(80, 35));
        labelComponent.setHorizontalAlignment(SwingConstants.RIGHT);
        labelComponent.setForeground(new Color(66, 66, 66));
        
        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        fieldPanel.setBackground(new Color(245, 247, 250));
        fieldPanel.add(field);
        
        rowPanel.add(labelComponent, BorderLayout.WEST);
        rowPanel.add(fieldPanel, BorderLayout.CENTER);
        
        return rowPanel;
    }

    /**
     * 创建密码输入行组件
     * @param label 标签文本，显示在输入框左侧
     * @param field 密码输入字段组件
     * @return 配置好的密码输入行面板
     */
    private JPanel createPasswordRow(String label, JPasswordField field) {
        JPanel rowPanel = new JPanel(new BorderLayout(10, 0));
        rowPanel.setBackground(new Color(245, 247, 250));
        
        JLabel labelComponent = new JLabel(label + "：");
        labelComponent.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        labelComponent.setPreferredSize(new Dimension(80, 35));
        labelComponent.setHorizontalAlignment(SwingConstants.RIGHT);
        labelComponent.setForeground(new Color(66, 66, 66));
        
        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        fieldPanel.setBackground(new Color(245, 247, 250));
        fieldPanel.add(field);
        
        rowPanel.add(labelComponent, BorderLayout.WEST);
        rowPanel.add(fieldPanel, BorderLayout.CENTER);
        
        return rowPanel;
    }

    /**
     * 创建按钮面板
     * 包含登录、注册和退出三个功能按钮
     * @return 配置好的按钮面板
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        panel.setBackground(new Color(245, 247, 250));
        
        panel.add(loginBtn);
        panel.add(registerBtn);
        panel.add(exitBtn);
        
        return panel;
    }

    /**
     * 为各个按钮添加事件监听器
     * 绑定登录、注册和退出操作到对应按钮
     */
    private void addListeners() {
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RegisterFrame();
            }
        });
        
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * 执行登录操作
     * 获取用户输入的账号和密码，调用服务层进行验证
     * 登录成功则打开主界面，失败则显示错误信息
     */
    private void login() {
        String account = accountField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        
        if (account.isEmpty() || password.isEmpty()) {
            CustomDialog.showMessageDialog(this, "账号和密码不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (account.length() != 11) {
            CustomDialog.showMessageDialog(this, "账号必须是11位数字！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            User user = userService.login(account, password);
            
            if (user != null) {
                CustomDialog.showMessageDialog(this, "登录成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new MainFrame(user);
            } else {
                CustomDialog.showMessageDialog(this, "账号或密码错误！", "提示", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        } catch (Exception e) {
            CustomDialog.showMessageDialog(this, "登录失败！请检查数据库连接或配置。\n错误信息：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
