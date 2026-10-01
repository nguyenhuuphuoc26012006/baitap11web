package com.phuoc.model;

import java.sql.Date;

/** Model tuong ung bang Product. */
public class Product_24162100 {
    private int productId;
    private String productName;
    private long productCode;
    private int categoryId;
    private String description;
    private double price;
    private int amount;
    private int stock;
    private String images;
    private int wishlist;
    private int status;
    private Date createDate;
    private int sellerId;

    // Truong tien ich hien thi (JOIN), khong anh xa truc tiep cot DB
    private String categoryName;
    private String sellerName;

    public Product_24162100() {
    }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public long getProductCode() { return productCode; }
    public void setProductCode(long productCode) { this.productCode = productCode; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }

    public int getWishlist() { return wishlist; }
    public void setWishlist(int wishlist) { this.wishlist = wishlist; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    public int getSellerId() { return sellerId; }
    public void setSellerId(int sellerId) { this.sellerId = sellerId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getSellerName() { return sellerName; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }
}
