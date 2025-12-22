package com.bank.view;

import com.bank.entity.User;
import com.bank.service.UserService;
import com.bank.service.impl.UserServiceImpl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

/**
 * 主功能界面
 */
public class MainFrame extends JFrame {
    // 服务层对象
    private UserService userService = new UserServiceImpl();
    
    // 当前登录用户
    private User currentUser;
    
    // 组件
    private JLabel welcomeLabel;
    private JLabel balanceLabel;
    private JButton depositBtn;
    private JButton withdrawBtn;
    private JButton checkBalanceBtn;
    private JButton transferBtn;
    private JButton deleteAccountBtn;
    private JButton exitBtn;

    public MainFrame(User user) {
        this.currentUser = user;
        
        // 设置窗口标题
        setTitle("银行管理系统 - 主界面");
        // 设置窗口大小
        setSize(400, 300);
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
        welcomeLabel = new JLabel("欢迎您，" + currentUser.getName() + "！", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("宋体", Font.BOLD, 16));
        
        balanceLabel = new JLabel("当前余额：¥" + currentUser.getBalance(), SwingConstants.CENTER);
        balanceLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        
        depositBtn = new JButton("存款");
        withdrawBtn = new JButton("取款");
        checkBalanceBtn = new JButton("查看余额");
        transferBtn = new JButton("转账");
        deleteAccountBtn = new JButton("注销账户");
        exitBtn = new JButton("退出系统");
    }

    // 添加组件到窗口
    private void addComponents() {
        // 创建面板
        JPanel panel = new JPanel();
        // 设置网格布局，7行1列，行间距10
        panel.setLayout(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // 添加组件到面板
        panel.add(welcomeLabel);
        panel.add(balanceLabel);
        panel.add(depositBtn);
        panel.add(withdrawBtn);
        panel.add(checkBalanceBtn);
        panel.add(transferBtn);
        panel.add(deleteAccountBtn);
        panel.add(exitBtn);
        
        // 添加面板到窗口
        add(panel);
    }

    // 添加事件监听器
    private void addListeners() {
        // 存款按钮事件
        depositBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });
        
        // 取款按钮事件
        withdrawBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });
        
        // 查看余额按钮事件
        checkBalanceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });
        
        // 转账按钮事件
        transferBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transfer();
            }
        });
        
        // 注销账户按钮事件
        deleteAccountBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAccount();
            }
        });
        
        // 退出按钮事件
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 显示确认对话框
                int result = JOptionPane.showConfirmDialog(MainFrame.this, "确定要退出系统吗？", "提示", 
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    // 存款方法
    private void deposit() {
        String amountStr = JOptionPane.showInputDialog(this, "请输入存款金额：");
        if (amountStr == null) {
            return; // 用户取消操作
        }
        
        try {
            BigDecimal amount = new BigDecimal(amountStr);
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "存款金额必须大于0！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            boolean success = userService.deposit(currentUser.getAccount(), amount);
            if (success) {
                // 更新当前用户余额
                currentUser.setBalance(currentUser.getBalance().add(amount));
                // 更新余额显示
                balanceLabel.setText("当前余额：¥" + currentUser.getBalance());
                JOptionPane.showMessageDialog(this, "存款成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "存款失败！", "提示", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字！", "提示", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "存款失败！请检查数据库连接或配置。\n错误信息：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // 取款方法
    private void withdraw() {
        String amountStr = JOptionPane.showInputDialog(this, "请输入取款金额：");
        if (amountStr == null) {
            return; // 用户取消操作
        }
        
        try {
            BigDecimal amount = new BigDecimal(amountStr);
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "取款金额必须大于0！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (amount.compareTo(currentUser.getBalance()) > 0) {
                JOptionPane.showMessageDialog(this, "余额不足！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            boolean success = userService.withdraw(currentUser.getAccount(), amount);
            if (success) {
                // 更新当前用户余额
                currentUser.setBalance(currentUser.getBalance().subtract(amount));
                // 更新余额显示
                balanceLabel.setText("当前余额：¥" + currentUser.getBalance());
                JOptionPane.showMessageDialog(this, "取款成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "取款失败！", "提示", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字！", "提示", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "取款失败！请检查数据库连接或配置。\n错误信息：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // 查看余额方法
    private void checkBalance() {
        try {
            BigDecimal balance = userService.checkBalance(currentUser.getAccount());
            if (balance != null) {
                // 更新当前用户余额
                currentUser.setBalance(balance);
                // 更新余额显示
                balanceLabel.setText("当前余额：¥" + balance);
                JOptionPane.showMessageDialog(this, "当前余额：¥" + balance, "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "查询失败！", "提示", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "查询失败！请检查数据库连接或配置。\n错误信息：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // 转账方法
    private void transfer() {
        String toAccount = JOptionPane.showInputDialog(this, "请输入对方账号：");
        if (toAccount == null) {
            return; // 用户取消操作
        }
        
        if (toAccount.length() != 11) {
            JOptionPane.showMessageDialog(this, "对方账号必须是11位数字！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String amountStr = JOptionPane.showInputDialog(this, "请输入转账金额：");
        if (amountStr == null) {
            return; // 用户取消操作
        }
        
        try {
            BigDecimal amount = new BigDecimal(amountStr);
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "转账金额必须大于0！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (amount.compareTo(currentUser.getBalance()) > 0) {
                JOptionPane.showMessageDialog(this, "余额不足！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // 检查对方账号是否存在
            if (userService.findByAccount(toAccount) == null) {
                JOptionPane.showMessageDialog(this, "对方账号不存在！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            boolean success = userService.transfer(currentUser.getAccount(), toAccount, amount);
            if (success) {
                // 更新当前用户余额
                currentUser.setBalance(currentUser.getBalance().subtract(amount));
                // 更新余额显示
                balanceLabel.setText("当前余额：¥" + currentUser.getBalance());
                JOptionPane.showMessageDialog(this, "转账成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "转账失败！", "提示", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字！", "提示", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "转账失败！请检查数据库连接或配置。\n错误信息：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // 注销账户方法
    private void deleteAccount() {
        // 显示确认对话框
        int result = JOptionPane.showConfirmDialog(this, "确定要注销账户吗？此操作不可恢复！", "提示", 
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            try {
                boolean success = userService.deleteAccount(currentUser.getAccount());
                if (success) {
                    JOptionPane.showMessageDialog(this, "账户注销成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    // 关闭当前窗口
                    dispose();
                    // 打开登录窗口
                    new LoginFrame();
                } else {
                    JOptionPane.showMessageDialog(this, "账户注销失败！", "提示", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "注销失败！请检查数据库连接或配置。\n错误信息：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    // 根据账号查找用户（辅助方法）
    private User findByAccount(String account) {
        return userService.findByAccount(account);
    }
}