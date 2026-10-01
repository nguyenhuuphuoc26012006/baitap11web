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
import java.util.List;
import java.util.Map;

/**
 * Servlet hien thi tat ca san pham, gom theo tung Seller (Cau 3).
 */
@WebServlet("/products")
public class ProductListController_24162100 extends HttpServlet {

    private final IProductService_24162100 productService = new ProductService_24162100();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<Integer, List<Product_24162100>> grouped = productService.getAllGroupedBySeller();
        req.setAttribute("groupedProducts", grouped);
        req.getRequestDispatcher("/WEB-INF/jsp/product-list.jsp").forward(req, resp);
    }
}
