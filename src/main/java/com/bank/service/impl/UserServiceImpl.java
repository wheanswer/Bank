package com.bank.service.impl;

import com.bank.dao.UserDao;
import com.bank.dao.impl.UserDaoImpl;
import com.bank.entity.User;
import com.bank.service.UserService;
import java.math.BigDecimal;
import java.util.Random;

/**
 * 用户业务逻辑实现类，实现用户相关的业务逻辑方法
 */
public class UserServiceImpl implements UserService {
    // 注入UserDao
    private UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String account, String password) {
        // 参数校验
        if (account == null || account.trim().length() != 11 || password == null || password.trim().isEmpty()) {
            return null;
        }
        return userDao.login(account, password);
    }

    @Override
    public String generateAccount() {
        Random random = new Random();
        
        // 生成11位随机数，以1开头（模拟手机号格式）
        StringBuilder sb = new StringBuilder("1");
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    @Override
    public boolean register(User user) {
        // 参数校验
        if (user == null) {
            System.out.println("注册失败：用户对象为null");
            return false;
        }
        if (user.getAccount() == null || user.getAccount().trim().isEmpty()) {
            System.out.println("注册失败：账号为空");
            return false;
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            System.out.println("注册失败：密码为空");
            return false;
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            System.out.println("注册失败：姓名为空");
            return false;
        }
        if (user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            System.out.println("注册失败：电话为空");
            return false;
        }
        if (user.getIdCard() == null || user.getIdCard().trim().isEmpty()) {
            System.out.println("注册失败：身份证号为空");
            return false;
        }
        // 检查账号是否已存在
        System.out.println("检查账号是否已存在：" + user.getAccount());
        User existingUser = userDao.findByAccount(user.getAccount());
        if (existingUser != null) {
            System.out.println("注册失败：账号已存在：" + user.getAccount());
            return false;
        }
        // 设置初始余额为0
        if (user.getBalance() == null) {
            user.setBalance(BigDecimal.ZERO);
            System.out.println("注册：设置初始余额为0");
        }
        System.out.println("执行注册操作：" + user.getAccount());
        boolean result = userDao.register(user);
        if (result) {
            System.out.println("注册成功：" + user.getAccount());
        } else {
            System.out.println("注册失败：数据库操作失败，账号：" + user.getAccount());
        }
        return result;
    }

    @Override
    public boolean deposit(String account, BigDecimal amount) {
        // 参数校验
        if (account == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        // 检查账号是否存在
        if (userDao.findByAccount(account) == null) {
            return false;
        }
        return userDao.updateBalance(account, amount);
    }

    @Override
    public boolean withdraw(String account, BigDecimal amount) {
        // 参数校验
        if (account == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        // 检查账号是否存在
        User user = userDao.findByAccount(account);
        if (user == null) {
            return false;
        }
        // 检查余额是否足够
        if (user.getBalance().compareTo(amount) < 0) {
            return false;
        }
        return userDao.updateBalance(account, amount.negate());
    }

    @Override
    public boolean transfer(String fromAccount, String toAccount, BigDecimal amount) {
        // 参数校验
        if (fromAccount == null || toAccount == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        // 检查转出账号和转入账号是否存在
        if (userDao.findByAccount(fromAccount) == null || userDao.findByAccount(toAccount) == null) {
            return false;
        }
        // 检查转出账号余额是否足够
        User fromUser = userDao.findByAccount(fromAccount);
        if (fromUser.getBalance().compareTo(amount) < 0) {
            return false;
        }
        return userDao.transfer(fromAccount, toAccount, amount);
    }

    @Override
    public BigDecimal checkBalance(String account) {
        if (account == null) {
            return null;
        }
        User user = userDao.findByAccount(account);
        return user != null ? user.getBalance() : null;
    }

    @Override
    public boolean deleteAccount(String account) {
        if (account == null) {
            return false;
        }
        // 检查账号是否存在
        if (userDao.findByAccount(account) == null) {
            return false;
        }
        return userDao.deleteUser(account);
    }

    @Override
    public User findByAccount(String account) {
        if (account == null) {
            return null;
        }
        return userDao.findByAccount(account);
    }
}