package com.phuoc.service;

import com.phuoc.model.Category_24162100;
import java.util.List;

/** Interface Service - nghiep vu CRUD Category co phan trang. */
public interface ICategoryService_24162100 {
    List<Category_24162100> getPage(int page, int pageSize);
    int countAll();
    Category_24162100 getById(int categoryId);
    boolean create(Category_24162100 category);
    boolean update(Category_24162100 category);
    boolean delete(int categoryId);
    List<Category_24162100> getAll();
}
