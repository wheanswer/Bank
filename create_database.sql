-- 创建银行管理系统数据库
CREATE DATABASE IF NOT EXISTS bank_system CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 使用该数据库
USE bank_system;

-- 如果用户表已存在但缺少新字段，执行以下ALTER语句
-- ALTER TABLE bank_user ADD COLUMN status TINYINT DEFAULT 1 AFTER balance;
-- ALTER TABLE bank_user ADD COLUMN create_time DATETIME DEFAULT CURRENT_TIMESTAMP AFTER status;
-- ALTER TABLE bank_user ADD COLUMN update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP AFTER create_time;

-- 创建用户表（如果不存在）
CREATE TABLE IF NOT EXISTS bank_user (
    account VARCHAR(11) PRIMARY KEY,  -- 账号，11位数字
    name VARCHAR(50) NOT NULL,        -- 姓名
    password VARCHAR(255) NOT NULL,   -- 密码
    phone VARCHAR(11) NOT NULL,       -- 手机号
    id_card VARCHAR(18) NOT NULL,     -- 身份证号
    balance DECIMAL(10,2) DEFAULT 0.00, -- 余额
    status TINYINT DEFAULT 1,          -- 账号状态：1-正常，0-已注销
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP, -- 创建时间
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 更新时间
);

-- 创建管理员表
CREATE TABLE IF NOT EXISTS bank_admin (
    id INT PRIMARY KEY AUTO_INCREMENT,  -- 管理员ID
    username VARCHAR(50) NOT NULL UNIQUE, -- 管理员用户名，唯一
    password VARCHAR(255) NOT NULL,      -- 管理员密码（BCrypt加密存储）
    name VARCHAR(50) NOT NULL,           -- 管理员姓名
    phone VARCHAR(11),                   -- 联系电话
    role VARCHAR(20) DEFAULT 'admin',    -- 角色：admin-超级管理员，operator-操作员
    status TINYINT DEFAULT 1,            -- 账号状态：1-正常，0-已禁用
    login_time DATETIME,                 -- 最后登录时间
    login_ip VARCHAR(45),                -- 最后登录IP
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP, -- 创建时间
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 更新时间
);

-- 插入默认管理员（用户名：admin，密码：admin123，BCrypt哈希）
INSERT INTO bank_admin (username, password, name, role) VALUES
('admin', '$2a$10$U4SMOf9I6YIRmaY3QS4UdeuNKDvmm0duglvEY/5uzpSGXSSGDnm.G', '系统管理员', 'admin');

-- 插入测试管理员（用户名：operator，密码：123456）
INSERT INTO bank_admin (username, password, name, phone, role) VALUES
('operator', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '操作员', '13800138000', 'operator');

-- 创建操作日志表
CREATE TABLE IF NOT EXISTS bank_operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  -- 日志ID
    admin_id INT,                           -- 管理员ID
    admin_name VARCHAR(50),                 -- 管理员姓名
    operation_type VARCHAR(50),             -- 操作类型
    operation_desc VARCHAR(500),            -- 操作描述
    target_account VARCHAR(11),             -- 目标账号（操作对象）
    ip_address VARCHAR(45),                 -- IP地址
    status TINYINT DEFAULT 1,               -- 操作状态：1-成功，0-失败
    error_message VARCHAR(500),             -- 错误信息（如果失败）
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP, -- 操作时间
    FOREIGN KEY (admin_id) REFERENCES bank_admin(id)
);

-- 创建索引提高查询效率（如果索引不存在会报错，可忽略）
CREATE INDEX idx_log_admin_id ON bank_operation_log(admin_id);
CREATE INDEX idx_log_create_time ON bank_operation_log(create_time);
CREATE INDEX idx_log_operation_type ON bank_operation_log(operation_type);

-- 仅当status字段已存在时才创建索引
-- CREATE INDEX idx_user_status ON bank_user(status);

-- 插入测试用户数据（可选）
INSERT INTO bank_user (account, name, password, phone, id_card, balance) VALUES
('13800138000', '张三', '123456', '13800138000', '110101199001011234', 1000.00),
('01234567890', 'admin', '123456', '13800138007', '110101199001011236', 1000.00),
('13800138001', '李四', '123456', '13800138001', '110101199001011235', 2000.00);
