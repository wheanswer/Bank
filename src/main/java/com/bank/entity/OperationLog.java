package com.bank.entity;

import java.time.LocalDateTime;

/**
 * 操作日志实体类
 * 记录管理员的所有操作行为
 * 对应数据库中的 bank_operation_log 表
 */
public class OperationLog {
    /** 日志唯一ID，自增主键 */
    private Long id;
    
    /** 执行操作的管理员ID */
    private Integer adminId;
    
    /** 执行操作的管理员姓名 */
    private String adminName;
    
    /** 操作类型：LOGIN-登录、LOCK_USER-锁定用户、UNLOCK_USER-解锁用户等 */
    private String operationType;
    
    /** 操作详细描述 */
    private String operationDesc;
    
    /** 操作目标账号（如锁定/解锁的用户账号） */
    private String targetAccount;
    
    /** 客户端IP地址 */
    private String ipAddress;
    
    /** 操作状态：1-成功，0-失败 */
    private Integer status;
    
    /** 错误信息（操作失败时） */
    private String errorMessage;
    
    /** 操作时间 */
    private LocalDateTime createTime;

    /**
     * 默认构造函数
     */
    public OperationLog() {
    }

    /**
     * 带参构造函数
     * @param adminId 管理员ID
     * @param adminName 管理员姓名
     * @param operationType 操作类型
     * @param operationDesc 操作描述
     */
    public OperationLog(Integer adminId, String adminName, String operationType, String operationDesc) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.operationType = operationType;
        this.operationDesc = operationDesc;
        this.status = 1;
    }

    /**
     * 标记操作失败
     * @param errorMessage 错误信息
     */
    public void markFailed(String errorMessage) {
        this.status = 0;
        this.errorMessage = errorMessage;
    }

    // ========== Getter/Setter 方法 ==========

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationDesc() {
        return operationDesc;
    }

    public void setOperationDesc(String operationDesc) {
        this.operationDesc = operationDesc;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "OperationLog{" +
                "id=" + id +
                ", adminName='" + adminName + '\'' +
                ", operationType='" + operationType + '\'' +
                ", operationDesc='" + operationDesc + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
