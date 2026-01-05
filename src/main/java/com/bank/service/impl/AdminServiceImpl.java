package com.bank.service.impl;

import com.bank.dao.AdminDao;
import com.bank.dao.OperationLogDao;
import com.bank.dao.impl.AdminDaoImpl;
import com.bank.dao.impl.OperationLogDaoImpl;
import com.bank.entity.Admin;
import com.bank.entity.OperationLog;
import com.bank.service.AdminService;

public class AdminServiceImpl implements AdminService {
    private final AdminDao adminDao = new AdminDaoImpl();
    private final OperationLogDao logDao = new OperationLogDaoImpl();

    @Override
    public Admin login(String username, String password) {
        Admin admin = adminDao.login(username, password);
        if (admin != null) {
            String ip = getClientIP();
            adminDao.updateLoginInfo(admin.getId(), ip);

            OperationLog log = new OperationLog(admin.getId(), admin.getName(),
                "ADMIN_LOGIN", "管理员登录系统");
            log.setIpAddress(ip);
            logDao.insert(log);
        }
        return admin;
    }

    @Override
    public Admin findById(Integer id) {
        return adminDao.findById(id);
    }

    @Override
    public boolean updateLoginInfo(Integer id, String ip) {
        return adminDao.updateLoginInfo(id, ip);
    }

    @Override
    public boolean changePassword(Integer id, String newPassword) {
        return adminDao.updatePassword(id, newPassword);
    }

    @Override
    public boolean disableAdmin(Integer id) {
        Admin admin = adminDao.findById(id);
        if (admin == null) {
            return false;
        }
        boolean result = adminDao.updateStatus(id, 0);

        OperationLog log = new OperationLog(admin.getId(), admin.getName(),
            "DISABLE_ADMIN", "禁用管理员账号: " + admin.getUsername());
        log.setTargetAccount(admin.getUsername());
        log.setStatus(result ? 1 : 0);
        if (!result) {
            log.markFailed("禁用管理员失败");
        }
        logDao.insert(log);

        return result;
    }

    @Override
    public boolean enableAdmin(Integer id) {
        Admin admin = adminDao.findById(id);
        if (admin == null) {
            return false;
        }
        boolean result = adminDao.updateStatus(id, 1);

        OperationLog log = new OperationLog(admin.getId(), admin.getName(),
            "ENABLE_ADMIN", "启用管理员账号: " + admin.getUsername());
        log.setTargetAccount(admin.getUsername());
        log.setStatus(result ? 1 : 0);
        if (!result) {
            log.markFailed("启用管理员失败");
        }
        logDao.insert(log);

        return result;
    }

    private String getClientIP() {
        return "127.0.0.1";
    }
}
