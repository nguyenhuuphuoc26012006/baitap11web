package com.phuoc.controller;

import com.phuoc.model.CartItem_24162100;
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
import java.util.List;

/**
 * Gio hang: xem (GET /cart) va them / sua so luong / xoa / xoa het (POST /cart, tham so action).
 * Bat buoc dang nhap (JWT). Sau moi POST redirect (Post-Redirect-Get) kem thong bao tren URL.
 */
@WebServlet("/cart")
public class CartController_24162100 extends HttpServlet {

    private final ICartService_24162100 cartService = new CartService_24162100();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Users_24162100 user = AuthUtil_24162100.getCurrentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        List<CartItem_24162100> items = cartService.getItems(user.getUserId());
        req.setAttribute("cartItems", items);
        req.setAttribute("cartTotal", cartService.getTotal(items));
        req.setAttribute("cartHasProblem", cartService.hasProblem(items));
        req.setAttribute("msg", req.getParameter("msg"));
        req.setAttribute("err", req.getParameter("err"));
        req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Users_24162100 user = AuthUtil_24162100.getCurrentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        int productId = parseInt(req.getParameter("productId"));
        int quantity = parseInt(req.getParameter("quantity"));
        String ctx = req.getContextPath();

        try {
            if ("add".equals(action)) {
                cartService.addToCart(user.getUserId(), productId, quantity);
                resp.sendRedirect(ctx + "/cart?msg=" + enc("Đã thêm sản phẩm vào giỏ hàng."));
            } else if ("update".equals(action)) {
                cartService.updateQuantity(user.getUserId(), productId, quantity);
                resp.sendRedirect(ctx + "/cart?msg=" + enc("Đã cập nhật số lượng."));
            } else if ("remove".equals(action)) {
                cartService.removeItem(user.getUserId(), productId);
                resp.sendRedirect(ctx + "/cart?msg=" + enc("Đã xóa sản phẩm khỏi giỏ hàng."));
            } else if ("clear".equals(action)) {
                cartService.clearCart(user.getUserId());
                resp.sendRedirect(ctx + "/cart?msg=" + enc("Đã xóa toàn bộ giỏ hàng."));
            } else {
                resp.sendRedirect(ctx + "/cart");
            }
        } catch (IllegalArgumentException e) {
            // Loi nghiep vu: quay lai trang nguoi dung dang dung kem thong bao
            String back = req.getParameter("back");
            String target;
            if ("add".equals(action) && "detail".equals(back)) {
                target = ctx + "/product-detail?id=" + productId + "&err=" + enc(e.getMessage());
            } else if ("add".equals(action) && "list".equals(back)) {
                target = ctx + "/products?err=" + enc(e.getMessage());
            } else {
                target = ctx + "/cart?err=" + enc(e.getMessage());
            }
            resp.sendRedirect(target);
        }
    }

    private static int parseInt(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return 0; // 0 se bi service tu choi (so luong/ma san pham khong hop le)
        }
    }

    private static String enc(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}
