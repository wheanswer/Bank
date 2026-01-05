package com.bank.service.impl;

import com.bank.dao.OperationLogDao;
import com.bank.dao.impl.OperationLogDaoImpl;
import com.bank.entity.OperationLog;
import com.bank.service.OperationLogService;

import java.util.List;

public class OperationLogServiceImpl implements OperationLogService {
    private final OperationLogDao logDao = new OperationLogDaoImpl();

    @Override
    public boolean logOperation(Integer adminId, String adminName, String operationType,
                                String operationDesc, String targetAccount) {
        OperationLog log = new OperationLog(adminId, adminName, operationType, operationDesc);
        log.setTargetAccount(targetAccount);
        log.setIpAddress(getClientIP());
        log.setStatus(1);

        return logDao.insert(log);
    }

    @Override
    public boolean logFailedOperation(Integer adminId, String adminName, String operationType,
                                      String operationDesc, String targetAccount, String errorMsg) {
        OperationLog log = new OperationLog(adminId, adminName, operationType, operationDesc);
        log.setTargetAccount(targetAccount);
        log.setIpAddress(getClientIP());
        log.markFailed(errorMsg);

        return logDao.insert(log);
    }

    @Override
    public List<OperationLog> getLogsByAdmin(Integer adminId) {
        return logDao.findByAdminId(adminId);
    }

    @Override
    public List<OperationLog> getLogsByType(String operationType) {
        return logDao.findByType(operationType);
    }

    @Override
    public List<OperationLog> getLogsByAccount(String account) {
        return logDao.findByAccount(account);
    }

    @Override
    public List<OperationLog> getAllLogs() {
        return logDao.findAll();
    }

    @Override
    public List<OperationLog> getRecentLogs(int limit) {
        return logDao.findRecent(limit);
    }

    private String getClientIP() {
        return "127.0.0.1";
    }
}
