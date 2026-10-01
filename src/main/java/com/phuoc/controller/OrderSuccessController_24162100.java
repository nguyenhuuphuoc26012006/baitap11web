package com.phuoc.controller;

import com.phuoc.model.Cart_24162100;
import com.phuoc.model.Users_24162100;
import com.phuoc.service.CartService_24162100;
import com.phuoc.service.ICartService_24162100;
import com.phuoc.util.AuthUtil_24162100;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** Trang xac nhan sau khi dat hang COD thanh cong (chi chu don moi xem duoc). */
@WebServlet("/order-success")
public class OrderSuccessController_24162100 extends HttpServlet {

    private final ICartService_24162100 cartService = new CartService_24162100();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Users_24162100 user = AuthUtil_24162100.getCurrentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        Cart_24162100 order = cartService.getOrder(user.getUserId(), req.getParameter("id"));
        if (order == null) {
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }
        req.setAttribute("order", order);
        req.getRequestDispatcher("/WEB-INF/jsp/order-success.jsp").forward(req, resp);
    }
}
