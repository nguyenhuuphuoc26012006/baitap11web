package com.phuoc.controller;

import com.phuoc.model.Product_24162100;
import com.phuoc.service.IProductService_24162100;
import com.phuoc.service.ProductService_24162100;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet hien thi chi tiet 01 san pham khi bam vao tieu de san pham (Cau 4).
 */
@WebServlet("/product-detail")
public class ProductDetailController_24162100 extends HttpServlet {

    private final IProductService_24162100 productService = new ProductService_24162100();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }
        Product_24162100 product = productService.getById(id);
        if (product == null) {
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }
        req.setAttribute("product", product);
        req.getRequestDispatcher("/WEB-INF/jsp/product-detail.jsp").forward(req, resp);
    }
}
