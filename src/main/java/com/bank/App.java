package com.bank;

import com.bank.view.LoginFrame;

/**
 * 银行管理系统主程序入口类
 * 负责初始化应用程序并启动用户界面
 * 采用单入口点设计，通过main方法作为程序执行的起始点
 */
public class App {
    /**
     * 程序主入口方法
     * 创建应用程序上下文并启动登录界面
     * 采用事件调度线程(EDT)启动Swing界面，确保UI组件在正确的线程中创建和更新
     * @param args 命令行参数，当前版本未使用
     */
    public static void main(String[] args) {
        // 使用SwingUtilities.invokeLater确保UI在事件调度线程中初始化
        // 这是Swing多线程编程的最佳实践，避免并发访问问题
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 启动登录界面，创建LoginFrame实例并显示
                // 登录界面是用户与系统交互的第一个接触点
                new LoginFrame().setVisible(true);
            }
        });
    }
}
