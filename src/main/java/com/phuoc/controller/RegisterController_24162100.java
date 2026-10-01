package com.phuoc.controller;

import com.phuoc.model.Users_24162100;
import com.phuoc.service.IUserService_24162100;
import com.phuoc.service.UserService_24162100;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet xu ly Dang ky tai khoan (Cau 2).
 * Sau khi dang ky thanh cong se chuyen sang trang nhap OTP.
 */
@WebServlet("/register")
public class RegisterController_24162100 extends HttpServlet {

    private final IUserService_24162100 userService = new UserService_24162100();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        Users_24162100 user = new Users_24162100();
        user.setUsername(username);
        user.setEmail(email);
        user.setFullname(fullname);
        user.setPhone(phone);
        user.setRoleId(2); // USER

        String error = userService.register(user, password);
        if (error != null) {
            req.setAttribute("error", error);
            req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
            return;
        }
        req.setAttribute("email", email);
        req.setAttribute("message", "Dang ky thanh cong! Vui long kiem tra email de lay ma OTP kich hoat.");
        req.getRequestDispatcher("/WEB-INF/jsp/verify-otp.jsp").forward(req, resp);
    }
}
