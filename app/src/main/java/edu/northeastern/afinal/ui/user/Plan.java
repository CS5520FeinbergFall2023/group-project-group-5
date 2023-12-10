package edu.northeastern.afinal.ui.user;

public class Plan {
    private String imageUrl;
    private String title;

    private String id;

    private String furnitureId;

    // Constructor
    public Plan(String id, String imageUrl, String title,String furnitureId) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.furnitureId = furnitureId;
    }

    public String getFurnitureId() {
        return furnitureId;
    }

    public void setFurnitureId(String furnitureId) {
        this.furnitureId = furnitureId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for imageUrl
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Getter and Setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
