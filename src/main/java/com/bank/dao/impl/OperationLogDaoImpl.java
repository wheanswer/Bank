package com.bank.dao.impl;

import com.bank.dao.OperationLogDao;
import com.bank.entity.OperationLog;
import com.bank.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OperationLogDaoImpl implements OperationLogDao {

    @Override
    public boolean insert(OperationLog log) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                return false;
            }
            String sql = "INSERT INTO bank_operation_log (admin_id, admin_name, operation_type, operation_desc, target_account, ip_address, status, error_message) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, log.getAdminId());
            pstmt.setString(2, log.getAdminName());
            pstmt.setString(3, log.getOperationType());
            pstmt.setString(4, log.getOperationDesc());
            pstmt.setString(5, log.getTargetAccount());
            pstmt.setString(6, log.getIpAddress());
            pstmt.setInt(7, log.getStatus());
            pstmt.setString(8, log.getErrorMessage());

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
    public List<OperationLog> findByAdminId(Integer adminId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<OperationLog> logs = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                return logs;
            }
            String sql = "SELECT * FROM bank_operation_log WHERE admin_id = ? ORDER BY create_time DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, adminId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                logs.add(parseLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return logs;
    }

    @Override
    public List<OperationLog> findByType(String operationType) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<OperationLog> logs = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                return logs;
            }
            String sql = "SELECT * FROM bank_operation_log WHERE operation_type = ? ORDER BY create_time DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, operationType);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                logs.add(parseLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return logs;
    }

    @Override
    public List<OperationLog> findByAccount(String account) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<OperationLog> logs = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                return logs;
            }
            String sql = "SELECT * FROM bank_operation_log WHERE target_account = ? ORDER BY create_time DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, account);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                logs.add(parseLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return logs;
    }

    @Override
    public List<OperationLog> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<OperationLog> logs = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                return logs;
            }
            String sql = "SELECT * FROM bank_operation_log ORDER BY create_time DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                logs.add(parseLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return logs;
    }

    @Override
    public List<OperationLog> findRecent(int limit) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<OperationLog> logs = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                return logs;
            }
            String sql = "SELECT * FROM bank_operation_log ORDER BY create_time DESC LIMIT ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, limit);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                logs.add(parseLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return logs;
    }

    private OperationLog parseLog(ResultSet rs) throws SQLException {
        OperationLog log = new OperationLog();
        log.setId(rs.getLong("id"));
        log.setAdminId(rs.getInt("admin_id"));
        log.setAdminName(rs.getString("admin_name"));
        log.setOperationType(rs.getString("operation_type"));
        log.setOperationDesc(rs.getString("operation_desc"));
        log.setTargetAccount(rs.getString("target_account"));
        log.setIpAddress(rs.getString("ip_address"));
        log.setStatus(rs.getInt("status"));
        log.setErrorMessage(rs.getString("error_message"));

        java.sql.Timestamp timestamp = rs.getTimestamp("create_time");
        if (timestamp != null) {
            log.setCreateTime(timestamp.toLocalDateTime());
        }

        return log;
    }
}
