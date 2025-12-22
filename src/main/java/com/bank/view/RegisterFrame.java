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
 * 注册界面
 */
public class RegisterFrame extends JFrame {
    // 服务层对象
    private UserService userService = new UserServiceImpl();
    
    // 组件
    private JTextField nameField;
    private JTextField accountField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField phoneField;
    private JTextField idCardField;
    private JButton generateAccountBtn;
    private JButton registerBtn;
    private JButton cancelBtn;

    public RegisterFrame() {
        // 设置窗口标题
        setTitle("银行管理系统 - 注册");
        // 设置窗口大小
        setSize(450, 350);
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
        nameField = new JTextField(15);
        accountField = new JTextField(15);
        accountField.setEditable(true); // 账号可以手动编辑
        passwordField = new JPasswordField(15);
        confirmPasswordField = new JPasswordField(15);
        phoneField = new JTextField(15);
        idCardField = new JTextField(15);
        generateAccountBtn = new JButton("生成账号");
        registerBtn = new JButton("注册");
        cancelBtn = new JButton("取消");
    }

    // 添加组件到窗口
    private void addComponents() {
        // 创建面板
        JPanel panel = new JPanel();
        // 设置网格布局，8行2列，行间距10，列间距10
        panel.setLayout(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // 添加组件到面板
        panel.add(new JLabel("姓名："));
        panel.add(nameField);
        panel.add(new JLabel("账号："));
        panel.add(accountField);
        panel.add(new JLabel()); // 空标签，用于占位
        panel.add(generateAccountBtn);
        panel.add(new JLabel("密码："));
        panel.add(passwordField);
        panel.add(new JLabel("确认密码："));
        panel.add(confirmPasswordField);
        panel.add(new JLabel("手机号："));
        panel.add(phoneField);
        panel.add(new JLabel("身份证号："));
        panel.add(idCardField);
        panel.add(registerBtn);
        panel.add(cancelBtn);
        
        // 添加面板到窗口
        add(panel);
    }

    // 添加事件监听器
    private void addListeners() {
        // 生成账号按钮事件
        generateAccountBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = userService.generateAccount();
                accountField.setText(account);
            }
        });
        
        // 注册按钮事件
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
        
        // 取消按钮事件
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭当前窗口
                dispose();
                // 打开登录窗口
                new LoginFrame();
            }
        });
    }

    // 手机号校验
    private boolean validatePhone(String phone) {
        if (phone == null || phone.length() != 11) {
            JOptionPane.showMessageDialog(this, "手机号必须是11位数字！", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // 检查是否全为数字
        if (!phone.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(this, "手机号只能包含数字！", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // 检查手机号格式（中国手机号规则）
        if (!phone.matches("^1[3-9]\\d{9}$")) {
            JOptionPane.showMessageDialog(this, "手机号格式不正确！必须以13、14、15、16、17、18、19开头", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // 检查是否全相同数字
        if (phone.matches("^(\\d)\\1{10}$")) {
            JOptionPane.showMessageDialog(this, "手机号不能是全相同数字！", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    // 身份证号校验
    private boolean validateIdCard(String idCard) {
        if (idCard == null || idCard.length() != 18) {
            JOptionPane.showMessageDialog(this, "身份证号必须是18位！", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // 检查格式：前17位数字，第18位数字或X
        if (!idCard.matches("\\d{17}[0-9X]")) {
            JOptionPane.showMessageDialog(this, "身份证号格式不正确！前17位必须是数字，第18位是数字或X", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // 地区码校验（前6位）
        String areaCode = idCard.substring(0, 6);
        if (!isValidAreaCode(areaCode)) {
            JOptionPane.showMessageDialog(this, "身份证地区码无效！", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // 出生日期校验（第7-14位）
        String birthDate = idCard.substring(6, 14);
        if (!isValidBirthDate(birthDate)) {
            JOptionPane.showMessageDialog(this, "身份证出生日期无效！格式：YYYYMMDD", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // 校验位校验
        if (!isValidCheckDigit(idCard)) {
            JOptionPane.showMessageDialog(this, "身份证校验位无效！", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    // 地区码校验
    private boolean isValidAreaCode(String areaCode) {
        // 简化的地区码校验，实际应用中应该有完整的地区码表
        String[] validAreaCodes = {
            "110000", "110100", "110101", "110102", "110105", "110106", "110107", "110108", "110109", "110111",
            "120000", "120100", "120101", "120102", "120103", "120104", "120105", "120106", "120110", "120113",
            "130000", "130100", "130101", "130102", "130104", "130105", "130107", "130121", "130123", "130124",
            "140000", "140100", "140101", "140105", "140106", "140107", "140108", "140109", "140121", "140122",
            "150000", "150100", "150101", "150102", "150103", "150104", "150121", "150122", "150123", "150124",
            "210000", "210100", "210101", "210102", "210103", "210104", "210105", "210106", "210111", "210112",
            "220000", "220100", "220101", "220102", "220103", "220104", "220105", "220106", "220112", "220113",
            "230000", "230100", "230101", "230102", "230103", "230104", "230105", "230106", "230107", "230108",
            "310000", "310100", "310101", "310104", "310105", "310106", "310107", "310109", "310110", "310112",
            "320000", "320100", "320101", "320102", "320104", "320105", "320106", "320111", "320113", "320114",
            "330000", "330100", "330101", "330102", "330103", "330104", "330105", "330106", "330108", "330109",
            "340000", "340100", "340101", "340102", "340103", "340104", "340105", "340106", "340111", "340121",
            "350000", "350100", "350101", "350102", "350103", "350104", "350105", "350111", "350112", "350113",
            "360000", "360100", "360101", "360102", "360103", "360104", "360105", "360111", "360121", "360122",
            "370000", "370100", "370101", "370102", "370103", "370104", "370105", "370112", "370113", "370114",
            "410000", "410100", "410101", "410102", "410103", "410104", "410105", "410106", "410108", "410122",
            "420000", "420100", "420101", "420102", "420103", "420104", "420105", "420106", "420107", "420112",
            "430000", "430100", "430101", "430102", "430103", "430104", "430105", "430111", "430121", "430122",
            "440000", "440100", "440101", "440103", "440104", "440105", "440106", "440111", "440112", "440113",
            "450000", "450100", "450101", "450102", "450103", "450104", "450105", "450107", "450108", "450121",
            "460000", "460100", "460101", "460102", "460103", "460104", "460105", "460106", "460107", "460108",
            "500000", "500100", "500101", "500102", "500103", "500104", "500105", "500106", "500107", "500108",
            "510000", "510100", "510101", "510103", "510104", "510105", "510106", "510107", "510108", "510112",
            "520000", "520100", "520101", "520102", "520103", "520104", "520111", "520112", "520121", "520122",
            "530000", "530100", "530101", "530102", "530103", "530104", "530111", "530112", "530113", "530114",
            "540000", "540100", "540101", "540102", "540103", "540104", "540121", "540122", "540123", "540124",
            "610000", "610100", "610101", "610102", "610103", "610104", "610111", "610112", "610113", "610114",
            "620000", "620100", "620101", "620102", "620103", "620104", "620105", "620111", "620112", "620113",
            "630000", "630100", "630101", "630102", "630103", "630104", "630105", "630106", "630107", "630108",
            "640000", "640100", "640101", "640104", "640105", "640106", "640107", "640121", "640122", "640205",
            "650000", "650100", "650101", "650102", "650103", "650104", "650105", "650106", "650107", "650108"
        };
        
        for (String validCode : validAreaCodes) {
            if (areaCode.equals(validCode)) {
                return true;
            }
        }
        return false;
    }
    
    // 出生日期校验
    private boolean isValidBirthDate(String birthDate) {
        if (!birthDate.matches("\\d{8}")) {
            return false;
        }
        
        int year = Integer.parseInt(birthDate.substring(0, 4));
        int month = Integer.parseInt(birthDate.substring(4, 6));
        int day = Integer.parseInt(birthDate.substring(6, 8));
        
        // 检查年份范围（1900-当前年份）
        int currentYear = java.time.LocalDate.now().getYear();
        if (year < 1900 || year > currentYear) {
            return false;
        }
        
        // 检查月份范围
        if (month < 1 || month > 12) {
            return false;
        }
        
        // 检查日期范围
        if (day < 1 || day > 31) {
            return false;
        }
        
        // 检查具体月份的天数
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        
        // 闰年处理
        if (isLeapYear(year)) {
            daysInMonth[1] = 29;
        }
        
        if (day > daysInMonth[month - 1]) {
            return false;
        }
        
        return true;
    }
    
    // 闰年判断
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
    
    // 身份证校验位校验
    private boolean isValidCheckDigit(String idCard) {
        char[] chars = idCard.toCharArray();
        int[] weights = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        // 校验位映射表：模0-1, 模1-0, 模2-X, 模3-9, 模4-8, 模5-7, 模6-6, 模7-5, 模8-4, 模9-3, 模10-2
        char[] checkCodes = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            int digit = Character.getNumericValue(chars[i]);
            sum += digit * weights[i];
        }
        
        int remainder = sum % 11;
        char expectedCheckCode = checkCodes[remainder];
        char actualCheckCode = chars[17];
        
        System.out.println("校验位调试 - 总和: " + sum + ", 余数: " + remainder + 
                          ", 期望: " + expectedCheckCode + ", 实际: " + actualCheckCode);
        
        return expectedCheckCode == actualCheckCode;
    }
    
    // 注册方法
    private void register() {
        // 获取注册信息
        String name = nameField.getText().trim();
        String account = accountField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
        String phone = phoneField.getText().trim();
        String idCard = idCardField.getText().trim().toUpperCase();
        
        // 参数校验
        if (name.isEmpty() || account.isEmpty() || password.isEmpty() || 
            confirmPassword.isEmpty() || phone.isEmpty() || idCard.isEmpty()) {
            JOptionPane.showMessageDialog(this, "所有字段不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "两次输入的密码不一致！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // 校验手机号
        if (!validatePhone(phone)) {
            return;
        }
        
        // 校验身份证号
        if (!validateIdCard(idCard)) {
            return;
        }
        
        // 创建用户对象
        User user = new User();
        user.setName(name);
        user.setAccount(account);
        user.setPassword(password);
        user.setPhone(phone);
        user.setIdCard(idCard);
        user.setBalance(BigDecimal.ZERO);
        
        try {
            // 调用服务层注册方法
            boolean success = userService.register(user);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "注册成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                // 关闭当前窗口
                dispose();
                // 打开登录窗口
                new LoginFrame();
            } else {
                // 检查账号是否已存在
                if (userService.findByAccount(account) != null) {
                    JOptionPane.showMessageDialog(this, "注册失败！账号已存在。", "提示", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "注册失败！请检查输入信息或数据库连接。", "提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            // 捕获所有异常，避免界面卡死
            JOptionPane.showMessageDialog(this, "注册失败！系统异常。\n错误信息：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
