package com.phuoc.model;

/** Model tuong ung bang Seller. */
public class Seller_24162100 {
    private int sellerId;
    private String sellername;
    private String images;
    private int status;

    public Seller_24162100() {
    }

    public Seller_24162100(int sellerId, String sellername, String images, int status) {
        this.sellerId = sellerId;
        this.sellername = sellername;
        this.images = images;
        this.status = status;
    }

    public int getSellerId() { return sellerId; }
    public void setSellerId(int sellerId) { this.sellerId = sellerId; }

    public String getSellername() { return sellername; }
    public void setSellername(String sellername) { this.sellername = sellername; }

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}
