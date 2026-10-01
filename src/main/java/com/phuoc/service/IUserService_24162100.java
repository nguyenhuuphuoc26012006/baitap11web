package com.phuoc.service;

import com.phuoc.model.Users_24162100;
import java.util.List;

/** Interface Service - nghiep vu lien quan Users (dang ky, dang nhap, CRUD). */
public interface IUserService_24162100 {
    String register(Users_24162100 user, String rawPassword);
    boolean verifyOtp(String email, String otp);
    Users_24162100 login(String usernameOrEmail, String rawPassword);
    List<Users_24162100> getPage(int page, int pageSize);
    int countAll();
    Users_24162100 getById(int userId);
    boolean create(Users_24162100 user, String rawPassword);
    boolean update(Users_24162100 user);
    boolean delete(int userId);
}
