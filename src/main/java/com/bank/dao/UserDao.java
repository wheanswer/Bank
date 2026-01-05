package com.bank.dao;

import com.bank.entity.User;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户数据访问接口
 * 定义用户相关的数据库操作方法
 */
public interface UserDao {
    /**
     * 用户登录验证
     * @param account 用户账号
     * @param password 用户密码
     * @return 匹配的用户对象，不存在返回null
     */
    User login(String account, String password);

    /**
     * 根据账号查询用户信息
     * @param account 用户账号
     * @return 找到的用户对象，不存在返回null
     */
    User findByAccount(String account);

    /**
     * 注册新用户
     * @param user 包含注册信息的用户对象
     * @return 插入成功返回true，失败返回false
     */
    boolean register(User user);

    /**
     * 更新用户余额
     * @param account 用户账号
     * @param amount 金额变动，正数为存款，负数为取款
     * @return 更新成功返回true，失败返回false
     */
    boolean updateBalance(String account, BigDecimal amount);

    /**
     * 转账操作
     * @param fromAccount 转出账号
     * @param toAccount 转入账号
     * @param amount 转账金额
     * @return 转账成功返回true，失败返回false
     */
    boolean transfer(String fromAccount, String toAccount, BigDecimal amount);

    /**
     * 注销用户（软删除，将状态设为0）
     * @param account 要注销的用户账号
     * @return 注销成功返回true，失败返回false
     */
    boolean deleteUser(String account);

    /**
     * 更新用户状态
     * @param account 用户账号
     * @param status 状态：1-正常，0-锁定
     * @return 更新成功返回true，失败返回false
     */
    boolean updateStatus(String account, Integer status);

    /**
     * 查询所有用户
     * @return 用户列表
     */
    List<User> findAll();

    /**
     * 根据状态查询用户
     * @param status 用户状态
     * @return 符合条件的用户列表
     */
    List<User> findByStatus(Integer status);
}
