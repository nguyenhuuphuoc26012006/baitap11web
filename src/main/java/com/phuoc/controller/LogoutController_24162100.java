package com.phuoc.controller;

import com.phuoc.util.AuthUtil_24162100;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** Servlet xu ly Dang xuat: xoa cookie chua JWT (Cau 2). */
@WebServlet("/logout")
public class LogoutController_24162100 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthUtil_24162100.clearTokenCookie(req, resp);
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
