package com.phuoc.controller;

import com.phuoc.model.Category_24162100;
import com.phuoc.service.CategoryService_24162100;
import com.phuoc.service.ICategoryService_24162100;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet CRUD (tao, xem, cap nhat, xoa) cho bang Category, co phan trang (Cau 5).
 * Cac action: list (mac dinh), new, create, edit, update, delete.
 */
@WebServlet("/admin/categories")
public class CategoryCrudController_24162100 extends HttpServlet {

    private static final int PAGE_SIZE = 5;
    private final ICategoryService_24162100 categoryService = new CategoryService_24162100();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                req.getRequestDispatcher("/WEB-INF/jsp/admin/category-form.jsp").forward(req, resp);
                break;
            case "edit":
                int editId = Integer.parseInt(req.getParameter("id"));
                Category_24162100 category = categoryService.getById(editId);
                req.setAttribute("category", category);
                req.getRequestDispatcher("/WEB-INF/jsp/admin/category-form.jsp").forward(req, resp);
                break;
            case "delete":
                int delId = Integer.parseInt(req.getParameter("id"));
                categoryService.delete(delId);
                resp.sendRedirect(req.getContextPath() + "/admin/categories");
                break;
            default:
                listCategories(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        Category_24162100 category = new Category_24162100();
        category.setCategoryName(req.getParameter("categoryName"));
        category.setImages(req.getParameter("images"));
        category.setStatus(parseIntOrDefault(req.getParameter("status"), 1));

        if ("create".equals(action)) {
            categoryService.create(category);
        } else if ("update".equals(action)) {
            category.setCategoryId(Integer.parseInt(req.getParameter("categoryId")));
            categoryService.update(category);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/categories");
    }

    private void listCategories(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = parseIntOrDefault(req.getParameter("page"), 1);
        if (page < 1) page = 1;
        List<Category_24162100> categories = categoryService.getPage(page, PAGE_SIZE);
        int total = categoryService.countAll();
        int totalPages = (int) Math.ceil(total / (double) PAGE_SIZE);

        req.setAttribute("categories", categories);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.getRequestDispatcher("/WEB-INF/jsp/admin/category-list.jsp").forward(req, resp);
    }

    private int parseIntOrDefault(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }
}
