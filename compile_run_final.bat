@echo off

rem 最终版银行管理系统编译运行脚本
rem 请确保已安装JDK并配置好环境变量
rem 请确保已将MySQL JDBC驱动jar包放在lib目录下

echo ===================================
echo 银行管理系统编译运行脚本（最终版）
echo ===================================

rem 设置编码为UTF-8，避免中文乱码
chcp 65001

rem 检查lib目录是否存在，不存在则创建
if not exist lib (
    echo 创建lib目录...
    mkdir lib
    echo 请将MySQL JDBC驱动jar包（mysql-connector-java-8.0.x.jar）放在lib目录下
    pause
    exit /b 1
)

rem 检查MySQL JDBC驱动是否存在
set JDBC_DRIVER_FOUND=0
for %%f in (lib\mysql-connector-java-*.jar) do (
    set JDBC_DRIVER_FOUND=1
    echo 找到MySQL JDBC驱动：%%f
    goto :found_driver
)

:found_driver
if %JDBC_DRIVER_FOUND% equ 0 (
    echo 警告：未找到MySQL JDBC驱动，请将mysql-connector-java-8.0.x.jar放在lib目录下
    pause
)

echo.
echo 1. 清理旧的编译结果...
if exist target (
    echo 删除target目录...
    rd /s /q target
)

echo.
echo 2. 创建target/classes目录...
mkdir target
mkdir target\classes

echo.
echo 3. 复制资源文件到target/classes目录...
copy src\main\resources\*.* target\classes\ /y

echo.
echo 4. 编译项目...
javac -d target\classes -cp lib\* src\main\java\com\bank\*.java src\main\java\com\bank\entity\*.java src\main\java\com\bank\dao\*.java src\main\java\com\bank\dao\impl\*.java src\main\java\com\bank\service\*.java src\main\java\com\bank\service\impl\*.java src\main\java\com\bank\util\*.java src\main\java\com\bank\view\*.java

if %errorlevel% equ 0 (
    echo 编译成功！
    echo.
echo 5. 运行项目...
    java -cp target\classes;lib\* com.bank.App
) else (
    echo 编译失败！请检查错误信息。
    pause
    exit /b 1
)

pause