package com.bank.service;

import com.bank.entity.User;
import java.math.BigDecimal;

/**
 * 用户业务逻辑接口，定义用户相关的业务逻辑方法
 */
public interface UserService {
    /**
     * 用户登录
     * @param account 账号
     * @param password 密码
     * @return 用户对象，如果登录失败则返回null
     */
    User login(String account, String password);

    /**
     * 生成11位随机账号
     * @return 11位随机账号
     */
    String generateAccount();

    /**
     * 用户注册
     * @param user 用户对象
     * @return 是否注册成功
     */
    boolean register(User user);

    /**
     * 存款操作
     * @param account 账号
     * @param amount 存款金额
     * @return 是否存款成功
     */
    boolean deposit(String account, BigDecimal amount);

    /**
     * 取款操作
     * @param account 账号
     * @param amount 取款金额
     * @return 是否取款成功
     */
    boolean withdraw(String account, BigDecimal amount);

    /**
     * 转账操作
     * @param fromAccount 转出账号
     * @param toAccount 转入账号
     * @param amount 转账金额
     * @return 是否转账成功
     */
    boolean transfer(String fromAccount, String toAccount, BigDecimal amount);

    /**
     * 查看余额
     * @param account 账号
     * @return 余额，如果账号不存在则返回null
     */
    BigDecimal checkBalance(String account);

    /**
     * 注销账户
     * @param account 账号
     * @return 是否注销成功
     */
    boolean deleteAccount(String account);
    
    /**
     * 根据账号查询用户
     * @param account 账号
     * @return 用户对象，如果不存在则返回null
     */
    User findByAccount(String account);
}