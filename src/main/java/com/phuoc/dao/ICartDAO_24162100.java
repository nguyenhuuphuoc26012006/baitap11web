package com.phuoc.dao;

import com.phuoc.model.Cart_24162100;
import com.phuoc.model.CartItem_24162100;
import java.util.List;

/** Interface DAO cho bang Cart / CartItem. */
public interface ICartDAO_24162100 {
    /** Id gio hang dang dung (status = 0) cua user, null neu chua co. */
    String findActiveCartId(int userId);

    /** Tao gio hang moi (status = 0) va tra ve cartId. */
    String createCart(int userId);

    /** Cac dong trong gio; don gia lay theo gia hien tai cua san pham. */
    List<CartItem_24162100> findItems(String cartId);

    /** So luong san pham dang co trong gio (0 neu chua co). */
    int getQuantity(String cartId, int productId);

    /** Them san pham: neu da co thi cong don so luong. */
    void addItem(String cartId, int productId, int quantity, double unitPrice);

    void setQuantity(String cartId, int productId, int quantity);

    void removeItem(String cartId, int productId);

    void clear(String cartId);

    /**
     * Dat hang COD trong 1 transaction: tru ton kho, chot don gia, doi status gio -> da dat.
     * @throws IllegalStateException neu het hang / gio rong / don da duoc xu ly (message hien thi cho user)
     */
    void placeOrder(String cartId, String receiverName, String receiverPhone,
                    String receiverAddress, String note);

    /** Don hang da dat cua user (status = 1), kem cac dong voi gia luc dat; null neu khong co. */
    Cart_24162100 findOrder(String cartId, int userId);

    /** Lịch sử đơn hàng của user; orderStatus = 0 để lấy tất cả. */
    List<Cart_24162100> findOrderHistory(int userId, int orderStatus);

    /** Huy don hang: doi orderStatus -> 7 va hoan lai ton kho trong cung transaction. */
    void cancelOrder(String cartId, int userId);
}
