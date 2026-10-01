package com.phuoc.controller;

import com.phuoc.model.Users_24162100;
import com.phuoc.service.IUserService_24162100;
import com.phuoc.service.UserService_24162100;
import com.phuoc.util.AuthUtil_24162100;
import com.phuoc.util.JwtUtil_24162100;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet xu ly Dang nhap, dung JWT (luu trong cookie HttpOnly) thay cho Session (Cau 2).
 * Dang nhap thanh cong: roleId = ADMIN -> trang quan tri; roleId = SELLER -> trang chu Seller;
 * con lai -> trang chu User. That bai -> quay lai trang dang nhap.
 */
@WebServlet("/login")
public class LoginController_24162100 extends HttpServlet {

    private final IUserService_24162100 userService = new UserService_24162100();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String usernameOrEmail = req.getParameter("username");
        String password = req.getParameter("password");

        Users_24162100 user = userService.login(usernameOrEmail, password);
        if (user == null) {
            req.setAttribute("error", "Sai tai khoan/mat khau hoac tai khoan chua kich hoat OTP");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            return;
        }

        // Dang nhap thanh cong -> sinh JWT va gui ve trinh duyet trong cookie HttpOnly
        String token = JwtUtil_24162100.generateToken(user);
        AuthUtil_24162100.addTokenCookie(req, resp, token);

        if (user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        } else if (user.isSeller()) {
            resp.sendRedirect(req.getContextPath() + "/products");
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}
