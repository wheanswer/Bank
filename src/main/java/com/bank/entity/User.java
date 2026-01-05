package com.bank.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户实体类
 * 封装银行系统用户的所有核心信息
 * 对应数据库中的 bank_user 表
 */
public class User {
    /** 用户账号，11位数字字符串，系统唯一标识符 */
    private String account;
    
    /** 用户真实姓名 */
    private String name;
    
    /** 用户登录密码 */
    private String password;
    
    /** 用户手机号，11位数字 */
    private String phone;
    
    /** 用户身份证号，18位 */
    private String idCard;
    
    /** 账户余额，使用BigDecimal确保金额计算精度 */
    private BigDecimal balance;
    
    /** 账户状态：1-正常，0-已锁定/注销 */
    private Integer status;
    
    /** 账户创建时间 */
    private LocalDateTime createTime;
    
    /** 最后更新时间 */
    private LocalDateTime updateTime;

    /**
     * 默认构造函数
     */
    public User() {
    }

    /**
     * 带参构造函数
     * @param account 用户账号
     * @param name 用户姓名
     * @param password 用户密码
     * @param phone 手机号
     * @param idCard 身份证号
     * @param balance 账户余额
     */
    public User(String account, String name, String password, String phone, String idCard, BigDecimal balance) {
        this.account = account;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.idCard = idCard;
        this.balance = balance;
        this.status = 1;
    }

    /**
     * 检查账户是否处于正常状态
     * @return true-正常，false-已锁定
     */
    public boolean isActive() {
        return status != null && status == 1;
    }

    // ========== Getter/Setter 方法 ==========

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", balance=" + balance +
                ", status=" + status +
                '}';
    }
}
