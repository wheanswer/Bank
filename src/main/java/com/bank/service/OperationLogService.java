package com.bank.service;

import com.bank.entity.OperationLog;
import java.util.List;

/**
 * 操作日志业务逻辑接口
 * 定义日志相关的业务操作方法
 */
public interface OperationLogService {
    /**
     * 记录成功操作日志
     * @param adminId 管理员ID
     * @param adminName 管理员姓名
     * @param operationType 操作类型
     * @param operationDesc 操作描述
     * @param targetAccount 目标账号
     * @return 记录成功返回true，失败返回false
     */
    boolean logOperation(Integer adminId, String adminName, String operationType,
                         String operationDesc, String targetAccount);

    /**
     * 记录失败操作日志
     * @param adminId 管理员ID
     * @param adminName 管理员姓名
     * @param operationType 操作类型
     * @param operationDesc 操作描述
     * @param targetAccount 目标账号
     * @param errorMsg 错误信息
     * @return 记录成功返回true，失败返回false
     */
    boolean logFailedOperation(Integer adminId, String adminName, String operationType,
                               String operationDesc, String targetAccount, String errorMsg);

    /**
     * 根据管理员ID查询日志
     * @param adminId 管理员ID
     * @return 该管理员的操作日志列表
     */
    List<OperationLog> getLogsByAdmin(Integer adminId);

    /**
     * 根据操作类型查询日志
     * @param operationType 操作类型
     * @return 符合条件的日志列表
     */
    List<OperationLog> getLogsByType(String operationType);

    /**
     * 根据目标账号查询日志
     * @param account 目标用户账号
     * @return 涉及该账号的日志列表
     */
    List<OperationLog> getLogsByAccount(String account);

    /**
     * 查询所有日志
     * @return 所有操作日志列表
     */
    List<OperationLog> getAllLogs();

    /**
     * 查询最近的日志
     * @param limit 返回记录数量限制
     * @return 最近的操作日志列表
     */
    List<OperationLog> getRecentLogs(int limit);
}
