package com.phuoc.util;

import java.security.SecureRandom;

/**
 * Sinh ma OTP 6 chu so dung khi dang ky tai khoan.
 */
public class OtpUtil_24162100 {

    private static final SecureRandom RANDOM = new SecureRandom();

    private OtpUtil_24162100() {
    }

    public static String generateOtp() {
        int otp = 100000 + RANDOM.nextInt(900000);
        return String.valueOf(otp);
    }
}
