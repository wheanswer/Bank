package com.bank.dao.impl;

import com.bank.dao.UserDao;
import com.bank.entity.User;
import com.bank.util.DBUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用户数据访问实现类，使用JDBC实现具体的数据库操作
 */
public class UserDaoImpl implements UserDao {

    @Override
    public User login(String account, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                System.out.println("数据库连接失败，无法执行login操作");
                return null;
            }
            String sql = "SELECT * FROM bank_user WHERE account = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, account);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setAccount(rs.getString("account"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("phone"));
                user.setIdCard(rs.getString("id_card"));
                user.setBalance(rs.getBigDecimal("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return user;
    }

    @Override
    public User findByAccount(String account) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            System.out.println("执行findByAccount操作：" + account);
            conn = DBUtil.getConnection();
            if (conn == null) {
                System.out.println("数据库连接失败，无法执行findByAccount操作");
                return null;
            }
            String sql = "SELECT * FROM bank_user WHERE account = ?";
            System.out.println("执行SQL：" + sql + "，参数：" + account);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, account);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setAccount(rs.getString("account"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("phone"));
                user.setIdCard(rs.getString("id_card"));
                user.setBalance(rs.getBigDecimal("balance"));
                System.out.println("找到账号：" + user.toString());
            } else {
                System.out.println("未找到账号：" + account);
            }
        } catch (SQLException e) {
            System.out.println("执行findByAccount操作失败：" + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return user;
    }

    @Override
    public boolean register(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                System.out.println("数据库连接失败，无法执行register操作");
                return false;
            }
            String sql = "INSERT INTO bank_user (account, name, password, phone, id_card, balance) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getAccount());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getPhone());
            pstmt.setString(5, user.getIdCard());
            pstmt.setBigDecimal(6, user.getBalance());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }

    @Override
    public boolean updateBalance(String account, BigDecimal amount) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                System.out.println("数据库连接失败，无法执行updateBalance操作");
                return false;
            }
            String sql = "UPDATE bank_user SET balance = balance + ? WHERE account = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setBigDecimal(1, amount);
            pstmt.setString(2, account);

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }

    @Override
    public boolean transfer(String fromAccount, String toAccount, BigDecimal amount) {
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                System.out.println("数据库连接失败，无法执行transfer操作");
                return false;
            }
            // 开启事务
            conn.setAutoCommit(false);

            // 转出操作
            String sql1 = "UPDATE bank_user SET balance = balance - ? WHERE account = ?";
            pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setBigDecimal(1, amount);
            pstmt1.setString(2, fromAccount);
            int rows1 = pstmt1.executeUpdate();

            // 转入操作
            String sql2 = "UPDATE bank_user SET balance = balance + ? WHERE account = ?";
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setBigDecimal(1, amount);
            pstmt2.setString(2, toAccount);
            int rows2 = pstmt2.executeUpdate();

            if (rows1 > 0 && rows2 > 0) {
                // 提交事务
                conn.commit();
                return true;
            } else {
                // 回滚事务
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    // 回滚事务
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (conn != null) {
                    // 恢复自动提交
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBUtil.close(conn, pstmt1);
            DBUtil.close(null, pstmt2);
        }
    }

    @Override
    public boolean deleteUser(String account) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                System.out.println("数据库连接失败，无法执行deleteUser操作");
                return false;
            }
            String sql = "DELETE FROM bank_user WHERE account = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, account);

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }
}
