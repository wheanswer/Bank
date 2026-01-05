package com.bank.dao.impl;

import com.bank.dao.UserDao;
import com.bank.entity.User;
import com.bank.util.DBUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                return null;
            }
            String sql = "SELECT * FROM bank_user WHERE account = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, account);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = parseUser(rs);
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
            conn = DBUtil.getConnection();
            if (conn == null) {
                return null;
            }
            String sql = "SELECT * FROM bank_user WHERE account = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, account);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = parseUser(rs);
            }
        } catch (SQLException e) {
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
                return false;
            }
            String sql = "INSERT INTO bank_user (account, name, password, phone, id_card, balance, status) VALUES (?, ?, ?, ?, ?, ?, 1)";
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
                return false;
            }
            String sql = "UPDATE bank_user SET balance = balance + ? WHERE account = ? AND status = 1";
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
                return false;
            }
            conn.setAutoCommit(false);

            String sql1 = "UPDATE bank_user SET balance = balance - ? WHERE account = ? AND status = 1";
            pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setBigDecimal(1, amount);
            pstmt1.setString(2, fromAccount);
            int rows1 = pstmt1.executeUpdate();

            String sql2 = "UPDATE bank_user SET balance = balance + ? WHERE account = ? AND status = 1";
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setBigDecimal(1, amount);
            pstmt2.setString(2, toAccount);
            int rows2 = pstmt2.executeUpdate();

            if (rows1 > 0 && rows2 > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (conn != null) {
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
        return updateStatus(account, 0);
    }

    @Override
    public boolean updateStatus(String account, Integer status) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                return false;
            }
            String sql = "UPDATE bank_user SET status = ? WHERE account = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, status);
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
    public List<User> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                return users;
            }
            String sql = "SELECT * FROM bank_user ORDER BY create_time DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                users.add(parseUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return users;
    }

    @Override
    public List<User> findByStatus(Integer status) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                return users;
            }
            String sql = "SELECT * FROM bank_user WHERE status = ? ORDER BY create_time DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, status);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                users.add(parseUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return users;
    }

    private User parseUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setAccount(rs.getString("account"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setPhone(rs.getString("phone"));
        user.setIdCard(rs.getString("id_card"));
        user.setBalance(rs.getBigDecimal("balance"));
        user.setStatus(rs.getInt("status"));

        java.sql.Timestamp createTime = rs.getTimestamp("create_time");
        if (createTime != null) {
            user.setCreateTime(createTime.toLocalDateTime());
        }

        java.sql.Timestamp updateTime = rs.getTimestamp("update_time");
        if (updateTime != null) {
            user.setUpdateTime(updateTime.toLocalDateTime());
        }

        return user;
    }
}
