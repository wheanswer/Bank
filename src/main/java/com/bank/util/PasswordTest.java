package com.bank.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordTest {
    public static void main(String[] args) {
        String inputPassword = "admin123";
        String storedHash = "$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi";
        
        System.out.println("Input password: " + inputPassword);
        System.out.println("Stored hash: " + storedHash);
        System.out.println("Match result: " + BCrypt.checkpw(inputPassword, storedHash));
        
        String newHash = BCrypt.hashpw("admin123", BCrypt.gensalt());
        System.out.println("\nNew hash for admin123: " + newHash);
        System.out.println("Check new hash: " + BCrypt.checkpw("admin123", newHash));
    }
}
