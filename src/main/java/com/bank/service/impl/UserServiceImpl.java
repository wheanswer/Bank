package com.bank.service.impl;

import com.bank.dao.OperationLogDao;
import com.bank.dao.UserDao;
import com.bank.dao.impl.OperationLogDaoImpl;
import com.bank.dao.impl.UserDaoImpl;
import com.bank.entity.OperationLog;
import com.bank.entity.User;
import com.bank.service.UserService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();
    private final OperationLogDao logDao = new OperationLogDaoImpl();

    @Override
    public User login(String account, String password) {
        if (account == null || account.trim().length() != 11 || password == null || password.trim().isEmpty()) {
            return null;
        }
        return userDao.login(account, password);
    }

    @Override
    public String generateAccount() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder("1");
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    @Override
    public boolean register(User user) {
        if (user == null || user.getAccount() == null || user.getPassword() == null) {
            return false;
        }
        if (userDao.findByAccount(user.getAccount()) != null) {
            return false;
        }
        if (user.getBalance() == null) {
            user.setBalance(BigDecimal.ZERO);
        }
        return userDao.register(user);
    }

    @Override
    public boolean deposit(String account, BigDecimal amount) {
        if (account == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (userDao.findByAccount(account) == null) {
            return false;
        }
        return userDao.updateBalance(account, amount);
    }

    @Override
    public boolean withdraw(String account, BigDecimal amount) {
        if (account == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        User user = userDao.findByAccount(account);
        if (user == null) {
            return false;
        }
        if (user.getBalance().compareTo(amount) < 0) {
            return false;
        }
        return userDao.updateBalance(account, amount.negate());
    }

    @Override
    public boolean transfer(String fromAccount, String toAccount, BigDecimal amount) {
        if (fromAccount == null || toAccount == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (userDao.findByAccount(fromAccount) == null || userDao.findByAccount(toAccount) == null) {
            return false;
        }
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

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @Override
    public List<User> findUsersByStatus(Integer status) {
        return userDao.findByStatus(status);
    }

    @Override
    public boolean lockAccount(String account) {
        if (account == null) {
            return false;
        }
        if (userDao.findByAccount(account) == null) {
            return false;
        }
        return userDao.updateStatus(account, 0);
    }

    @Override
    public boolean unlockAccount(String account) {
        if (account == null) {
            return false;
        }
        if (userDao.findByAccount(account) == null) {
            return false;
        }
        return userDao.updateStatus(account, 1);
    }

    @Override
    public boolean updateUserBalance(String account, BigDecimal amount) {
        if (account == null || amount == null) {
            return false;
        }
        if (userDao.findByAccount(account) == null) {
            return false;
        }
        return userDao.updateBalance(account, amount);
    }
}
