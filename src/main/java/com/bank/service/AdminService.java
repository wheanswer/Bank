package com.bank.service;

import com.bank.entity.Admin;

/**
 * 管理员业务逻辑接口
 * 定义管理员相关的业务操作方法
 */
public interface AdminService {
    /**
     * 管理员登录
     * 登录成功后记录登录时间和IP，并生成登录日志
     * @param username 用户名
     * @param password 密码（明文）
     * @return 登录成功的管理员对象，失败返回null
     */
    Admin login(String username, String password);

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
     * @return 修改成功返回true，失败返回false
     */
    boolean changePassword(Integer id, String newPassword);

    /**
     * 禁用管理员账号
     * @param id 要禁用的管理员ID
     * @return 操作成功返回true，失败返回false
     */
    boolean disableAdmin(Integer id);

    /**
     * 启用管理员账号
     * @param id 要启用的管理员ID
     * @return 操作成功返回true，失败返回false
     */
    boolean enableAdmin(Integer id);
}
