package com.phuoc.util;

import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

/**
 * Gui email kich hoat tai khoan bang ma OTP (domain phuoc.com).
 */
public class MailUtil_24162100 {

    private MailUtil_24162100() {
    }

    public static void sendOtpMail(String toEmail, String otpCode) {
        String host = DBConnection_24162100.getMailProp("mail.host");
        String port = DBConnection_24162100.getMailProp("mail.port");
        final String username = DBConnection_24162100.getMailProp("mail.username");
        final String password = DBConnection_24162100.getMailProp("mail.password");
        String from = DBConnection_24162100.getMailProp("mail.from");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from, "OnlineShop - phuoc.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("[OnlineShop phuoc.com] Ma xac thuc OTP dang ky tai khoan");
            message.setText("Xin chao,\n\nMa OTP kich hoat tai khoan cua ban la: " + otpCode
                    + "\nMa co hieu luc trong 5 phut.\n\nTran trong,\nOnlineShop - phuoc.com");
            Transport.send(message);
        } catch (Exception e) {
            // Trong moi truong thi/khong co SMTP that, ghi log de khong lam sap ung dung
            System.err.println("[MailUtil_24162100] Khong gui duoc mail toi " + toEmail
                    + ". OTP = " + otpCode + ". Loi: " + e.getMessage());
        }
    }
}
