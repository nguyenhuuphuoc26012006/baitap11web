package com.phuoc.service;

import com.phuoc.dao.CategoryDAO_24162100;
import com.phuoc.dao.ICategoryDAO_24162100;
import com.phuoc.model.Category_24162100;

import java.util.List;

/** Cai dat nghiep vu cho Category (Business Layer). */
public class CategoryService_24162100 implements ICategoryService_24162100 {

    private final ICategoryDAO_24162100 categoryDAO = new CategoryDAO_24162100();

    @Override
    public List<Category_24162100> getPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return categoryDAO.findAll(offset, pageSize);
    }

    @Override
    public int countAll() {
        return categoryDAO.countAll();
    }

    @Override
    public Category_24162100 getById(int categoryId) {
        return categoryDAO.findById(categoryId);
    }

    @Override
    public boolean create(Category_24162100 category) {
        return categoryDAO.insert(category);
    }

    @Override
    public boolean update(Category_24162100 category) {
        return categoryDAO.update(category);
    }

    @Override
    public boolean delete(int categoryId) {
        return categoryDAO.delete(categoryId);
    }

    @Override
    public List<Category_24162100> getAll() {
        return categoryDAO.findAllNoPaging();
    }
}
