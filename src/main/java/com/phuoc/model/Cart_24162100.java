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

    // Trang thai don hang: 1 moi, 2 da xac nhan, 3 chuan bi hang,
    // 4 van chuyen, 5 giao hang, 6 da giao, 7 huy, 8 hoan.
    public static final int ORDER_NEW = 1;
    public static final int ORDER_CONFIRMED = 2;
    public static final int ORDER_PREPARING = 3;
    public static final int ORDER_SHIPPING = 4;
    public static final int ORDER_DELIVERING = 5;
    public static final int ORDER_DELIVERED = 6;
    public static final int ORDER_CANCELLED = 7;
    public static final int ORDER_RETURNED = 8;

    private String cartId;
    private int userId;
    private Timestamp buyDate;
    private int status;
    private int orderStatus;

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

    public int getOrderStatus() { return orderStatus; }
    public void setOrderStatus(int orderStatus) { this.orderStatus = orderStatus; }

    public String getOrderStatusName() {
        switch (orderStatus) {
            case ORDER_NEW: return "Đơn hàng mới";
            case ORDER_CONFIRMED: return "Đã xác nhận";
            case ORDER_PREPARING: return "Chuẩn bị hàng";
            case ORDER_SHIPPING: return "Vận chuyển";
            case ORDER_DELIVERING: return "Giao hàng";
            case ORDER_DELIVERED: return "Đã giao";
            case ORDER_CANCELLED: return "Đơn hàng hủy";
            case ORDER_RETURNED: return "Đơn hàng hoàn";
            default: return "Không xác định";
        }
    }

    public String getOrderStatusClass() {
        switch (orderStatus) {
            case ORDER_CONFIRMED: return "status-confirmed";
            case ORDER_PREPARING: return "status-preparing";
            case ORDER_SHIPPING: return "status-shipping";
            case ORDER_DELIVERING: return "status-delivering";
            case ORDER_DELIVERED: return "status-delivered";
            case ORDER_CANCELLED: return "status-cancelled";
            case ORDER_RETURNED: return "status-returned";
            default: return "status-new";
        }
    }

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
