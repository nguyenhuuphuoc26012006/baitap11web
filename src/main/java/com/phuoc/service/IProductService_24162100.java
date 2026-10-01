package com.phuoc.service;

import com.phuoc.model.Product_24162100;
import com.phuoc.model.Seller_24162100;
import java.util.List;
import java.util.Map;

/** Interface Service - nghiep vu hien thi san pham. */
public interface IProductService_24162100 {
    Map<Integer, List<Product_24162100>> getAllGroupedBySeller();
    Product_24162100 getById(int productId);

    // ----- CRUD cho Admin -----
    List<Product_24162100> getPage(int page, int pageSize);
    int countAll();
    boolean create(Product_24162100 product);
    boolean update(Product_24162100 product);
    boolean delete(int productId);
    List<Seller_24162100> getAllSellers();
}