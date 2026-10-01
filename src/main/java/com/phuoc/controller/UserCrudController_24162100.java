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
import java.util.List;

/**
 * Servlet CRUD (tao, xem, cap nhat, xoa) cho bang User, co phan trang (Cau 5).
 * Cac action: list (mac dinh), new, create, edit, update, delete.
 */
@WebServlet("/admin/users")
public class UserCrudController_24162100 extends HttpServlet {

    private static final int PAGE_SIZE = 5;
    private final IUserService_24162100 userService = new UserService_24162100();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                req.getRequestDispatcher("/WEB-INF/jsp/admin/user-form.jsp").forward(req, resp);
                break;
            case "edit":
                int editId = Integer.parseInt(req.getParameter("id"));
                Users_24162100 user = userService.getById(editId);
                req.setAttribute("user", user);
                req.getRequestDispatcher("/WEB-INF/jsp/admin/user-form.jsp").forward(req, resp);
                break;
            case "delete":
                int delId = Integer.parseInt(req.getParameter("id"));
                userService.delete(delId);
                resp.sendRedirect(req.getContextPath() + "/admin/users");
                break;
            default:
                listUsers(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        Users_24162100 user = new Users_24162100();
        user.setUsername(req.getParameter("username"));
        user.setEmail(req.getParameter("email"));
        user.setFullname(req.getParameter("fullname"));
        user.setPhone(req.getParameter("phone"));
        user.setImages(req.getParameter("images"));
        user.setStatus(parseIntOrDefault(req.getParameter("status"), 1));
        user.setRoleId(parseIntOrDefault(req.getParameter("roleId"), 2));

        if ("create".equals(action)) {
            String password = req.getParameter("password");
            userService.create(user, password);
        } else if ("update".equals(action)) {
            user.setUserId(Integer.parseInt(req.getParameter("userId")));
            String password = req.getParameter("password");
            if (password != null && !password.isBlank()) {
                user.setPassword(com.phuoc.util.PasswordUtil_24162100.hash(password));
            } else {
                Users_24162100 old = userService.getById(user.getUserId());
                user.setPassword(old.getPassword());
            }
            userService.update(user);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }

    private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = parseIntOrDefault(req.getParameter("page"), 1);
        if (page < 1) page = 1;
        List<Users_24162100> users = userService.getPage(page, PAGE_SIZE);
        int total = userService.countAll();
        int totalPages = (int) Math.ceil(total / (double) PAGE_SIZE);

        req.setAttribute("users", users);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.getRequestDispatcher("/WEB-INF/jsp/admin/user-list.jsp").forward(req, resp);
    }

    private int parseIntOrDefault(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }
}
