package com.phuoc.controller;

import com.phuoc.service.IUserService_24162100;
import com.phuoc.service.UserService_24162100;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** Servlet xac thuc OTP de kich hoat tai khoan (Cau 2). */
@WebServlet("/verify-otp")
public class VerifyOtpController_24162100 extends HttpServlet {

    private final IUserService_24162100 userService = new UserService_24162100();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/verify-otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String otp = req.getParameter("otp");

        boolean ok = userService.verifyOtp(email, otp);
        if (ok) {
            req.setAttribute("message", "Kich hoat tai khoan thanh cong! Ban co the dang nhap.");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Ma OTP khong dung hoac da het han");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/WEB-INF/jsp/verify-otp.jsp").forward(req, resp);
        }
    }
}
