package com.phuoc.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Model tuong ung bang Cart.
 * status: 0 = gio hang dang dung, 1 = da dat hang (thanh toan COD, cho giao hang).
 */
public class Cart_24162100 {
    public static final int STATUS_CART = 0;
    public static final int STATUS_ORDERED = 1;

    private String cartId;
    private int userId;
    private Timestamp buyDate;
    private int status;

    // Thong tin giao hang / thanh toan (chi co khi da dat hang)
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String note;
    private String paymentMethod;
    private double totalAmount;

    private List<CartItem_24162100> items = new ArrayList<>();

    public Cart_24162100() {
    }

    public String getCartId() { return cartId; }
    public void setCartId(String cartId) { this.cartId = cartId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Timestamp getBuyDate() { return buyDate; }
    public void setBuyDate(Timestamp buyDate) { this.buyDate = buyDate; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

    public String getReceiverPhone() { return receiverPhone; }
    public void setReceiverPhone(String receiverPhone) { this.receiverPhone = receiverPhone; }

    public String getReceiverAddress() { return receiverAddress; }
    public void setReceiverAddress(String receiverAddress) { this.receiverAddress = receiverAddress; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public List<CartItem_24162100> getItems() { return items; }
    public void setItems(List<CartItem_24162100> items) { this.items = items; }
}
