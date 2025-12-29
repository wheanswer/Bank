package com.bank.view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 自定义对话框类
 * 提供三种类型的对话框：消息对话框、确认对话框、输入对话框
 * 支持自定义样式和交互效果
 */
public class CustomDialog extends JDialog {
    /** 对话框返回结果 */
    private int result = JOptionPane.CANCEL_OPTION;
    /** 输入框内容 */
    private String inputText = "";

    /**
     * 构造函数
     * @param parent 父窗口
     * @param title 对话框标题
     * @param modal 是否模态对话框
     */
    public CustomDialog(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        initUI();
    }

    /**
     * 初始化对话框UI设置
     */
    private void initUI() {
        // 设置关闭默认行为：释放资源
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // 设置对话框居中显示
        setLocationRelativeTo(getParent());
    }

    /**
     * 创建样式化按钮
     * @param text 按钮文本
     * @param bgColor 按钮背景颜色
     * @return 样式化后的JButton
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        // 设置字体为微软雅黑，加粗，大小13
        button.setFont(new Font("微软雅黑", Font.BOLD, 13));

        // 完全禁用UI管理器的影响，使用自定义样式
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        // 设置按钮背景色为鲜艳颜色
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        // 设置边框颜色比背景略深
        button.setBorder(new LineBorder(bgColor.darker(), 2));
        // 禁用焦点绘制
        button.setFocusPainted(false);
        // 设置鼠标样式为手型
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // 设置按钮首选大小
        button.setPreferredSize(new Dimension(100, 36));

        // 完全禁用UI外观，使用自定义样式
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorderPainted(true);

        // 计算悬停时的更浅颜色
        Color originalBg = bgColor;
        Border originalBorder = button.getBorder();

        // 计算更亮的颜色（RGB值增加40）
        Color lighterBg = new Color(
            Math.min(255, bgColor.getRed() + 40),
            Math.min(255, bgColor.getGreen() + 40),
            Math.min(255, bgColor.getBlue() + 40)
        );

        // 添加鼠标悬停效果
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            /**
             * 鼠标进入按钮区域
             */
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(lighterBg);
                button.setBorder(new LineBorder(lighterBg, 2));
            }

            /**
             * 鼠标离开按钮区域
             */
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalBg);
                button.setBorder(originalBorder);
            }
        });

        return button;
    }

    /**
     * 显示消息对话框
     * @param parent 父组件
     * @param message 消息内容
     * @param title 对话框标题
     * @param messageType 消息类型（INFORMATION_MESSAGE, WARNING_MESSAGE, ERROR_MESSAGE, QUESTION_MESSAGE）
     */
    public static void showMessageDialog(Component parent, String message, String title, int messageType) {
        // 创建模态对话框
        CustomDialog dialog = new CustomDialog((Frame) SwingUtilities.getWindowAncestor(parent),
                                              title, true);
        dialog.setSize(400, 200);

        // 创建主面板，使用边界布局，间距10
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        // 设置边框留白
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // 设置背景色
        contentPanel.setBackground(new Color(245, 247, 250));

        // 创建消息标签，支持HTML格式自动换行
        JLabel messageLabel = new JLabel("<html><body style='width: 300px;'>" + message + "</body></html>");
        messageLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        messageLabel.setForeground(Color.BLACK);

        // 根据消息类型获取对应图标
        JLabel iconLabel = getIconForMessageType(messageType);

        // 消息面板：图标在左，文字在右
        JPanel messagePanel = new JPanel(new BorderLayout(10, 0));
        messagePanel.setBackground(new Color(245, 247, 250));
        messagePanel.add(iconLabel, BorderLayout.WEST);
        messagePanel.add(messageLabel, BorderLayout.CENTER);

        // 按钮面板：居中对齐
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(245, 247, 250));

        // 根据消息类型获取按钮颜色
        Color buttonColor = getButtonColorForMessageType(messageType);
        // 创建确定按钮
        JButton okButton = dialog.createStyledButton("确定", buttonColor);
        // 点击确定按钮
        okButton.addActionListener(e -> {
            dialog.result = JOptionPane.OK_OPTION;
            dialog.dispose();
        });

        buttonPanel.add(okButton);

        // 添加消息面板到中央，按钮面板到南部
        contentPanel.add(messagePanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(contentPanel);
        dialog.setVisible(true);
    }

    /**
     * 显示确认对话框（是/否）
     * @param parent 父组件
     * @param message 消息内容
     * @param title 对话框标题
     * @param optionType 选项类型
     * @return 用户选择结果（JOptionPane.YES_OPTION 或 JOptionPane.NO_OPTION）
     */
    public static int showConfirmDialog(Component parent, String message, String title, int optionType) {
        // 创建模态对话框
        CustomDialog dialog = new CustomDialog((Frame) SwingUtilities.getWindowAncestor(parent),
                                              title, true);
        dialog.setSize(400, 200);

        // 创建主面板
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(245, 247, 250));

        // 消息标签
        JLabel messageLabel = new JLabel("<html><body style='width: 300px;'>" + message + "</body></html>");
        messageLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        messageLabel.setForeground(Color.BLACK);

        // 图标 - 确认对话框使用警告图标
        JLabel iconLabel = getIconForMessageType(JOptionPane.WARNING_MESSAGE);

        // 消息面板
        JPanel messagePanel = new JPanel(new BorderLayout(10, 0));
        messagePanel.setBackground(new Color(245, 247, 250));
        messagePanel.add(iconLabel, BorderLayout.WEST);
        messagePanel.add(messageLabel, BorderLayout.CENTER);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(245, 247, 250));

        // 是按钮（绿色）
        JButton yesButton = dialog.createStyledButton("是", new Color(50, 205, 50));
        // 否按钮（橙红色）
        JButton noButton = dialog.createStyledButton("否", new Color(255, 69, 0));

        // 点击是按钮
        yesButton.addActionListener(e -> {
            dialog.result = JOptionPane.YES_OPTION;
            dialog.dispose();
        });

        // 点击否按钮
        noButton.addActionListener(e -> {
            dialog.result = JOptionPane.NO_OPTION;
            dialog.dispose();
        });

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        contentPanel.add(messagePanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(contentPanel);
        dialog.setVisible(true);

        return dialog.result;
    }

    /**
     * 显示输入对话框
     * @param parent 父组件
     * @param message 提示消息
     * @param title 对话框标题
     * @return 用户输入的内容（取消返回null）
     */
    public static String showInputDialog(Component parent, String message, String title) {
        // 创建模态对话框
        CustomDialog dialog = new CustomDialog((Frame) SwingUtilities.getWindowAncestor(parent),
                                              title, true);
        dialog.setSize(450, 220);

        // 创建主面板，垂直间距15
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(245, 247, 250));

        // 消息标签
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        messageLabel.setForeground(Color.BLACK);

        // 输入框
        JTextField inputField = new JTextField(20);
        inputField.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        // 设置输入框边框样式
        inputField.setBorder(BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        inputField.setBackground(Color.WHITE);

        // 输入面板：提示信息在上，输入框在下
        JPanel inputPanel = new JPanel(new BorderLayout(0, 5));
        inputPanel.setBackground(new Color(245, 247, 250));
        inputPanel.add(messageLabel, BorderLayout.NORTH);
        inputPanel.add(inputField, BorderLayout.CENTER);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(245, 247, 250));

        // 确定按钮（天蓝色）
        JButton okButton = dialog.createStyledButton("确定", new Color(0, 191, 255));
        // 取消按钮（橙红色）
        JButton cancelButton = dialog.createStyledButton("取消", new Color(255, 69, 0));

        // 点击确定按钮
        okButton.addActionListener(e -> {
            dialog.result = JOptionPane.OK_OPTION;
            dialog.inputText = inputField.getText();
            dialog.dispose();
        });

        // 点击取消按钮
        cancelButton.addActionListener(e -> {
            dialog.result = JOptionPane.CANCEL_OPTION;
            dialog.dispose();
        });

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        contentPanel.add(inputPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(contentPanel);
        dialog.setVisible(true);

        // 返回输入的内容
        if (dialog.result == JOptionPane.OK_OPTION) {
            return dialog.inputText;
        }
        return null;
    }

    /**
     * 根据消息类型获取对应的图标
     * @param messageType 消息类型
     * @return 图标标签
     */
    private static JLabel getIconForMessageType(int messageType) {
        Icon icon = null;
        switch (messageType) {
            case JOptionPane.INFORMATION_MESSAGE:
                icon = UIManager.getIcon("OptionPane.informationIcon");
                break;
            case JOptionPane.WARNING_MESSAGE:
                icon = UIManager.getIcon("OptionPane.warningIcon");
                break;
            case JOptionPane.ERROR_MESSAGE:
                icon = UIManager.getIcon("OptionPane.errorIcon");
                break;
            default:
                icon = UIManager.getIcon("OptionPane.questionIcon");
                break;
        }

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setVerticalAlignment(SwingConstants.TOP);
        return iconLabel;
    }

    /**
     * 根据消息类型获取对应的按钮颜色
     * @param messageType 消息类型
     * @return 按钮颜色
     */
    private static Color getButtonColorForMessageType(int messageType) {
        switch (messageType) {
            case JOptionPane.INFORMATION_MESSAGE:
                return new Color(0, 191, 255); // 天蓝色
            case JOptionPane.WARNING_MESSAGE:
                return new Color(255, 215, 0); // 金黄色
            case JOptionPane.ERROR_MESSAGE:
                return new Color(255, 69, 0); // 橙红色
            default:
                return new Color(138, 43, 226); // 蓝紫色
        }
    }
}