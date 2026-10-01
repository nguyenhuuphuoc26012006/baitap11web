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
 * Thanh toan don hang bang COD (tra tien khi nhan hang).
 *  GET  /checkout : hien tom tat gio hang + form thong tin giao hang
 *  POST /checkout : kiem tra du lieu, tru ton kho, chot don hang trong 1 transaction
 */
@WebServlet("/checkout")
public class CheckoutController_24162100 extends HttpServlet {

    private final ICartService_24162100 cartService = new CartService_24162100();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Users_24162100 user = AuthUtil_24162100.getCurrentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (!loadSummary(req, resp, user)) return;
        req.setAttribute("receiverName", user.getFullname());
        req.getRequestDispatcher("/WEB-INF/jsp/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Users_24162100 user = AuthUtil_24162100.getCurrentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("receiverName");
        String phone = req.getParameter("receiverPhone");
        String address = req.getParameter("receiverAddress");
        String note = req.getParameter("note");

        try {
            String orderId = cartService.checkoutCod(user.getUserId(), name, phone, address, note);
            resp.sendRedirect(req.getContextPath() + "/order-success?id="
                    + URLEncoder.encode(orderId, StandardCharsets.UTF_8));
        } catch (IllegalArgumentException e) {
            // Giu lai du lieu nguoi dung vua nhap + hien loi
            if (!loadSummary(req, resp, user)) return;
            req.setAttribute("err", e.getMessage());
            req.setAttribute("receiverName", name);
            req.setAttribute("receiverPhone", phone);
            req.setAttribute("receiverAddress", address);
            req.setAttribute("note", note);
            req.getRequestDispatcher("/WEB-INF/jsp/checkout.jsp").forward(req, resp);
        }
    }

    /** Nap gio hang vao request; neu gio rong hoac co san pham loi thi chuyen ve trang gio hang. */
    private boolean loadSummary(HttpServletRequest req, HttpServletResponse resp, Users_24162100 user)
            throws IOException {
        List<CartItem_24162100> items = cartService.getItems(user.getUserId());
        String ctx = req.getContextPath();
        if (items.isEmpty()) {
            resp.sendRedirect(ctx + "/cart?err=" + URLEncoder.encode("Giỏ hàng đang trống.", StandardCharsets.UTF_8));
            return false;
        }
        if (cartService.hasProblem(items)) {
            resp.sendRedirect(ctx + "/cart?err=" + URLEncoder.encode(
                    "Giỏ hàng có sản phẩm hết hàng hoặc vượt số lượng còn lại, vui lòng chỉnh sửa trước khi thanh toán.",
                    StandardCharsets.UTF_8));
            return false;
        }
        req.setAttribute("cartItems", items);
        req.setAttribute("cartTotal", cartService.getTotal(items));
        return true;
    }
}
