package com.phuoc.service;

import com.phuoc.dao.CartDAO_24162100;
import com.phuoc.dao.ICartDAO_24162100;
import com.phuoc.dao.IProductDAO_24162100;
import com.phuoc.dao.ProductDAO_24162100;
import com.phuoc.model.Cart_24162100;
import com.phuoc.model.CartItem_24162100;
import com.phuoc.model.Product_24162100;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/** Cai dat nghiep vu gio hang + thanh toan COD (Business Layer). */
public class CartService_24162100 implements ICartService_24162100 {

    /** Gioi han toi da cho moi lan nhap, tranh so qua lon/overflow. */
    private static final int MAX_QTY = 1000;
    private static final Pattern PHONE = Pattern.compile("^(0|\\+84)\\d{9,10}$");

    private final ICartDAO_24162100 cartDAO = new CartDAO_24162100();
    private final IProductDAO_24162100 productDAO = new ProductDAO_24162100();

    @Override
    public List<CartItem_24162100> getItems(int userId) {
        String cartId = cartDAO.findActiveCartId(userId);
        if (cartId == null) return Collections.emptyList();
        return cartDAO.findItems(cartId);
    }

    @Override
    public double getTotal(List<CartItem_24162100> items) {
        double total = 0;
        for (CartItem_24162100 it : items) total += it.getLineTotal();
        return total;
    }

    @Override
    public boolean hasProblem(List<CartItem_24162100> items) {
        for (CartItem_24162100 it : items) {
            if (it.isProblem()) return true;
        }
        return false;
    }

    /** Lay san pham dang ban, nem loi neu khong ton tai / het hang. */
    private Product_24162100 requireSellable(int productId) {
        Product_24162100 p = productDAO.findById(productId);
        if (p == null || p.getStatus() != 1) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại hoặc đã ngừng bán.");
        }
        if (p.getAmount() <= 0) {
            throw new IllegalArgumentException("Sản phẩm \"" + p.getProductName() + "\" đã hết hàng.");
        }
        return p;
    }

    private static void checkQtyRange(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Số lượng phải từ 1 trở lên (muốn bỏ sản phẩm hãy bấm Xóa).");
        }
        if (quantity > MAX_QTY) {
            throw new IllegalArgumentException("Số lượng không hợp lệ.");
        }
    }

    @Override
    public void addToCart(int userId, int productId, int quantity) {
        checkQtyRange(quantity);
        Product_24162100 p = requireSellable(productId);

        String cartId = cartDAO.findActiveCartId(userId);
        if (cartId == null) {
            try {
                cartId = cartDAO.createCart(userId);
            } catch (RuntimeException e) {
                // Bam nut 2 lan lien tiep: gio da duoc tao boi request truoc (rang buoc UNIQUE) -> lay lai gio do
                cartId = cartDAO.findActiveCartId(userId);
                if (cartId == null) throw e;
            }
        }

        int inCart = cartDAO.getQuantity(cartId, productId);
        if (inCart + quantity > p.getAmount()) {
            throw new IllegalArgumentException("Chỉ còn " + p.getAmount() + " sản phẩm \"" + p.getProductName()
                    + "\" (trong giỏ đã có " + inCart + ").");
        }
        cartDAO.addItem(cartId, productId, quantity, p.getPrice());
    }

    @Override
    public void updateQuantity(int userId, int productId, int quantity) {
        checkQtyRange(quantity);
        String cartId = cartDAO.findActiveCartId(userId);
        if (cartId == null || cartDAO.getQuantity(cartId, productId) == 0) {
            throw new IllegalArgumentException("Sản phẩm không có trong giỏ hàng.");
        }
        Product_24162100 p = requireSellable(productId);
        if (quantity > p.getAmount()) {
            throw new IllegalArgumentException("Chỉ còn " + p.getAmount() + " sản phẩm \"" + p.getProductName() + "\".");
        }
        cartDAO.setQuantity(cartId, productId, quantity);
    }

    @Override
    public void removeItem(int userId, int productId) {
        String cartId = cartDAO.findActiveCartId(userId);
        if (cartId != null) cartDAO.removeItem(cartId, productId);
    }

    @Override
    public void clearCart(int userId) {
        String cartId = cartDAO.findActiveCartId(userId);
        if (cartId != null) cartDAO.clear(cartId);
    }

    @Override
    public String checkoutCod(int userId, String receiverName, String receiverPhone,
                              String receiverAddress, String note) {
        String name = receiverName == null ? "" : receiverName.trim();
        String phone = receiverPhone == null ? "" : receiverPhone.trim().replaceAll("[\\s.\\-]", "");
        String address = receiverAddress == null ? "" : receiverAddress.trim();
        String n = note == null ? "" : note.trim();

        if (name.isEmpty() || name.length() > 100) {
            throw new IllegalArgumentException("Vui lòng nhập họ tên người nhận (tối đa 100 ký tự).");
        }
        if (!PHONE.matcher(phone).matches()) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ (ví dụ: 0901234567).");
        }
        if (address.isEmpty() || address.length() > 300) {
            throw new IllegalArgumentException("Vui lòng nhập địa chỉ giao hàng (tối đa 300 ký tự).");
        }
        if (n.length() > 300) {
            throw new IllegalArgumentException("Ghi chú tối đa 300 ký tự.");
        }

        String cartId = cartDAO.findActiveCartId(userId);
        if (cartId == null) {
            throw new IllegalArgumentException("Giỏ hàng đang trống.");
        }
        try {
            cartDAO.placeOrder(cartId, name, phone, address, n);
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return cartId;
    }

    @Override
    public Cart_24162100 getOrder(int userId, String orderId) {
        if (orderId == null || orderId.isBlank()) return null;
        return cartDAO.findOrder(orderId, userId);
    }

    @Override
    public List<Cart_24162100> getOrderHistory(int userId, int orderStatus) {
        if (orderStatus < 0 || orderStatus > Cart_24162100.ORDER_RETURNED) {
            orderStatus = 0;
        }
        return cartDAO.findOrderHistory(userId, orderStatus);
    }

    @Override
    public void cancelOrder(int userId, String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Mã đơn hàng không hợp lệ.");
        }
        try {
            cartDAO.cancelOrder(orderId.trim(), userId);
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
