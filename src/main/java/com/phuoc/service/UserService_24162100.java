package com.phuoc.service;

import com.phuoc.dao.IUserDAO_24162100;
import com.phuoc.dao.UserDAO_24162100;
import com.phuoc.model.Users_24162100;
import com.phuoc.util.MailUtil_24162100;
import com.phuoc.util.OtpUtil_24162100;
import com.phuoc.util.PasswordUtil_24162100;

import java.util.List;

/** Cai dat nghiep vu cho Users (Business Layer). */
public class UserService_24162100 implements IUserService_24162100 {

    private final IUserDAO_24162100 userDAO = new UserDAO_24162100();

    /**
     * Dang ky tai khoan moi, kich hoat bang OTP gui qua mail.
     * @return null neu thanh cong, nguoc lai tra ve thong bao loi
     */
    @Override
    public String register(Users_24162100 user, String rawPassword) {
        if (userDAO.findByUsername(user.getUsername()) != null) {
            return "Ten dang nhap da ton tai";
        }
        if (userDAO.findByEmail(user.getEmail()) != null) {
            return "Email da duoc dang ky";
        }
        String otp = OtpUtil_24162100.generateOtp();
        user.setPassword(PasswordUtil_24162100.hash(rawPassword));
        user.setStatus(0);
        user.setCode(otp);
        if (user.getRoleId() == 0) {
            user.setRoleId(2); // mac dinh la USER
        }
        boolean ok = userDAO.insert(user);
        if (!ok) {
            return "Khong the tao tai khoan, vui long thu lai";
        }
        MailUtil_24162100.sendOtpMail(user.getEmail(), otp);
        return null;
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        return userDAO.activateByEmailAndCode(email, otp);
    }

    @Override
    public Users_24162100 login(String usernameOrEmail, String rawPassword) {
        Users_24162100 user = userDAO.findByUsername(usernameOrEmail);
        if (user == null) {
            user = userDAO.findByEmail(usernameOrEmail);
        }
        if (user == null) return null;
        if (user.getStatus() != 1) return null; // chua kich hoat OTP
        if (!PasswordUtil_24162100.matches(rawPassword, user.getPassword())) return null;
        return user;
    }

    @Override
    public List<Users_24162100> getPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return userDAO.findAll(offset, pageSize);
    }

    @Override
    public int countAll() {
        return userDAO.countAll();
    }

    @Override
    public Users_24162100 getById(int userId) {
        return userDAO.findById(userId);
    }

    @Override
    public boolean create(Users_24162100 user, String rawPassword) {
        user.setPassword(PasswordUtil_24162100.hash(rawPassword));
        if (user.getStatus() == 0) user.setStatus(1); // admin tao truc tiep thi active luon
        return userDAO.insert(user);
    }

    @Override
    public boolean update(Users_24162100 user) {
        return userDAO.update(user);
    }

    @Override
    public boolean delete(int userId) {
        return userDAO.delete(userId);
    }
}
