package com.phuoc.model;

/** Model tuong ung bang CartItem (kem thong tin san pham de hien thi). */
public class CartItem_24162100 {
    private String cartItemId;
    private int quantity;
    private double unitPrice;
    private int productId;
    private String cartId;

    // Truong tien ich hien thi (JOIN Product), khong anh xa truc tiep cot CartItem
    private String productName;
    private String images;
    private int availableAmount;   // Product.amount: so luong con co the ban
    private int productStatus;     // Product.status: 1 = dang ban

    public CartItem_24162100() {
    }

    public String getCartItemId() { return cartItemId; }
    public void setCartItemId(String cartItemId) { this.cartItemId = cartItemId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getCartId() { return cartId; }
    public void setCartId(String cartId) { this.cartId = cartId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }

    public int getAvailableAmount() { return availableAmount; }
    public void setAvailableAmount(int availableAmount) { this.availableAmount = availableAmount; }

    public int getProductStatus() { return productStatus; }
    public void setProductStatus(int productStatus) { this.productStatus = productStatus; }

    /** Thanh tien = don gia x so luong. */
    public double getLineTotal() { return unitPrice * quantity; }

    /** San pham con ban duoc (dang ban va con hang). */
    public boolean isAvailable() { return productStatus == 1 && availableAmount > 0; }

    /** So luong trong gio vuot qua so luong con lai hoac san pham khong con ban. */
    public boolean isProblem() { return !isAvailable() || quantity > availableAmount; }
}
