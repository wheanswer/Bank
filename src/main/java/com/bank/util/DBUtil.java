package com.bank.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库工具类
 * 负责管理数据库连接的获取和释放
 * 使用单例模式加载数据库配置，提供静态方法获取数据库连接
 */
public class DBUtil {
    /** JDBC驱动程序类名 */
    private static String driver;
    /** 数据库连接URL，包含数据库地址、端口、数据库名和连接参数 */
    private static String url;
    /** 数据库用户名 */
    private static String username;
    /** 数据库密码 */
    private static String password;

    /**
     * 静态代码块
     * 在类加载时执行，用于加载数据库配置文件并初始化连接参数
     * 配置文件加载顺序：类路径resources/db.properties -> 项目目录绝对路径
     * 提供默认配置作为fallback，确保程序在配置文件缺失时也能运行
     */
    static {
        Properties properties = new Properties();
        InputStream inputStream = null;
        
        try {
            // 尝试从类路径加载配置文件
            inputStream = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            
            if (inputStream == null) {
                System.out.println("从类路径找不到db.properties，尝试从当前目录加载...");
                // 使用绝对路径加载项目中的配置文件
                String currentDir = System.getProperty("user.dir");
                String configPath = currentDir + "\\src\\main\\resources\\db.properties";
                System.out.println("尝试加载配置文件：" + configPath);
                inputStream = new FileInputStream(configPath);
            }
            
            properties.load(inputStream);
            driver = properties.getProperty("jdbc.driver");
            url = properties.getProperty("jdbc.url");
            username = properties.getProperty("jdbc.username");
            password = properties.getProperty("jdbc.password");
            
            // 验证必需的配置项，如果为空则使用默认值
            if (driver == null || driver.trim().isEmpty()) {
                driver = "com.mysql.cj.jdbc.Driver";
            }
            if (url == null || url.trim().isEmpty()) {
                url = "jdbc:mysql://localhost:3306/bank_system?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
            }
            if (username == null || username.trim().isEmpty()) {
                username = "root";
            }
            if (password == null || password.trim().isEmpty()) {
                password = "123456";
            }
            
            System.out.println("数据库配置加载成功：");
            System.out.println("driver: " + driver);
            System.out.println("url: " + url);
            System.out.println("username: " + username);
            System.out.println("password: " + "*".repeat(password.length()));
            
            // 加载数据库驱动类
            Class.forName(driver);
            System.out.println("数据库驱动加载成功：" + driver);
            
        } catch (IOException e) {
            // 配置文件加载失败，使用默认配置
            System.out.println("数据库配置加载失败，使用默认配置：" + e.getMessage());
            e.printStackTrace();
            // 提供默认配置，避免类初始化失败导致程序无法启动
            driver = "com.mysql.cj.jdbc.Driver";
            url = "jdbc:mysql://localhost:3306/bank_system?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
            username = "root";
            password = "123456";
            
            try {
                Class.forName(driver);
                System.out.println("默认配置下驱动加载成功：" + driver);
            } catch (ClassNotFoundException ex) {
                System.out.println("默认配置下驱动加载失败：" + ex.getMessage());
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            // 驱动类加载失败
            System.out.println("数据库驱动加载失败：" + e.getMessage());
            e.printStackTrace();
            // 不抛出异常，使用默认驱动类名尝试重新加载
            driver = "com.mysql.cj.jdbc.Driver";
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException ex) {
                System.out.println("尝试加载默认驱动类也失败：" + ex.getMessage());
                ex.printStackTrace();
            }
        } finally {
            // 确保输入流被关闭
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 获取数据库连接
     * 根据配置文件中的参数建立与MySQL数据库的连接
     * @return Connection 返回数据库连接对象，连接失败返回null
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // 尝试连接数据库
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("数据库连接成功：" + conn);
            System.out.println("连接URL：" + url);
            System.out.println("连接用户：" + username);
        } catch (SQLException e) {
            // 连接失败，打印详细错误信息
            System.out.println("数据库连接失败！");
            System.out.println("错误信息：" + e.getMessage());
            System.out.println("SQL状态：" + e.getSQLState());
            System.out.println("错误代码：" + e.getErrorCode());
            
            // 提供具体的连接问题诊断信息
            if (e.getErrorCode() == 1049) {
                System.out.println("诊断：数据库 'bank_system' 不存在，请先创建数据库");
            } else if (e.getErrorCode() == 1045) {
                System.out.println("诊断：用户名或密码错误，请检查数据库配置");
            } else if (e.getErrorCode() == 0) {
                System.out.println("诊断：可能是网络连接问题或数据库服务未启动");
            } else if (e.getMessage().contains("Communications link failure")) {
                System.out.println("诊断：无法连接到MySQL服务器，请检查：");
                System.out.println("1. MySQL服务是否正在运行");
                System.out.println("2. 端口3306是否被占用");
                System.out.println("3. 防火墙是否阻止了连接");
            }
            
            // 不抛出异常，返回null，由调用者决定如何处理连接失败
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭所有数据库资源
     * 依次关闭ResultSet、PreparedStatement和Connection
     * 注意：关闭顺序很重要，应该先关闭ResultSet，再关闭PreparedStatement，最后关闭Connection
     * @param conn Connection数据库连接对象，可以为null
     * @param pstmt PreparedStatement预编译语句对象，可以为null
     * @param rs ResultSet结果集对象，可以为null
     */
    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭数据库资源（无ResultSet）
     * 重载方法，用于不需要关闭ResultSet的场景
     * @param conn Connection数据库连接对象，可以为null
     * @param pstmt PreparedStatement预编译语句对象，可以为null
     */
    public static void close(Connection conn, PreparedStatement pstmt) {
        close(conn, pstmt, null);
    }
}
