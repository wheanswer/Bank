package com.bank.dao;

import com.bank.entity.User;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * 用户数据访问接口，定义用户相关的数据库操作方法
 */
public interface UserDao {
    /**
     * 根据账号和密码查询用户
     * @param account 账号
     * @param password 密码
     * @return 用户对象，如果不存在则返回null
     */
    User login(String account, String password);

    /**
     * 根据账号查询用户
     * @param account 账号
     * @return 用户对象，如果不存在则返回null
     */
    User findByAccount(String account);

    /**
     * 注册新用户
     * @param user 用户对象
     * @return 是否注册成功
     */
    boolean register(User user);

    /**
     * 更新用户余额
     * @param account 账号
     * @param amount 金额（正数为存款，负数为取款）
     * @return 是否更新成功
     */
    boolean updateBalance(String account, BigDecimal amount);

    /**
     * 转账操作
     * @param fromAccount 转出账号
     * @param toAccount 转入账号
     * @param amount 转账金额
     * @return 是否转账成功
     */
    boolean transfer(String fromAccount, String toAccount, BigDecimal amount);

    /**
     * 删除用户（注销账户）
     * @param account 账号
     * @return 是否删除成功
     */
    boolean deleteUser(String account);
}
