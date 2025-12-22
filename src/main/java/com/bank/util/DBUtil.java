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
 * 数据库工具类，用于获取和关闭数据库连接
 */
public class DBUtil {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    // 静态代码块，加载配置文件
    static {
        Properties properties = new Properties();
        InputStream inputStream = null;
        
        try {
            // 尝试从类路径加载配置文件
            inputStream = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            
            if (inputStream == null) {
                System.out.println("从类路径找不到db.properties，尝试从当前目录加载...");
                // 使用绝对路径
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
            
            // 验证必需的配置项
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
            
            // 加载数据库驱动
            Class.forName(driver);
            System.out.println("数据库驱动加载成功：" + driver);
            
        } catch (IOException e) {
            System.out.println("数据库配置加载失败，使用默认配置：" + e.getMessage());
            e.printStackTrace();
            // 提供默认配置，避免类初始化失败
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
            System.out.println("数据库驱动加载失败：" + e.getMessage());
            e.printStackTrace();
            // 不抛出异常，使用默认驱动类名
            driver = "com.mysql.cj.jdbc.Driver";
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException ex) {
                System.out.println("尝试加载默认驱动类也失败：" + ex.getMessage());
                ex.printStackTrace();
            }
        } finally {
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
     * @return Connection对象，如果连接失败返回null
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
            System.out.println("数据库连接失败！");
            System.out.println("错误信息：" + e.getMessage());
            System.out.println("SQL状态：" + e.getSQLState());
            System.out.println("错误代码：" + e.getErrorCode());
            
            // 提供具体的连接问题诊断
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
            
            // 不抛出异常，返回null，由调用者处理
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭数据库资源
     * @param conn Connection对象
     * @param pstmt PreparedStatement对象
     * @param rs ResultSet对象
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
     * @param conn Connection对象
     * @param pstmt PreparedStatement对象
     */
    public static void close(Connection conn, PreparedStatement pstmt) {
        close(conn, pstmt, null);
    }
}
