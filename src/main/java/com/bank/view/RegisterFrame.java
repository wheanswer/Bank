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
import java.math.BigDecimal;

/**
 * 注册界面类
 * 提供用户注册功能，包括姓名、账号、密码、手机号和身份证号的输入与验证
 * 继承自JFrame，创建银行管理系统的用户注册窗口
 */
public class RegisterFrame extends JFrame {
    /** 用户服务层对象，用于处理用户相关的业务逻辑 */
    private UserService userService = new UserServiceImpl();
    
    /** 姓名输入文本框 */
    private JTextField nameField;
    /** 账号输入文本框 */
    private JTextField accountField;
    /** 密码输入框 */
    private JPasswordField passwordField;
    /** 确认密码输入框 */
    private JPasswordField confirmPasswordField;
    /** 手机号输入文本框 */
    private JTextField phoneField;
    /** 身份证号输入文本框 */
    private JTextField idCardField;
    /** 生成账号按钮 */
    private JButton generateAccountBtn;
    /** 注册按钮 */
    private JButton registerBtn;
    /** 取消按钮 */
    private JButton cancelBtn;

    /**
     * 构造函数
     * 初始化注册窗口，设置窗口属性，创建并显示UI组件
     */
    public RegisterFrame() {
        setTitle("银行管理系统 - 用户注册");
        setSize(520, 700);
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
     * 创建姓名、账号、密码、确认密码、手机号、身份证号输入框
     * 创建生成账号、注册、取消按钮
     */
    private void initComponents() {
        nameField = createStyledTextField("请输入真实姓名");
        accountField = createStyledTextField("请输入账号或点击生成");
        accountField.setEditable(true);
        passwordField = createStyledPasswordField("请输入密码");
        confirmPasswordField = createStyledPasswordField("请再次输入密码");
        phoneField = createStyledTextField("请输入11位手机号");
        idCardField = createStyledTextField("请输入18位身份证号");
        
        generateAccountBtn = createStyledButton("🎯 生成账号", new Color(0, 191, 255));
        registerBtn = createStyledButton("✅ 注册账户", new Color(50, 205, 50));
        cancelBtn = createStyledButton("❌ 取消", new Color(255, 69, 0));
    }

    /**
     * 创建样式化的文本输入框
     * 设置字体、边框、背景色，并添加焦点监听器实现边框高亮效果
     * @param placeholder 占位符文本
     * @return 样式化的JTextField组件
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
     * 创建样式化的密码输入框
     * 设置字体、边框、背景色，并添加焦点监听器实现边框高亮效果
     * @param placeholder 占位符文本
     * @return 样式化的JPasswordField组件
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
     * 创建样式化的按钮
     * 设置按钮的字体、背景色、边框、焦点边框等样式
     * 添加鼠标悬停效果，鼠标进入时背景变浅，离开时恢复原色
     * @param text 按钮显示的文本
     * @param bgColor 按钮背景颜色
     * @return 样式化的JButton组件
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
     * 将所有UI组件添加到窗口中
     * 创建主面板、标题面板、表单面板和按钮面板，并按BorderLayout布局添加
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
     * 显示银行管理系统标题和用户注册副标题
     * @return 标题面板JPanel
     */
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        panel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("🏦 银行管理系统");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 118, 210));
        
        JLabel subtitleLabel = new JLabel("用户注册");
        subtitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(97, 97, 97));
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subtitleLabel);
        
        return panel;
    }

    /**
     * 创建注册须知提示面板
     * 显示用户注册的各项要求和注意事项
     * @return 注册须知面板JPanel
     */
    private JPanel createRegistrationGuidePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("📋 注册须知");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        titleLabel.setForeground(new Color(25, 118, 210));
        
        JTextArea guideText = new JTextArea();
        guideText.setText("请按要求填写以下信息：\n" +
                         "• 姓名：使用真实姓名\n" +
                         "• 账号：可手动输入或点击生成\n" +
                         "• 密码：至少6位字符\n" +
                         "• 手机号：11位数字，必须以1开头\n" +
                         "• 身份证号：18位数字，第18位可为X");
        guideText.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        guideText.setForeground(new Color(66, 66, 66));
        guideText.setBackground(new Color(240, 248, 255));
        guideText.setEditable(false);
        guideText.setLineWrap(true);
        guideText.setWrapStyleWord(true);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(guideText, BorderLayout.CENTER);
        
        return panel;
    }

    /**
     * 创建表单面板
     * 包含所有输入字段的表单，使用BoxLayout垂直排列
     * @return 表单面板JPanel
     */
    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(new EmptyBorder(20, 60, 20, 60));
        
        panel.add(createRegistrationGuidePanel());
        panel.add(Box.createVerticalStrut(20));
        
        panel.add(createInputRow("姓名", nameField));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createInputRow("账号", accountField, generateAccountBtn));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createPasswordRow("密码", passwordField));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createPasswordRow("确认密码", confirmPasswordField));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createInputRow("手机号", phoneField));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createInputRow("身份证号", idCardField));
        
        return panel;
    }

    /**
     * 创建输入行（无按钮）
     * @param label 标签文本
     * @param field 输入字段组件
     * @return 输入行面板JPanel
     */
    private JPanel createInputRow(String label, JTextField field) {
        return createInputRow(label, field, null);
    }

    /**
     * 创建输入行（带按钮）
     * 创建包含标签、输入框和可选按钮的水平布局行
     * @param label 标签文本
     * @param field 输入字段组件
     * @param button 按钮组件（可为null）
     * @return 输入行面板JPanel
     */
    private JPanel createInputRow(String label, JTextField field, JButton button) {
        JPanel rowPanel = new JPanel(new BorderLayout(10, 0));
        rowPanel.setBackground(new Color(245, 247, 250));
        
        JLabel labelComponent = new JLabel(label + "：");
        labelComponent.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        labelComponent.setPreferredSize(new Dimension(80, 35));
        labelComponent.setHorizontalAlignment(SwingConstants.RIGHT);
        labelComponent.setForeground(new Color(66, 66, 66));
        
        if (button != null) {
            JPanel fieldPanel = new JPanel(new GridBagLayout());
            fieldPanel.setBackground(new Color(245, 247, 250));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 0, 0, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            
            fieldPanel.add(field, gbc);
            
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
            fieldPanel.add(button, gbc);
            
            rowPanel.add(labelComponent, BorderLayout.WEST);
            rowPanel.add(fieldPanel, BorderLayout.CENTER);
        } else {
            JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            fieldPanel.setBackground(new Color(245, 247, 250));
            fieldPanel.add(field);
            
            rowPanel.add(labelComponent, BorderLayout.WEST);
            rowPanel.add(fieldPanel, BorderLayout.CENTER);
        }
        
        return rowPanel;
    }

    /**
     * 创建密码输入行
     * 创建包含标签和密码输入框的水平布局行
     * @param label 标签文本
     * @param field 密码输入框组件
     * @return 密码输入行面板JPanel
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
     * 包含注册按钮和取消按钮，居中对齐
     * @return 按钮面板JPanel
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panel.setBackground(new Color(245, 247, 250));
        
        panel.add(registerBtn);
        panel.add(cancelBtn);
        
        return panel;
    }

    /**
     * 添加事件监听器
     * 为生成账号按钮、注册按钮和取消按钮添加点击事件处理
     */
    private void addListeners() {
        generateAccountBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = userService.generateAccount();
                accountField.setText(account);
            }
        });
        
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
        
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginFrame();
            }
        });
    }

    /**
     * 验证手机号格式是否正确
     * 检查手机号长度、是否全为数字、是否符合中国手机号格式规则
     * @param phone 待验证的手机号字符串
     * @return 验证通过返回true，否则返回false
     */
    private boolean validatePhone(String phone) {
        if (phone == null || phone.length() != 11) {
            CustomDialog.showMessageDialog(this, "手机号必须是11位数字！", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (!phone.matches("\\d{11}")) {
            CustomDialog.showMessageDialog(this, "手机号只能包含数字！", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (!phone.matches("^1[3-9]\\d{9}$")) {
            CustomDialog.showMessageDialog(this, "手机号格式不正确！必须以13、14、15、16、17、18、19开头", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (phone.matches("^(\\d)\\1{10}$")) {
            CustomDialog.showMessageDialog(this, "手机号不能是全相同数字！", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    /**
     * 验证身份证号格式是否正确
     * 检查身份证号长度、格式、地区码、出生日期和校验位
     * @param idCard 待验证的身份证号字符串
     * @return 验证通过返回true，否则返回false
     */
    private boolean validateIdCard(String idCard) {
        if (idCard == null || idCard.length() != 18) {
            CustomDialog.showMessageDialog(this, "身份证号必须是18位！", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (!idCard.matches("\\d{17}[0-9X]")) {
            CustomDialog.showMessageDialog(this, "身份证号格式不正确！前17位必须是数字，第18位是数字或X", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        String areaCode = idCard.substring(0, 6);
        if (!isValidAreaCode(areaCode)) {
            CustomDialog.showMessageDialog(this, "身份证地区码无效！", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        String birthDate = idCard.substring(6, 14);
        if (!isValidBirthDate(birthDate)) {
            CustomDialog.showMessageDialog(this, "身份证出生日期无效！格式：YYYYMMDD", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (!isValidCheckDigit(idCard)) {
            CustomDialog.showMessageDialog(this, "身份证校验位无效！", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    /**
     * 有效的省份代码数组
     * 包含中国所有省份和直辖市的行政区划代码前两位
     */
    private static final String[] VALID_PROVINCE_CODES = {
        "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", 
        "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65"
    };
        
    /**
     * 验证身份证地区码是否有效
     * 通过检查地区码前两位是否在有效省份代码列表中来验证
     * @param areaCode 身份证地区码（前6位）
     * @return 地区码有效返回true，否则返回false
     */
    private boolean isValidAreaCode(String areaCode) {
        if (areaCode == null || areaCode.length() < 2) {
            return false;
        }
        
        String provinceCode = areaCode.substring(0, 2);
        for (String validProvince : VALID_PROVINCE_CODES) {
            if (provinceCode.equals(validProvince)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 验证身份证出生日期是否有效
     * 检查日期格式、年份范围、月份范围和日期范围的合法性
     * @param birthDate 出生日期字符串（格式：YYYYMMDD）
     * @return 日期有效返回true，否则返回false
     */
    private boolean isValidBirthDate(String birthDate) {
        if (!birthDate.matches("\\d{8}")) {
            return false;
        }
        
        int year = Integer.parseInt(birthDate.substring(0, 4));
        int month = Integer.parseInt(birthDate.substring(4, 6));
        int day = Integer.parseInt(birthDate.substring(6, 8));
        
        int currentYear = java.time.LocalDate.now().getYear();
        if (year < 1900 || year > currentYear) {
            return false;
        }
        
        if (month < 1 || month > 12) {
            return false;
        }
        
        if (day < 1 || day > 31) {
            return false;
        }
        
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        
        if (isLeapYear(year)) {
            daysInMonth[1] = 29;
        }
        
        if (day > daysInMonth[month - 1]) {
            return false;
        }
        
        return true;
    }
    
    /**
     * 判断是否为闰年
     * 闰年规则：能被4整除但不能被100整除，或能被400整除
     * @param year 年份
     * @return 闰年返回true，否则返回false
     */
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
    
    /**
     * 验证身份证校验位是否正确
     * 使用GB 11643-1999标准计算校验位并与身份证最后一位对比
     * @param idCard 身份证号字符串
     * @return 校验位正确返回true，否则返回false
     */
    private boolean isValidCheckDigit(String idCard) {
        char[] chars = idCard.toCharArray();
        int[] weights = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char[] checkCodes = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            int digit = Character.getNumericValue(chars[i]);
            sum += digit * weights[i];
        }
        
        int remainder = sum % 11;
        char expectedCheckCode = checkCodes[remainder];
        char actualCheckCode = chars[17];
        
        return expectedCheckCode == actualCheckCode;
    }
    
    /**
     * 执行用户注册操作
     * 获取所有输入字段的值，进行校验，调用服务层完成注册
     * 注册成功后显示成功提示并关闭当前窗口
     */
    private void register() {
        String name = nameField.getText().trim();
        String account = accountField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
        String phone = phoneField.getText().trim();
        String idCard = idCardField.getText().trim().toUpperCase();
        
        if (name.isEmpty() || account.isEmpty() || password.isEmpty() || 
            confirmPassword.isEmpty() || phone.isEmpty() || idCard.isEmpty()) {
            CustomDialog.showMessageDialog(this, "所有字段不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            CustomDialog.showMessageDialog(this, "两次输入的密码不一致！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validatePhone(phone)) {
            return;
        }
        
        if (!validateIdCard(idCard)) {
            return;
        }
        
        try {
            User user = new User();
            user.setName(name);
            user.setAccount(account);
            user.setPassword(password);
            user.setPhone(phone);
            user.setIdCard(idCard);
            user.setBalance(BigDecimal.ZERO);
            
            boolean success = userService.register(user);
            
            if (success) {
                CustomDialog.showMessageDialog(this, "注册成功！您的账号是：" + account, "成功", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                CustomDialog.showMessageDialog(this, "注册失败！账号可能已存在。", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            CustomDialog.showMessageDialog(this, "注册过程中发生错误：" + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
