package com.phuoc.dao;

import com.phuoc.model.Category_24162100;
import java.util.List;

/** Interface DAO cho bang Category. */
public interface ICategoryDAO_24162100 {
    List<Category_24162100> findAll(int offset, int pageSize);
    int countAll();
    Category_24162100 findById(int categoryId);
    boolean insert(Category_24162100 category);
    boolean update(Category_24162100 category);
    boolean delete(int categoryId);
    List<Category_24162100> findAllNoPaging();
}
