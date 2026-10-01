package com.phuoc.service;

import com.phuoc.model.Cart_24162100;
import com.phuoc.model.CartItem_24162100;
import java.util.List;

/**
 * Interface Service - nghiep vu gio hang va thanh toan COD.
 * Loi nghiep vu (het hang, vuot so luong, du lieu sai...) duoc nem ra la IllegalArgumentException
 * voi message tieng Viet de Controller hien thi cho nguoi dung.
 */
public interface ICartService_24162100 {
    List<CartItem_24162100> getItems(int userId);

    double getTotal(List<CartItem_24162100> items);

    boolean hasProblem(List<CartItem_24162100> items);

    void addToCart(int userId, int productId, int quantity);

    void updateQuantity(int userId, int productId, int quantity);

    void removeItem(int userId, int productId);

    void clearCart(int userId);

    /** Dat hang COD, tra ve ma don hang (cartId). */
    String checkoutCod(int userId, String receiverName, String receiverPhone, String receiverAddress, String note);

    Cart_24162100 getOrder(int userId, String orderId);
}
