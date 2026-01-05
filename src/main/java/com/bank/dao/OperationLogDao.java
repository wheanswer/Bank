package com.bank.dao;

import com.bank.entity.OperationLog;
import java.util.List;

/**
 * 操作日志数据访问接口
 * 定义日志相关的数据库操作方法
 */
public interface OperationLogDao {
    /**
     * 插入操作日志
     * @param log 日志对象
     * @return 插入成功返回true，失败返回false
     */
    boolean insert(OperationLog log);

    /**
     * 根据管理员ID查询日志
     * @param adminId 管理员ID
     * @return 该管理员的操作日志列表
     */
    List<OperationLog> findByAdminId(Integer adminId);

    /**
     * 根据操作类型查询日志
     * @param operationType 操作类型
     * @return 符合条件的日志列表
     */
    List<OperationLog> findByType(String operationType);

    /**
     * 根据目标账号查询日志
     * @param account 目标用户账号
     * @return 涉及该账号的日志列表
     */
    List<OperationLog> findByAccount(String account);

    /**
     * 查询所有日志
     * @return 所有操作日志列表
     */
    List<OperationLog> findAll();

    /**
     * 查询最近的操作日志
     * @param limit 返回记录数量限制
     * @return 最近的操作日志列表
     */
    List<OperationLog> findRecent(int limit);
}
