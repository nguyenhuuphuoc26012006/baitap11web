package com.phuoc.dao;

import com.phuoc.model.Product_24162100;
import com.phuoc.model.Seller_24162100;
import java.util.List;
import java.util.Map;

/** Interface DAO cho bang Product. */
public interface IProductDAO_24162100 {
    /** Danh sach san pham, gom nhom theo Seller (dung cho Cau 3). */
    Map<Integer, List<Product_24162100>> findAllGroupedBySeller();
    Product_24162100 findById(int productId);

    // ----- CRUD cho Admin -----
    List<Product_24162100> findAll(int offset, int pageSize);
    int countAll();
    boolean insert(Product_24162100 product);
    boolean update(Product_24162100 product);
    boolean delete(int productId);
    List<Seller_24162100> findAllSellers();
}