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
import java.util.List;

/** Lich su dat hang cua nguoi dung, co loc theo trang thai don hang. */
@WebServlet("/order-history")
public class OrderHistoryController_24162100 extends HttpServlet {

    private final ICartService_24162100 cartService = new CartService_24162100();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Users_24162100 user = AuthUtil_24162100.getCurrentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int status = parseStatus(req.getParameter("status"));
        List<com.phuoc.model.Cart_24162100> orders =
                cartService.getOrderHistory(user.getUserId(), status);

        req.setAttribute("orders", orders);
        req.setAttribute("selectedStatus", status);
        req.getRequestDispatcher("/WEB-INF/jsp/order-history.jsp").forward(req, resp);
    }

    private static int parseStatus(String value) {
        if (value == null || value.isBlank()) return 0;
        try {
            int status = Integer.parseInt(value);
            return status >= Cart_24162100.ORDER_NEW
                    && status <= Cart_24162100.ORDER_RETURNED ? status : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
