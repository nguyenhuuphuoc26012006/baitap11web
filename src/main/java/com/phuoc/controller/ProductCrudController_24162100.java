package com.phuoc.controller;

import com.phuoc.model.Product_24162100;
import com.phuoc.service.CategoryService_24162100;
import com.phuoc.service.ICategoryService_24162100;
import com.phuoc.service.IProductService_24162100;
import com.phuoc.service.ProductService_24162100;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet CRUD (tao, xem, cap nhat, xoa) cho bang Product, co phan trang. Chi ADMIN moi vao duoc (AdminAuthFilter).
 * Cac action: list (mac dinh), new, create, edit, update, delete.
 */
@WebServlet("/admin/products")
public class ProductCrudController_24162100 extends HttpServlet {

    private static final int PAGE_SIZE = 5;
    private final IProductService_24162100 productService = new ProductService_24162100();
    private final ICategoryService_24162100 categoryService = new CategoryService_24162100();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                showForm(req, resp, null);
                break;
            case "edit":
                Product_24162100 product = productService.getById(parseIntOrDefault(req.getParameter("id"), 0));
                if (product == null) {
                    resp.sendRedirect(req.getContextPath() + "/admin/products");
                    return;
                }
                showForm(req, resp, product);
                break;
            case "delete":
                boolean ok = productService.delete(parseIntOrDefault(req.getParameter("id"), 0));
                resp.sendRedirect(req.getContextPath() + "/admin/products" + (ok ? "" : "?error=delete"));
                break;
            default:
                listProducts(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        Product_24162100 p = new Product_24162100();
        p.setProductName(req.getParameter("productName"));
        p.setProductCode(parseLongOrDefault(req.getParameter("productCode"), 0));
        p.setCategoryId(parseIntOrDefault(req.getParameter("categoryId"), 0));
        p.setDescription(req.getParameter("description"));
        p.setPrice(parseDoubleOrDefault(req.getParameter("price"), 0));
        p.setAmount(parseIntOrDefault(req.getParameter("amount"), 0));
        p.setStock(parseIntOrDefault(req.getParameter("stock"), 0));
        p.setImages(req.getParameter("images"));
        p.setStatus(parseIntOrDefault(req.getParameter("status"), 1));
        p.setSellerId(parseIntOrDefault(req.getParameter("sellerId"), 0));

        boolean ok = false;
        if ("create".equals(action)) {
            ok = productService.create(p);
        } else if ("update".equals(action)) {
            p.setProductId(parseIntOrDefault(req.getParameter("productId"), 0));
            ok = productService.update(p);
        }

        if (!ok) {
            // Luu that bai: quay lai form, giu nguyen du lieu vua nhap
            req.setAttribute("error", "Khong the luu san pham. Kiem tra lai du lieu (danh muc/cua hang phai hop le).");
            req.setAttribute("product", p);
            req.setAttribute("isEdit", "update".equals(action));
            req.setAttribute("categories", categoryService.getAll());
            req.setAttribute("sellers", productService.getAllSellers());
            req.getRequestDispatcher("/WEB-INF/jsp/admin/product-form.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/admin/products");
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, Product_24162100 product)
            throws ServletException, IOException {
        req.setAttribute("product", product);
        req.setAttribute("isEdit", product != null);
        req.setAttribute("categories", categoryService.getAll());
        req.setAttribute("sellers", productService.getAllSellers());
        req.getRequestDispatcher("/WEB-INF/jsp/admin/product-form.jsp").forward(req, resp);
    }

    private void listProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = parseIntOrDefault(req.getParameter("page"), 1);
        if (page < 1) page = 1;
        List<Product_24162100> products = productService.getPage(page, PAGE_SIZE);
        int total = productService.countAll();
        int totalPages = (int) Math.ceil(total / (double) PAGE_SIZE);

        req.setAttribute("products", products);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.getRequestDispatcher("/WEB-INF/jsp/admin/product-list.jsp").forward(req, resp);
    }

    private int parseIntOrDefault(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; }
    }

    private long parseLongOrDefault(String s, long def) {
        try { return Long.parseLong(s.trim()); } catch (Exception e) { return def; }
    }

    private double parseDoubleOrDefault(String s, double def) {
        try { return Double.parseDouble(s.trim()); } catch (Exception e) { return def; }
    }
}