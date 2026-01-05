package com.bank.entity;

import java.time.LocalDateTime;

/**
 * 管理员实体类
 * 封装银行系统管理员的所有信息
 * 对应数据库中的 bank_admin 表
 */
public class Admin {
    /** 管理员唯一ID，自增主键 */
    private Integer id;
    
    /** 管理员登录用户名，唯一 */
    private String username;
    
    /** 管理员密码，BCrypt加密存储 */
    private String password;
    
    /** 管理员真实姓名 */
    private String name;
    
    /** 管理员联系电话 */
    private String phone;
    
    /** 角色：admin-超级管理员，operator-操作员 */
    private String role;
    
    /** 账号状态：1-正常，0-已禁用 */
    private Integer status;
    
    /** 最后登录时间 */
    private LocalDateTime loginTime;
    
    /** 最后登录IP */
    private String loginIp;
    
    /** 账号创建时间 */
    private LocalDateTime createTime;
    
    /** 最后更新时间 */
    private LocalDateTime updateTime;

    /**
     * 默认构造函数
     */
    public Admin() {
    }

    /**
     * 带参构造函数
     * @param username 用户名
     * @param password 密码（加密后）
     * @param name 姓名
     * @param role 角色
     */
    public Admin(String username, String password, String name, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.status = 1;
    }

    /**
     * 检查是否为超级管理员
     * @return true-是超级管理员
     */
    public boolean isSuperAdmin() {
        return "admin".equals(role);
    }

    /**
     * 检查账号是否处于正常状态
     * @return true-正常，false-已禁用
     */
    public boolean isActive() {
        return status != null && status == 1;
    }

    // ========== Getter/Setter 方法 ==========

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
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
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", status=" + status +
                '}';
    }
}
