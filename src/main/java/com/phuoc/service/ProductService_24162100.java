package com.phuoc.service;

import com.phuoc.dao.IProductDAO_24162100;
import com.phuoc.dao.ProductDAO_24162100;
import com.phuoc.model.Product_24162100;
import com.phuoc.model.Seller_24162100;

import java.util.List;
import java.util.Map;

/** Cai dat nghiep vu cho Product (Business Layer). */
public class ProductService_24162100 implements IProductService_24162100 {

    private final IProductDAO_24162100 productDAO = new ProductDAO_24162100();

    @Override
    public Map<Integer, List<Product_24162100>> getAllGroupedBySeller() {
        return productDAO.findAllGroupedBySeller();
    }

    @Override
    public Product_24162100 getById(int productId) {
        return productDAO.findById(productId);
    }

    @Override
    public List<Product_24162100> getPage(int page, int pageSize) {
        return productDAO.findAll((page - 1) * pageSize, pageSize);
    }

    @Override
    public int countAll() {
        return productDAO.countAll();
    }

    @Override
    public boolean create(Product_24162100 product) {
        return productDAO.insert(product);
    }

    @Override
    public boolean update(Product_24162100 product) {
        return productDAO.update(product);
    }

    @Override
    public boolean delete(int productId) {
        return productDAO.delete(productId);
    }

    @Override
    public List<Seller_24162100> getAllSellers() {
        return productDAO.findAllSellers();
    }
}