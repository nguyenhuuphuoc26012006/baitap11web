package com.phuoc.dao;

import com.phuoc.model.Category_24162100;
import com.phuoc.util.DBConnection_24162100;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/** Cai dat DAO cho bang Category (JDBC thuan). */
public class CategoryDAO_24162100 implements ICategoryDAO_24162100 {

    private Category_24162100 mapRow(ResultSet rs) throws Exception {
        Category_24162100 c = new Category_24162100();
        c.setCategoryId(rs.getInt("categoryId"));
        c.setCategoryName(rs.getString("categoryName"));
        c.setImages(rs.getString("images"));
        c.setStatus(rs.getInt("status"));
        return c;
    }

    @Override
    public List<Category_24162100> findAll(int offset, int pageSize) {
        List<Category_24162100> list = new ArrayList<>();
        String sql = "SELECT * FROM Category ORDER BY categoryId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Category_24162100> findAllNoPaging() {
        List<Category_24162100> list = new ArrayList<>();
        String sql = "SELECT * FROM Category ORDER BY categoryId";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM Category";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Category_24162100 findById(int categoryId) {
        String sql = "SELECT * FROM Category WHERE categoryId = ?";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Category_24162100 category) {
        String sql = "INSERT INTO Category (categoryName, images, status) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getImages());
            ps.setInt(3, category.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Category_24162100 category) {
        String sql = "UPDATE Category SET categoryName=?, images=?, status=? WHERE categoryId=?";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getImages());
            ps.setInt(3, category.getStatus());
            ps.setInt(4, category.getCategoryId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int categoryId) {
        String sql = "DELETE FROM Category WHERE categoryId = ?";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
