package com.bank.view;

import com.bank.entity.User;
import com.bank.service.UserService;
import com.bank.service.impl.UserServiceImpl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 登录界面
 */
public class LoginFrame extends JFrame {
    // 服务层对象
    private UserService userService = new UserServiceImpl();
    
    // 组件
    private JTextField accountField;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JButton registerBtn;
    private JButton exitBtn;

    public LoginFrame() {
        // 设置窗口标题
        setTitle("银行管理系统 - 登录");
        // 设置窗口大小
        setSize(400, 250);
        // 设置窗口居中
        setLocationRelativeTo(null);
        // 设置窗口关闭方式
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口不可调整大小
        setResizable(false);
        
        // 初始化组件
        initComponents();
        // 添加组件到窗口
        addComponents();
        // 添加事件监听器
        addListeners();
        
        // 显示窗口
        setVisible(true);
    }

    // 初始化组件
    private void initComponents() {
        accountField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginBtn = new JButton("登录");
        registerBtn = new JButton("注册");
        exitBtn = new JButton("退出");
    }

    // 添加组件到窗口
    private void addComponents() {
        // 创建面板
        JPanel panel = new JPanel();
        // 设置网格布局，5行2列，行间距10，列间距10
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // 添加组件到面板
        panel.add(new JLabel("账号："));
        panel.add(accountField);
        panel.add(new JLabel("密码："));
        panel.add(passwordField);
        panel.add(new JLabel()); // 空标签，用于占位
        panel.add(new JLabel()); // 空标签，用于占位
        panel.add(loginBtn);
        panel.add(registerBtn);
        panel.add(new JLabel()); // 空标签，用于占位
        panel.add(exitBtn);
        
        // 添加面板到窗口
        add(panel);
    }

    // 添加事件监听器
    private void addListeners() {
        // 登录按钮事件
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        
        // 注册按钮事件
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭当前窗口
                dispose();
                // 打开注册窗口
                new RegisterFrame();
            }
        });
        
        // 退出按钮事件
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    // 登录方法
    private void login() {
        // 获取账号和密码
        String account = accountField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        
        // 参数校验
        if (account.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "账号和密码不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (account.length() != 11) {
            JOptionPane.showMessageDialog(this, "账号必须是11位数字！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // 调用服务层登录方法
            User user = userService.login(account, password);
            
            if (user != null) {
                // 登录成功，显示主界面
                JOptionPane.showMessageDialog(this, "登录成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                // 关闭当前窗口
                dispose();
                // 打开主界面
                new MainFrame(user);
            } else {
                // 登录失败，显示提示信息
                JOptionPane.showMessageDialog(this, "账号或密码错误！", "提示", JOptionPane.ERROR_MESSAGE);
                // 清空密码输入框
                passwordField.setText("");
            }
        } catch (Exception e) {
            // 捕获所有异常，避免界面卡死
            JOptionPane.showMessageDialog(this, "登录失败！请检查数据库连接或配置。\n错误信息：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
