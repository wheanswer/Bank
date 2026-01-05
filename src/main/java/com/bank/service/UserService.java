package com.bank.service;

import com.bank.entity.User;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户业务逻辑接口
 * 定义用户相关的业务操作方法
 */
public interface UserService {
    /**
     * 用户登录
     * @param account 用户账号
     * @param password 用户密码
     * @return 登录成功的用户对象，失败返回null
     */
    User login(String account, String password);

    /**
     * 生成11位随机账号
     * @return 生成的账号字符串
     */
    String generateAccount();

    /**
     * 用户注册
     * @param user 要注册的用户信息
     * @return 注册成功返回true，失败返回false
     */
    boolean register(User user);

    /**
     * 存款操作
     * @param account 目标账号
     * @param amount 存款金额，必须大于0
     * @return 存款成功返回true，失败返回false
     */
    boolean deposit(String account, BigDecimal amount);

    /**
     * 取款操作
     * @param account 目标账号
     * @param amount 取款金额，必须大于0且余额充足
     * @return 取款成功返回true，失败返回false
     */
    boolean withdraw(String account, BigDecimal amount);

    /**
     * 转账操作
     * @param fromAccount 转出账号
     * @param toAccount 转入账号
     * @param amount 转账金额，必须大于0且余额充足
     * @return 转账成功返回true，失败返回false
     */
    boolean transfer(String fromAccount, String toAccount, BigDecimal amount);

    /**
     * 查询账户余额
     * @param account 要查询的账号
     * @return 账户余额，账号不存在返回null
     */
    BigDecimal checkBalance(String account);

    /**
     * 注销账户（软删除）
     * @param account 要注销的账号
     * @return 注销成功返回true，失败返回false
     */
    boolean deleteAccount(String account);

    /**
     * 根据账号查询用户
     * @param account 用户账号
     * @return 找到的用户对象，不存在返回null
     */
    User findByAccount(String account);

    /**
     * 查询所有用户
     * @return 所有用户列表
     */
    List<User> findAllUsers();

    /**
     * 根据状态查询用户
     * @param status 用户状态：1-正常，0-锁定
     * @return 符合条件的用户列表
     */
    List<User> findUsersByStatus(Integer status);

    /**
     * 锁定用户账号
     * @param account 要锁定的账号
     * @return 锁定成功返回true，失败返回false
     */
    boolean lockAccount(String account);

    /**
     * 解锁用户账号
     * @param account 要解锁的账号
     * @return 解锁成功返回true，失败返回false
     */
    boolean unlockAccount(String account);

    /**
     * 管理员修改用户余额
     * @param account 用户账号
     * @param amount 金额变动（正数增加，负数减少）
     * @return 修改成功返回true，失败返回false
     */
    boolean updateUserBalance(String account, BigDecimal amount);
}
