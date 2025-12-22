# 银行管理系统

## 项目简介
这是一个基于Java和MySQL的银行管理系统，实现了登录、注册、存款、取款、转账、查看余额和注销账户等功能。

## 技术栈
- **开发语言**：Java
- **数据库**：MySQL
- **数据库连接**：JDBC
- **界面框架**：Java Swing

## 项目结构
```
Bank/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── bank/
│   │   │   │   │   ├── App.java                    -- 主程序入口
│   │   │   │   │   ├── entity/
│   │   │   │   │   │   └── User.java               -- 用户实体类
│   │   │   │   │   ├── dao/
│   │   │   │   │   │   ├── UserDao.java           -- 用户数据访问接口
│   │   │   │   │   │   └── impl/
│   │   │   │   │   │       └── UserDaoImpl.java    -- 用户数据访问实现
│   │   │   │   │   ├── service/
│   │   │   │   │   │   ├── UserService.java        -- 用户业务逻辑接口
│   │   │   │   │   │   └── impl/
│   │   │   │   │   │       └── UserServiceImpl.java -- 用户业务逻辑实现
│   │   │   │   │   ├── util/
│   │   │   │   │   │   └── DBUtil.java             -- 数据库连接工具类
│   │   │   │   │   └── view/
│   │   │   │   │       ├── LoginFrame.java         -- 登录界面
│   │   │   │   │       ├── RegisterFrame.java      -- 注册界面
│   │   │   │   │       └── MainFrame.java          -- 主功能界面
│   │   └── resources/
│   │       └── db.properties                       -- 数据库配置文件
├── lib/                                            -- 依赖库目录
├── create_database.sql                             -- 创建数据库和表的SQL脚本
├── compile_run.bat                                 -- 编译和运行脚本
└── README.md                                       -- 项目说明文档
```

## 环境准备
1. **安装JDK**：确保已安装JDK 8或更高版本，并配置好环境变量
2. **安装MySQL**：确保已安装MySQL 5.7或更高版本
3. **下载MySQL JDBC驱动**：下载MySQL JDBC驱动jar包（mysql-connector-java-8.0.x.jar），并放在lib目录下

## 数据库配置
1. **创建数据库和表**：
   - 使用MySQL客户端连接MySQL服务器
   - 执行`create_database.sql`脚本创建数据库和表
   - 或者手动执行以下SQL语句：
     ```sql
     CREATE DATABASE IF NOT EXISTS bank_system CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
     USE bank_system;
     CREATE TABLE IF NOT EXISTS bank_user (
         account VARCHAR(11) PRIMARY KEY,
         name VARCHAR(50) NOT NULL,
         password VARCHAR(255) NOT NULL,
         phone VARCHAR(11) NOT NULL,
         id_card VARCHAR(18) NOT NULL,
         balance DECIMAL(10,2) DEFAULT 0.00
     );
     ```

2. **修改数据库配置**：
   - 打开`src/main/resources/db.properties`文件
   - 修改数据库连接信息（根据实际情况）：
     ```properties
     jdbc.url=jdbc:mysql://localhost:3306/bank_system?useUnicode=true&characterEncoding=utf8mb4&useSSL=false&serverTimezone=Asia/Shanghai
     jdbc.username=root
     jdbc.password=root
     ```

## 编译和运行
1. **使用编译运行脚本**：
   - 双击运行`compile_run.bat`脚本
   - 脚本会自动编译项目并运行

2. **手动编译和运行**：
   - 编译项目：
     ```
     javac -d target -cp lib/* src/main/java/com/bank/*.java src/main/java/com/bank/entity/*.java src/main/java/com/bank/dao/*.java src/main/java/com/bank/dao/impl/*.java src/main/java/com/bank/service/*.java src/main/java/com/bank/service/impl/*.java src/main/java/com/bank/util/*.java src/main/java/com/bank/view/*.java
     ```
   - 运行项目：
     ```
     java -cp target;lib/* com.bank.App
     ```

## 功能说明

### 1. 登录功能
- 输入账号和密码进行登录
- 登录成功后进入主功能界面
- 登录失败则提示错误信息

### 2. 注册功能
- 输入姓名、密码、手机号、身份证号
- 点击"生成账号"按钮生成11位随机账号
- 确认密码一致后点击"注册"按钮完成注册
- 注册成功后返回登录界面

### 3. 存款功能
- 输入存款金额
- 存款金额必须大于0
- 存款成功后更新余额显示

### 4. 取款功能
- 输入取款金额
- 取款金额必须大于0且不超过当前余额
- 取款成功后更新余额显示

### 5. 查看余额功能
- 点击"查看余额"按钮查看当前余额
- 余额会实时更新

### 6. 转账功能
- 输入对方账号和转账金额
- 检查对方账号是否存在
- 检查转账金额是否大于0且不超过当前余额
- 转账成功后更新余额显示

### 7. 注销账户功能
- 点击"注销账户"按钮
- 确认后注销当前账户
- 注销成功后返回登录界面

### 8. 退出系统功能
- 点击"退出系统"按钮退出系统

## 注意事项
1. 账号必须是11位数字
2. 密码在数据库中以明文存储（实际项目中应使用加密存储）
3. 转账操作使用了事务处理，确保转账的原子性
4. 界面设计简洁明了，适合初学者学习

## 测试数据
创建数据库时，会自动插入以下测试数据：
- 账号：13800138000，密码：123456，姓名：张三，余额：1000.00
- 账号：13800138001，密码：123456，姓名：李四，余额：2000.00

可以使用以上测试数据直接登录系统进行测试。

## 项目扩展
1. 添加密码加密功能（使用MD5或SHA256等算法）
2. 添加交易记录功能
3. 添加管理员功能
4. 优化界面设计
5. 添加更多的输入验证
6. 使用连接池管理数据库连接

## 许可证
本项目仅供学习使用。
