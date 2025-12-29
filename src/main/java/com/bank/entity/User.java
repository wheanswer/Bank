package com.bank.entity;

import java.math.BigDecimal;

/**
 * 用户实体类
 * 封装银行系统用户的所有核心信息
 * 作为数据在各层之间传递的载体，采用POJO(Plain Old Java Object)设计模式
 * 该类对应数据库中的user表，每个实例代表一条用户记录
 */
public class User {
    /** 用户账号，11位数字字符串，格式模拟手机号，是系统中的唯一标识符 */
    private String account;
    
    /** 用户真实姓名，用于身份验证和交易记录显示 */
    private String name;
    
    /** 用户密码，采用明文存储(生产环境应使用加密存储)，用于登录验证 */
    private String password;
    
    /** 用户手机号，11位数字字符串，用于联系和部分业务验证 */
    private String phone;
    
    /** 用户身份证号，18位或15位字符串，用于实名认证和身份核实 */
    private String idCard;
    
    /** 用户账户余额，采用BigDecimal确保金额计算的精确性，避免浮点运算精度问题 */
    private BigDecimal balance;

    /**
     * 无参构造方法
     * JavaBean规范要求，必须提供无参构造函数以支持反射创建实例
     * 通常用于从数据库查询结果或JSON反序列化创建对象
     */
    public User() {
    }

    /**
     * 带参全量构造方法
     * 方便在创建User对象时一次性初始化所有属性
     * @param account 用户账号，11位数字字符串
     * @param name 用户姓名
     * @param password 用户密码
     * @param phone 手机号，11位数字
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
    }

    /**
     * 获取用户账号
     * @return String 返回11位账号字符串
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置用户账号
     * @param account 11位数字账号字符串
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取用户姓名
     * @return String 用户真实姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户姓名
     * @param name 用户真实姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取用户密码
     * @return String 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取用户手机号
     * @return String 11位手机号字符串
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置用户手机号
     * @param phone 11位手机号字符串
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取用户身份证号
     * @return String 身份证号字符串
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 设置用户身份证号
     * @param idCard 身份证号字符串
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * 获取账户余额
     * @return BigDecimal 返回账户当前余额
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 设置账户余额
     * @param balance 新的账户余额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 重写toString方法
     * 提供用户对象的字符串表示，便于调试和日志输出
     * 注意：为安全考虑，不在输出中包含密码信息
     * @return String 用户对象的格式化字符串表示
     */
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
