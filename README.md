# 银行管理系统

## 项目简介
这是一个基于Java和MySQL的银行管理系统，实现了用户登录、注册、存款、取款、转账、查看余额和注销账户等功能，并增加了管理员系统用于管理用户账号和查看操作日志。

## 技术栈
- **开发语言**：Java 17
- **数据库**：MySQL 8.0+
- **数据库连接**：JDBC
- **界面框架**：Java Swing
- **密码加密**：BCrypt（jbcrypt）
- **项目管理**：Maven

## 项目结构
```
Bank/
├── pom.xml                                    -- Maven项目配置
├── create_database.sql                        -- 数据库初始化脚本
├── README.md                                  -- 项目说明文档
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── bank/
        │           ├── App.java                        -- 主程序入口
        │           ├── entity/                         -- 实体类
        │           │   ├── User.java                   -- 用户实体
        │           │   ├── Admin.java                  -- 管理员实体
        │           │   └── OperationLog.java           -- 操作日志实体
        │           ├── dao/                            -- 数据访问层
        │           │   ├── UserDao.java                -- 用户数据访问接口
        │           │   ├── AdminDao.java               -- 管理员数据访问接口
        │           │   ├── OperationLogDao.java        -- 日志数据访问接口
        │           │   └── impl/
        │           │       ├── UserDaoImpl.java        -- 用户数据访问实现
        │           │       ├── AdminDaoImpl.java       -- 管理员数据访问实现
        │           │       └── OperationLogDaoImpl.java -- 日志数据访问实现
        │           ├── service/                        -- 业务逻辑层
        │           │   ├── UserService.java            -- 用户业务逻辑接口
        │           │   ├── AdminService.java           -- 管理员业务逻辑接口
        │           │   ├── OperationLogService.java    -- 日志业务逻辑接口
        │           │   └── impl/
        │           │       ├── UserServiceImpl.java    -- 用户业务逻辑实现
        │           │       ├── AdminServiceImpl.java   -- 管理员业务逻辑实现
        │           │       └── OperationLogServiceImpl.java -- 日志业务逻辑实现
        │           ├── util/                           -- 工具类
        │           │   └── DBUtil.java                 -- 数据库连接工具类
        │           └── view/                           -- 界面层
        │               ├── LoginFrame.java             -- 登录界面（用户/管理员）
        │               ├── RegisterFrame.java          -- 注册界面
        │               ├── MainFrame.java              -- 用户主功能界面
        │               ├── AdminFrame.java             -- 管理员控制台界面
        │               └── CustomDialog.java           -- 自定义对话框工具
        └── resources/
            └── db.properties                           -- 数据库配置文件
```

## 环境准备
1. **安装JDK**：确保已安装JDK 17或更高版本，并配置好环境变量
2. **安装MySQL**：确保已安装MySQL 8.0或更高版本
3. **Maven**：确保已安装Maven 3.x

## 数据库配置

### 方式一：执行SQL脚本
1. 使用MySQL客户端连接MySQL服务器
2. 执行 `create_database.sql` 脚本创建数据库和表

### 方式二：手动创建
```sql
-- 创建数据库
CREATE DATABASE IF NOT EXISTS bank_system CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE bank_system;

-- 创建用户表
CREATE TABLE IF NOT EXISTS bank_user (
    account VARCHAR(11) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(11) NOT NULL,
    id_card VARCHAR(18) NOT NULL,
    balance DECIMAL(10,2) DEFAULT 0.00,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建管理员表
CREATE TABLE IF NOT EXISTS bank_admin (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(11),
    role VARCHAR(20) DEFAULT 'admin',
    status TINYINT DEFAULT 1,
    login_time DATETIME,
    login_ip VARCHAR(45),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建操作日志表
CREATE TABLE IF NOT EXISTS bank_operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    admin_id INT,
    admin_name VARCHAR(50),
    operation_type VARCHAR(50),
    operation_desc VARCHAR(500),
    target_account VARCHAR(11),
    ip_address VARCHAR(45),
    status TINYINT DEFAULT 1,
    error_message VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (admin_id) REFERENCES bank_admin(id)
);

-- 插入管理员（用户名: admin，密码: admin123）
INSERT INTO bank_admin (username, password, name, role) VALUES
('admin', '$2a$10$U4SMOf9I6YIRmaY3QS4UdeuNKDvmm0duglvEY/5uzpSGXSSGDnm.G', '系统管理员', 'admin');
```

### 修改数据库配置
打开 `src/main/resources/db.properties` 文件，修改数据库连接信息：
```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/bank_system?useSSL=false&serverTimezone=UTC&characterEncoding=utf8
jdbc.username=root
jdbc.password=your_password
```

