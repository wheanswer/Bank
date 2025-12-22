import java.lang.reflect.Method;

public class test_validation {
    public static void main(String[] args) {
        System.out.println("开始测试优化后的校验功能...");
        
        try {
            // 创建RegisterFrame实例来测试校验方法
            Object registerFrame = Class.forName("com.bank.view.RegisterFrame").getDeclaredConstructor().newInstance();
            
            // 获取校验方法
            Method validatePhoneMethod = registerFrame.getClass().getDeclaredMethod("validatePhone", String.class);
            Method validateIdCardMethod = registerFrame.getClass().getDeclaredMethod("validateIdCard", String.class);
            
            // 设置方法可访问
            validatePhoneMethod.setAccessible(true);
            validateIdCardMethod.setAccessible(true);
            
            System.out.println("\n=== 测试手机号校验 ===");
            
            // 测试正确的手机号
            String[] validPhones = {"13812345678", "15912345678", "18812345678"};
            for (String phone : validPhones) {
                boolean result = (Boolean) validatePhoneMethod.invoke(registerFrame, phone);
                System.out.println("手机号 " + phone + ": " + (result ? "✅ 通过" : "❌ 失败"));
            }
            
            // 测试错误的手机号
            String[] invalidPhones = {
                "12345678901",  // 开头不是1
                "1234567890",   // 长度不对
                "138123456789", // 长度不对
                "1381234567a",  // 包含字母
                "11111111111"   // 全相同数字
            };
            for (String phone : invalidPhones) {
                boolean result = (Boolean) validatePhoneMethod.invoke(registerFrame, phone);
                System.out.println("手机号 " + phone + ": " + (result ? "✅ 通过" : "❌ 失败"));
            }
            
            System.out.println("\n=== 测试身份证号校验 ===");
            
            // 测试正确的身份证号
            String[] validIdCards = {
                "110101199001011236", // 北京市正确身份证
                "320101199001011235", // 江苏省正确身份证
                "440101199001011234"  // 广东省正确身份证
            };
            for (String idCard : validIdCards) {
                boolean result = (Boolean) validateIdCardMethod.invoke(registerFrame, idCard);
                System.out.println("身份证号 " + idCard + ": " + (result ? "✅ 通过" : "❌ 失败"));
            }
            
            // 测试错误的身份证号
            String[] invalidIdCards = {
                "123456789012345678",   // 长度不对
                "12345678901234567",    // 长度不对
                "1234567890123456a",    // 第18位不是数字或X
                "123456789012345679",   // 校验位错误
                "110101189001011236",   // 出生日期无效
                "110101199013011236",   // 月份无效
                "110101199001321236",   // 日期无效
                "12345678901234567"     // 地区码无效
            };
            for (String idCard : invalidIdCards) {
                boolean result = (Boolean) validateIdCardMethod.invoke(registerFrame, idCard);
                System.out.println("身份证号 " + idCard + ": " + (result ? "✅ 通过" : "❌ 失败"));
            }
            
            System.out.println("\n✅ 校验功能测试完成！");
            
        } catch (Exception e) {
            System.out.println("❌ 测试过程中出现异常：");
            e.printStackTrace();
        }
    }
}