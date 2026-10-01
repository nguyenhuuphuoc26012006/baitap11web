package com.phuoc.dao;

import com.phuoc.model.Product_24162100;
import com.phuoc.model.Seller_24162100;
import com.phuoc.util.DBConnection_24162100;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/** Cai dat DAO cho bang Product (JDBC thuan, co JOIN Category/Seller). */
public class ProductDAO_24162100 implements IProductDAO_24162100 {

    private Product_24162100 mapRow(ResultSet rs) throws Exception {
        Product_24162100 p = new Product_24162100();
        p.setProductId(rs.getInt("productId"));
        p.setProductName(rs.getString("productName"));
        p.setProductCode(rs.getLong("productCode"));
        p.setCategoryId(rs.getInt("categoryId"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getDouble("price"));
        p.setAmount(rs.getInt("amount"));
        p.setStock(rs.getInt("stock"));
        p.setImages(rs.getString("images"));
        p.setWishlist(rs.getInt("wishlist"));
        p.setStatus(rs.getInt("status"));
        p.setCreateDate(rs.getDate("createDate"));
        p.setSellerId(rs.getInt("sellerId"));
        p.setCategoryName(rs.getString("categoryName"));
        p.setSellerName(rs.getString("sellername"));
        return p;
    }

    @Override
    public Map<Integer, List<Product_24162100>> findAllGroupedBySeller() {
        Map<Integer, List<Product_24162100>> result = new LinkedHashMap<>();
        String sql = "SELECT p.*, c.categoryName, s.sellername "
                + "FROM Product p "
                + "LEFT JOIN Category c ON p.categoryId = c.categoryId "
                + "LEFT JOIN Seller s ON p.sellerId = s.sellerId "
                + "ORDER BY p.sellerId, p.productId";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product_24162100 p = mapRow(rs);
                result.computeIfAbsent(p.getSellerId(), k -> new ArrayList<>()).add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Product_24162100 findById(int productId) {
        String sql = "SELECT p.*, c.categoryName, s.sellername "
                + "FROM Product p "
                + "LEFT JOIN Category c ON p.categoryId = c.categoryId "
                + "LEFT JOIN Seller s ON p.sellerId = s.sellerId "
                + "WHERE p.productId = ?";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================= CRUD cho Admin =================

    @Override
    public List<Product_24162100> findAll(int offset, int pageSize) {
        List<Product_24162100> list = new ArrayList<>();
        String sql = "SELECT p.*, c.categoryName, s.sellername "
                + "FROM Product p "
                + "LEFT JOIN Category c ON p.categoryId = c.categoryId "
                + "LEFT JOIN Seller s ON p.sellerId = s.sellerId "
                + "ORDER BY p.productId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
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
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM Product";
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
    public boolean insert(Product_24162100 p) {
        String sql = "INSERT INTO Product (productName, productCode, categoryId, description, price, amount, stock, "
                + "images, wishlist, status, createDate, sellerId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0, ?, ?, ?)";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getProductName());
            ps.setLong(2, p.getProductCode());
            ps.setInt(3, p.getCategoryId());
            ps.setString(4, p.getDescription());
            ps.setDouble(5, p.getPrice());
            ps.setInt(6, p.getAmount());
            ps.setInt(7, p.getStock());
            ps.setString(8, p.getImages());
            ps.setInt(9, p.getStatus());
            ps.setDate(10, new java.sql.Date(System.currentTimeMillis()));
            ps.setInt(11, p.getSellerId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Product_24162100 p) {
        String sql = "UPDATE Product SET productName=?, productCode=?, categoryId=?, description=?, price=?, "
                + "amount=?, stock=?, images=?, status=?, sellerId=? WHERE productId=?";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getProductName());
            ps.setLong(2, p.getProductCode());
            ps.setInt(3, p.getCategoryId());
            ps.setString(4, p.getDescription());
            ps.setDouble(5, p.getPrice());
            ps.setInt(6, p.getAmount());
            ps.setInt(7, p.getStock());
            ps.setString(8, p.getImages());
            ps.setInt(9, p.getStatus());
            ps.setInt(10, p.getSellerId());
            ps.setInt(11, p.getProductId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int productId) {
        String sql = "DELETE FROM Product WHERE productId = ?";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            // Vi du: san pham dang nam trong gio hang/don hang (rang buoc khoa ngoai)
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Seller_24162100> findAllSellers() {
        List<Seller_24162100> list = new ArrayList<>();
        String sql = "SELECT sellerId, sellername, images, status FROM Seller ORDER BY sellerId";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Seller_24162100(rs.getInt("sellerId"), rs.getString("sellername"),
                        rs.getString("images"), rs.getInt("status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}