-- 创建银行管理系统数据库
CREATE DATABASE IF NOT EXISTS bank_system CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 使用该数据库
USE bank_system;

-- 创建用户表
CREATE TABLE IF NOT EXISTS bank_user (
    account VARCHAR(11) PRIMARY KEY,  -- 账号，11位数字
    name VARCHAR(50) NOT NULL,        -- 姓名
    password VARCHAR(255) NOT NULL,   -- 密码
    phone VARCHAR(11) NOT NULL,       -- 手机号
    id_card VARCHAR(18) NOT NULL,     -- 身份证号
    balance DECIMAL(10,2) DEFAULT 0.00 -- 余额
);

-- 插入测试数据（可选）
INSERT INTO bank_user (account, name, password, phone, id_card, balance) VALUES 
('13800138000', '张三', '123456', '13800138000', '110101199001011234', 1000.00),
('13800138001', '李四', '123456', '13800138001', '110101199001011235', 2000.00);
