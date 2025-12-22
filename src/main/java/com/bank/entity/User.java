package com.bank.entity;

import java.math.BigDecimal;

/**
 * 用户实体类，封装用户信息
 */
public class User {
    private String account;     // 账号，11位数字
    private String name;        // 姓名
    private String password;    // 密码
    private String phone;       // 手机号
    private String idCard;      // 身份证号
    private BigDecimal balance; // 余额

    // 无参构造方法
    public User() {
    }

    // 带参构造方法
    public User(String account, String name, String password, String phone, String idCard, BigDecimal balance) {
        this.account = account;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.idCard = idCard;
        this.balance = balance;
    }

    // getter和setter方法
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

    // 重写toString方法，方便调试
    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", idCard='" + idCard + '\'' +
                ", balance=" + balance +
                '}';
    }
}
