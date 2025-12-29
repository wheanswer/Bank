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
 * 用户数据访问实现类
 * 使用JDBC实现UserDao接口中定义的数据库操作方法
 * 负责与MySQL数据库进行交互，执行CRUD操作
 */
public class UserDaoImpl implements UserDao {

    /**
     * 用户登录验证
     * 根据账号和密码查询用户信息
     * @param account 用户账号
     * @param password 用户密码
     * @return User 匹配的用户对象，不存在返回null
     */
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

    /**
     * 根据账号查询用户信息
     * 用于验证账号是否存在，在转账等操作前确认目标用户
     * @param account 用户账号
     * @return User 找到的用户对象，不存在返回null
     */
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

    /**
     * 注册新用户
     * 将用户信息插入数据库，初始余额为0
     * @param user 包含注册信息的用户对象
     * @return boolean 插入成功返回true，失败返回false
     */
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

    /**
     * 更新用户余额
     * 通过SQL的加减运算直接更新数据库中的余额
     * @param account 用户账号
     * @param amount 金额，正数为存款，负数为取款
     * @return boolean 更新成功返回true，失败返回false
     */
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

    /**
     * 转账操作
     * 实现两个账户之间的资金转移，使用数据库事务确保原子性
     * 事务确保：要么转出和转入都成功，要么都失败
     * @param fromAccount 转出账号
     * @param toAccount 转入账号
     * @param amount 转账金额
     * @return boolean 转账成功返回true，失败返回false
     */
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
            // 关闭自动提交，开启事务
            conn.setAutoCommit(false);

            // 执行转出操作：减少转出账号余额
            String sql1 = "UPDATE bank_user SET balance = balance - ? WHERE account = ?";
            pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setBigDecimal(1, amount);
            pstmt1.setString(2, fromAccount);
            int rows1 = pstmt1.executeUpdate();

            // 执行转入操作：增加转入账号余额
            String sql2 = "UPDATE bank_user SET balance = balance + ? WHERE account = ?";
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setBigDecimal(1, amount);
            pstmt2.setString(2, toAccount);
            int rows2 = pstmt2.executeUpdate();

            // 两笔操作都成功则提交事务，否则回滚
            if (rows1 > 0 && rows2 > 0) {
                // 提交事务使更改生效
                conn.commit();
                return true;
            } else {
                // 回滚事务撤销所有更改
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    // 发生异常时回滚事务
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (conn != null) {
                    // 恢复自动提交模式
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // 关闭数据库连接和语句对象
            DBUtil.close(conn, pstmt1);
            DBUtil.close(null, pstmt2);
        }
    }

    /**
     * 删除用户（注销账户）
     * 从数据库中永久删除指定用户的所有信息
     * @param account 要删除的用户账号
     * @return boolean 删除成功返回true，失败返回false
     */
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
