package com.phuoc.controller;

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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/** Huy don hang va giu don trong lich su voi orderStatus = 7. */
@WebServlet("/order-cancel")
public class OrderCancelController_24162100 extends HttpServlet {

    private final ICartService_24162100 cartService = new CartService_24162100();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Users_24162100 user = AuthUtil_24162100.getCurrentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.setCharacterEncoding("UTF-8");
        String orderId = req.getParameter("id");
        String ctx = req.getContextPath();

        try {
            cartService.cancelOrder(user.getUserId(), orderId);
            resp.sendRedirect(ctx + "/order-history?status=7&msg="
                    + URLEncoder.encode("Đã hủy đơn hàng thành công.", StandardCharsets.UTF_8));
        } catch (IllegalArgumentException e) {
            resp.sendRedirect(ctx + "/order-history?msg="
                    + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
        }
    }
}
