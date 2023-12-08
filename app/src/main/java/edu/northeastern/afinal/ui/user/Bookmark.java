package edu.northeastern.afinal.ui.user;

public class Bookmark {

    private String productId;
    private String imageUrl;
    private String productName;

    public Bookmark(String productId, String imageUrl, String productName) {
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.productName = productName;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getProductName() {
        return productName;
    }

}
