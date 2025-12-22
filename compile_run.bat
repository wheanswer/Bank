@echo off

rem 银行管理系统编译运行脚本
rem 请确保已安装JDK并配置好环境变量
rem 请确保已将MySQL JDBC驱动jar包放在lib目录下

echo ===================================
echo 银行管理系统编译运行脚本
echo ===================================

rem 检查lib目录是否存在，不存在则创建
if not exist lib mkdir lib

echo.
echo 1. 编译项目...
rem 编译项目，将编译后的class文件输出到target目录
javac -d target -cp lib/* src/main/java/com/bank/*.java src/main/java/com/bank/entity/*.java src/main/java/com/bank/dao/*.java src/main/java/com/bank/dao/impl/*.java src/main/java/com/bank/service/*.java src/main/java/com/bank/service/impl/*.java src/main/java/com/bank/util/*.java src/main/java/com/bank/view/*.java

if %errorlevel% equ 0 (
    echo 编译成功！
    echo.
    echo 2. 运行项目...
    rem 运行项目，添加lib目录下的所有jar包到classpath
    java -cp target;lib/* com.bank.App
) else (
    echo 编译失败！请检查错误信息。
    pause
    exit /b 1
)

pause
