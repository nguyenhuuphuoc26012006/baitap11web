package com.phuoc.util;

import java.security.MessageDigest;

/**
 * Ma hoa mat khau bang MD5 (32 ky tu hex), vua voi cot Users.password NVARCHAR(50) theo dung ERD de cho.
 */
public class PasswordUtil_24162100 {

    private PasswordUtil_24162100() {
    }

    public static String hash(String rawPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(rawPassword.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean matches(String rawPassword, String hashedPassword) {
        return hash(rawPassword).equals(hashedPassword);
    }
}
