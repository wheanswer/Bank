package com.bank.dao;

import com.bank.entity.Admin;

/**
 * 管理员数据访问接口
 * 定义管理员相关的数据库操作方法
 */
public interface AdminDao {
    /**
     * 管理员登录验证
     * 使用BCrypt加密验证密码
     * @param username 用户名
     * @param password 明文密码
     * @return 匹配的管理员对象，不存在返回null
     */
    Admin login(String username, String password);

    /**
     * 根据用户名查询管理员
     * @param username 用户名
     * @return 找到的管理员对象，不存在返回null
     */
    Admin findByUsername(String username);

    /**
     * 根据ID查询管理员
     * @param id 管理员ID
     * @return 找到的管理员对象，不存在返回null
     */
    Admin findById(Integer id);

    /**
     * 更新管理员登录信息
     * @param id 管理员ID
     * @param ip 登录IP
     * @return 更新成功返回true，失败返回false
     */
    boolean updateLoginInfo(Integer id, String ip);

    /**
     * 修改管理员密码
     * @param id 管理员ID
     * @param newPassword 新密码（加密后）
     * @return 更新成功返回true，失败返回false
     */
    boolean updatePassword(Integer id, String newPassword);

    /**
     * 更新管理员状态
     * @param id 管理员ID
     * @param status 状态：1-正常，0-禁用
     * @return 更新成功返回true，失败返回false
     */
    boolean updateStatus(Integer id, Integer status);

    /**
     * 新增管理员
     * @param admin 管理员对象
     * @return 插入成功返回true，失败返回false
     */
    boolean insert(Admin admin);
}
