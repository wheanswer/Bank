package com.bank.view;

import com.bank.entity.Admin;
import com.bank.entity.User;
import com.bank.service.AdminService;
import com.bank.service.UserService;
import com.bank.service.impl.AdminServiceImpl;
import com.bank.service.impl.UserServiceImpl;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class LoginFrame extends JFrame {
    private UserService userService = new UserServiceImpl();
    private AdminService adminService = new AdminServiceImpl();
    
    private JTextField accountField;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JButton registerBtn;
    private JButton exitBtn;
    private JButton adminLoginBtn;
    private JTabbedPane tabbedPane;
    private JTextField adminUsernameField;
    private JPasswordField adminPasswordField;
    private JButton adminLoginBtn2;

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
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        
        accountField = createStyledTextField("请输入账号");
        passwordField = createStyledPasswordField("请输入密码");
        
        loginBtn = createStyledButton("🔑 登录", new Color(0, 191, 255));
        registerBtn = createStyledButton("📝 注册", new Color(50, 205, 50));
        exitBtn = createStyledButton("❌ 退出", new Color(255, 69, 0));
        
        adminUsernameField = createStyledTextField("请输入管理员用户名");
        adminPasswordField = createStyledPasswordField("请输入管理员密码");
        adminLoginBtn2 = createStyledButton("👤 管理员登录", new Color(128, 0, 128));
    }

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
        javax.swing.border.Border originalBorder = button.getBorder();
        
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

    private void addComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));
        
        JPanel titlePanel = createTitlePanel();
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("🏦 银行管理系统");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 118, 210));
        
        panel.add(titleLabel);
        
        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(new EmptyBorder(20, 60, 20, 60));
        
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setBackground(new Color(245, 247, 250));
        
        JLabel userTitle = new JLabel("👤 用户登录");
        userTitle.setFont(new Font("微软雅黑", Font.BOLD, 14));
        userTitle.setForeground(new Color(25, 118, 210));
        userTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        userPanel.add(userTitle);
        userPanel.add(Box.createVerticalStrut(10));
        userPanel.add(createInputRow("账号", accountField));
        userPanel.add(Box.createVerticalStrut(10));
        userPanel.add(createPasswordRow("密码", passwordField));
        userPanel.add(Box.createVerticalStrut(15));
        
        JPanel userButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        userButtonPanel.setBackground(new Color(245, 247, 250));
        userButtonPanel.add(loginBtn);
        userButtonPanel.add(registerBtn);
        userButtonPanel.add(exitBtn);
        userPanel.add(userButtonPanel);
        
        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));
        adminPanel.setBackground(new Color(245, 247, 250));
        
        JLabel adminTitle = new JLabel("🔐 管理员登录");
        adminTitle.setFont(new Font("微软雅黑", Font.BOLD, 14));
        adminTitle.setForeground(new Color(128, 0, 128));
        adminTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminPanel.add(adminTitle);
        adminPanel.add(Box.createVerticalStrut(10));
        adminPanel.add(createInputRow("用户名", adminUsernameField));
        adminPanel.add(Box.createVerticalStrut(10));
        adminPanel.add(createPasswordRow("密码", adminPasswordField));
        adminPanel.add(Box.createVerticalStrut(15));
        
        JPanel adminButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        adminButtonPanel.setBackground(new Color(245, 247, 250));
        adminButtonPanel.add(adminLoginBtn2);
        adminPanel.add(adminButtonPanel);
        
        tabbedPane.addTab("👤 用户登录", userPanel);
        tabbedPane.addTab("🔐 管理员", adminPanel);
        
        panel.add(tabbedPane);
        
        return panel;
    }

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

    private void addListeners() {
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userLogin();
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
        
        adminLoginBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminLogin();
            }
        });
    }

    private void userLogin() {
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
                if (!user.isActive()) {
                    CustomDialog.showMessageDialog(this, "账号已注销，请联系管理员！", "提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
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

    private void adminLogin() {
        String username = adminUsernameField.getText().trim();
        String password = new String(adminPasswordField.getPassword()).trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            CustomDialog.showMessageDialog(this, "用户名和密码不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            Admin admin = adminService.login(username, password);
            
            if (admin != null) {
                if (!admin.isActive()) {
                    CustomDialog.showMessageDialog(this, "管理员账号已禁用！", "提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                CustomDialog.showMessageDialog(this, "管理员登录成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new AdminFrame(admin);
            } else {
                CustomDialog.showMessageDialog(this, "用户名或密码错误！", "提示", JOptionPane.ERROR_MESSAGE);
                adminPasswordField.setText("");
            }
        } catch (Exception e) {
            CustomDialog.showMessageDialog(this, "登录失败！请检查数据库连接或配置。\n错误信息：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
