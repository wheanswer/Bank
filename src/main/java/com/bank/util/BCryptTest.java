package com.bank.util;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptTest {
    public static void main(String[] args) {
        String password = "admin123";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println("Password: " + password);
        System.out.println("Hashed: " + hashed);
        System.out.println("Check result: " + BCrypt.checkpw(password, hashed));
    }
}
