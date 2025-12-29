package com.bank.dao;

import com.bank.entity.User;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * 用户数据访问接口
 * 定义用户相关的数据库操作方法规范
 * 采用DAO模式，将业务逻辑与数据访问逻辑分离
 */
public interface UserDao {
    /**
     * 用户登录验证
     * 根据账号和密码从数据库查询匹配的用户记录
     * @param account 用户账号，11位数字字符串
     * @param password 用户密码
     * @return User 返回查询到的用户对象，如果不存在返回null
     */
    User login(String account, String password);

    /**
     * 根据账号查询用户信息
     * 用于验证账号是否存在以及获取用户详情
     * @param account 用户账号，11位数字字符串
     * @return User 返回查询到的用户对象，如果不存在返回null
     */
    User findByAccount(String account);

    /**
     * 注册新用户
     * 将用户信息插入到数据库的bank_user表中
     * @param user 包含用户信息的User对象（账号、姓名、密码、手机号、身份证号、初始余额）
     * @return boolean 注册成功返回true，失败返回false
     */
    boolean register(User user);

    /**
     * 更新用户余额
     * 根据账号对用户账户进行余额增减操作
     * @param account 用户账号
     * @param amount 金额，正数为存款，负数为取款
     * @return boolean 更新成功返回true，失败返回false
     */
    boolean updateBalance(String account, BigDecimal amount);

    /**
     * 转账操作
     * 实现两个账户之间的资金转移，使用事务确保数据一致性
     * @param fromAccount 转出账号
     * @param toAccount 转入账号
     * @param amount 转账金额，必须大于0
     * @return boolean 转账成功返回true，失败返回false（包含余额不足、账号不存在等情况）
     */
    boolean transfer(String fromAccount, String toAccount, BigDecimal amount);

    /**
     * 删除用户（注销账户）
     * 从数据库中删除指定账号的用户记录
     * @param account 要删除的用户账号
     * @return boolean 删除成功返回true，失败返回false
     */
    boolean deleteUser(String account);
}