## 编译和运行

### 使用Maven
```bash
# 编译项目
mvn clean compile

# 运行项目
mvn exec:java -Dexec.mainClass="com.bank.App"
```

### 使用IDEA
1. 打开项目，等待Maven依赖下载完成
2. 右键点击 `App.java` → Run 'App.main()'

## 功能说明

### 用户功能

#### 1. 登录功能
- 输入11位账号和密码进行登录
- 登录成功后进入用户个人中心
- 登录失败提示错误信息

#### 2. 注册功能
- 输入姓名、密码、手机号、身份证号
- 点击"生成账号"按钮生成11位随机账号
- 确认信息后完成注册

#### 3. 存款功能
- 输入存款金额（必须大于0）
- 存款成功后实时更新余额

#### 4. 取款功能
- 输入取款金额（必须大于0且不超过余额）
- 取款成功后实时更新余额

#### 5. 查看余额功能
- 点击按钮查看当前余额
- 余额会实时从数据库获取最新数据

#### 6. 转账功能
- 输入对方11位账号和转账金额
- 系统验证对方账号存在性和余额充足性
- 转账使用事务处理，确保原子性

#### 7. 注销账户功能
- 软删除账户（将状态设为0）
- 注销后账户无法登录

### 管理员功能

#### 1. 管理员登录
- 用户名：`admin`
- 密码：`admin123`
- 支持BCrypt密码加密验证

#### 2. 用户管理
- **查看用户列表**：显示所有用户信息（账号、姓名、手机号、余额、状态、注册时间）
- **刷新**：重新加载用户数据
- **锁定用户**：将用户状态设为锁定（用户无法登录）
- **解锁用户**：将用户状态恢复正常
- **查看详情**：查看用户完整信息

#### 3. 操作日志
- 记录所有管理员操作
- 显示操作时间、管理员、操作类型、目标账号、状态
- 支持查看最近100条日志

## 数据库表说明

### bank_user（用户表）
| 字段 | 类型 | 说明 |
|------|------|------|
| account | VARCHAR(11) | 账号，主键 |
| name | VARCHAR(50) | 姓名 |
| password | VARCHAR(255) | 密码 |
| phone | VARCHAR(11) | 手机号 |
| id_card | VARCHAR(18) | 身份证号 |
| balance | DECIMAL(10,2) | 余额 |
| status | TINYINT | 状态：1-正常，0-锁定 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

### bank_admin（管理员表）
| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | ID，自增主键 |
| username | VARCHAR(50) | 用户名，唯一 |
| password | VARCHAR(255) | 密码（BCrypt加密） |
| name | VARCHAR(50) | 姓名 |
| phone | VARCHAR(11) | 联系电话 |
| role | VARCHAR(20) | 角色：admin/operator |
| status | TINYINT | 状态：1-正常，0-禁用 |
| login_time | DATETIME | 最后登录时间 |
| login_ip | VARCHAR(45) | 最后登录IP |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

### bank_operation_log（操作日志表）
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | ID，自增主键 |
| admin_id | INT | 管理员ID |
| admin_name | VARCHAR(50) | 管理员姓名 |
| operation_type | VARCHAR(50) | 操作类型 |
| operation_desc | VARCHAR(500) | 操作描述 |
| target_account | VARCHAR(11) | 目标账号 |
| ip_address | VARCHAR(45) | IP地址 |
| status | TINYINT | 状态：1-成功，0-失败 |
| error_message | VARCHAR(500) | 错误信息 |
| create_time | DATETIME | 操作时间 |

## 测试数据

### 用户测试数据
| 账号 | 密码 | 姓名 | 余额 |
|------|------|------|------|
| 13800138000 | 123456 | 张三 | 1000.00 |
| 13800138001 | 123456 | 李四 | 2000.00 |

### 管理员测试数据
| 用户名 | 密码 | 姓名 | 角色 |
|--------|------|------|------|
| admin | admin123 | 系统管理员 | admin |

## 项目特点
1. **分层架构**：采用MVC分层设计，清晰明了
2. **DAO模式**：数据访问层与业务逻辑层分离
3. **事务管理**：转账操作使用事务确保数据一致性
4. **密码加密**：管理员密码使用BCrypt加密存储
5. **操作日志**：记录所有管理员操作，便于审计
6. **详细注释**：关键代码都有详细注释，便于学习

## 项目扩展
- [x] 管理员系统
- [x] 操作日志
- [x] 密码加密
- [ ] 用户交易记录
- [ ] 邮件/短信通知
- [ ] 连接池管理
- [ ] REST API接口

## 许可证
本项目仅供学习使用。
