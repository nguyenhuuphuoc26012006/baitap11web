package com.phuoc.dao;

import com.phuoc.model.Cart_24162100;
import com.phuoc.model.CartItem_24162100;
import com.phuoc.util.DBConnection_24162100;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** Cai dat DAO cho Cart / CartItem (JDBC thuan, PreparedStatement chong SQL Injection). */
public class CartDAO_24162100 implements ICartDAO_24162100 {

    private static String newId() {
        return UUID.randomUUID().toString(); // 36 ky tu, vua cot NVARCHAR(50)
    }

    @Override
    public String findActiveCartId(int userId) {
        String sql = "SELECT TOP 1 cartId FROM Cart WHERE userId = ? AND status = 0";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public String createCart(int userId) {
        String id = newId();
        String sql = "INSERT INTO Cart (cartId, userId, buyDate, status) VALUES (?, ?, NULL, 0)";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    /**
     * Doc cac dong cua gio.
     * useSnapshotPrice = true: lay don gia da chot luc dat hang (CartItem.unitPrice);
     * false: lay gia hien tai cua san pham (gio hang dang dung).
     */
    private static List<CartItem_24162100> loadItems(Connection conn, String cartId, boolean useSnapshotPrice)
            throws Exception {
        String sql = "SELECT ci.cartItemId, ci.quantity, ci.unitPrice, ci.productId, ci.cartId, "
                + "p.productName, p.images, p.price AS currentPrice, p.amount AS available, p.status AS productStatus "
                + "FROM CartItem ci JOIN Product p ON ci.productId = p.productId "
                + "WHERE ci.cartId = ? ORDER BY p.productName, ci.productId";
        List<CartItem_24162100> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cartId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CartItem_24162100 it = new CartItem_24162100();
                    it.setCartItemId(rs.getString("cartItemId"));
                    it.setQuantity(rs.getInt("quantity"));
                    it.setUnitPrice(useSnapshotPrice ? rs.getDouble("unitPrice") : rs.getDouble("currentPrice"));
                    it.setProductId(rs.getInt("productId"));
                    it.setCartId(rs.getString("cartId"));
                    it.setProductName(rs.getString("productName"));
                    it.setImages(rs.getString("images"));
                    it.setAvailableAmount(rs.getInt("available"));
                    it.setProductStatus(rs.getInt("productStatus"));
                    list.add(it);
                }
            }
        }
        return list;
    }

    @Override
    public List<CartItem_24162100> findItems(String cartId) {
        try (Connection conn = DBConnection_24162100.getConnection()) {
            return loadItems(conn, cartId, false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getQuantity(String cartId, int productId) {
        String sql = "SELECT quantity FROM CartItem WHERE cartId = ? AND productId = ?";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cartId);
            ps.setInt(2, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public void addItem(String cartId, int productId, int quantity, double unitPrice) {
        String update = "UPDATE CartItem SET quantity = quantity + ?, unitPrice = ? WHERE cartId = ? AND productId = ?";
        String insert = "INSERT INTO CartItem (cartItemId, quantity, unitPrice, productId, cartId) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection_24162100.getConnection()) {
            int rows;
            try (PreparedStatement ps = conn.prepareStatement(update)) {
                ps.setInt(1, quantity);
                ps.setDouble(2, unitPrice);
                ps.setString(3, cartId);
                ps.setInt(4, productId);
                rows = ps.executeUpdate();
            }
            if (rows == 0) {
                try (PreparedStatement ps = conn.prepareStatement(insert)) {
                    ps.setString(1, newId());
                    ps.setInt(2, quantity);
                    ps.setDouble(3, unitPrice);
                    ps.setInt(4, productId);
                    ps.setString(5, cartId);
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setQuantity(String cartId, int productId, int quantity) {
        String sql = "UPDATE CartItem SET quantity = ? WHERE cartId = ? AND productId = ?";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setString(2, cartId);
            ps.setInt(3, productId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeItem(String cartId, int productId) {
        String sql = "DELETE FROM CartItem WHERE cartId = ? AND productId = ?";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cartId);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear(String cartId) {
        String sql = "DELETE FROM CartItem WHERE cartId = ?";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cartId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void placeOrder(String cartId, String receiverName, String receiverPhone,
                           String receiverAddress, String note) {
        Connection conn = null;
        try {
            conn = DBConnection_24162100.getConnection();
            conn.setAutoCommit(false);

            List<CartItem_24162100> items = loadItems(conn, cartId, false);
            if (items.isEmpty()) {
                throw new IllegalStateException("Giỏ hàng đang trống.");
            }

            // Tru ton kho. Dieu kien "amount >= ?" nam trong chinh cau UPDATE nen an toan khi nhieu nguoi mua cung luc.
            String decStock = "UPDATE Product SET amount = amount - ? WHERE productId = ? AND status = 1 AND amount >= ?";
            String lockPrice = "UPDATE CartItem SET unitPrice = ? WHERE cartItemId = ?";
            double total = 0;
            for (CartItem_24162100 it : items) {
                try (PreparedStatement ps = conn.prepareStatement(decStock)) {
                    ps.setInt(1, it.getQuantity());
                    ps.setInt(2, it.getProductId());
                    ps.setInt(3, it.getQuantity());
                    if (ps.executeUpdate() == 0) {
                        throw new IllegalStateException("Sản phẩm \"" + it.getProductName()
                                + "\" không đủ hàng hoặc đã ngừng bán. Vui lòng kiểm tra lại giỏ hàng.");
                    }
                }
                try (PreparedStatement ps = conn.prepareStatement(lockPrice)) {
                    ps.setDouble(1, it.getUnitPrice()); // gia hien tai, chot lai luc dat hang
                    ps.setString(2, it.getCartItemId());
                    ps.executeUpdate();
                }
                total += it.getLineTotal();
            }

            String order = "UPDATE Cart SET status = 1, buyDate = GETDATE(), receiverName = ?, receiverPhone = ?, "
                    + "receiverAddress = ?, note = ?, paymentMethod = 'COD', totalAmount = ? "
                    + "WHERE cartId = ? AND status = 0";
            try (PreparedStatement ps = conn.prepareStatement(order)) {
                ps.setString(1, receiverName);
                ps.setString(2, receiverPhone);
                ps.setString(3, receiverAddress);
                ps.setString(4, note);
                ps.setDouble(5, total);
                ps.setString(6, cartId);
                if (ps.executeUpdate() == 0) {
                    throw new IllegalStateException("Đơn hàng này đã được xử lý trước đó.");
                }
            }
            conn.commit();
        } catch (IllegalStateException e) {
            rollbackQuietly(conn);
            throw e; // loi nghiep vu: hien thi cho nguoi dung
        } catch (Exception e) {
            rollbackQuietly(conn);
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); } catch (Exception ignored) { }
            }
            DBConnection_24162100.close(conn);
        }
    }

    private static void rollbackQuietly(Connection conn) {
        try {
            if (conn != null) conn.rollback();
        } catch (Exception ignored) {
        }
    }

    @Override
    public Cart_24162100 findOrder(String cartId, int userId) {
        String sql = "SELECT cartId, userId, buyDate, status, receiverName, receiverPhone, receiverAddress, "
                + "note, paymentMethod, totalAmount FROM Cart WHERE cartId = ? AND userId = ? AND status = 1";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cartId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                Cart_24162100 c = new Cart_24162100();
                c.setCartId(rs.getString("cartId"));
                c.setUserId(rs.getInt("userId"));
                c.setBuyDate(rs.getTimestamp("buyDate"));
                c.setStatus(rs.getInt("status"));
                c.setReceiverName(rs.getString("receiverName"));
                c.setReceiverPhone(rs.getString("receiverPhone"));
                c.setReceiverAddress(rs.getString("receiverAddress"));
                c.setNote(rs.getString("note"));
                c.setPaymentMethod(rs.getString("paymentMethod"));
                c.setTotalAmount(rs.getDouble("totalAmount"));
                c.setItems(loadItems(conn, cartId, true));
                return c;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
