package edu.northeastern.afinal.ui.product;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.database.PropertyName;

public class ProductItemCard implements Parcelable {

    // Use the @PropertyName annotation to map the "id" field to the Firebase key
    @PropertyName("id")
    private String firebaseKey;
    private String productName;
    private double price;
    private String brand;

    private String color;
    private String category;
    private double depth;
    private String description;

    private double height;
    private String link;
    private double ratings;
    private String thumbnail;

    private String vendor;

    private double width;
    private double reviews;

    public ProductItemCard() {
    }

    public ProductItemCard(String firebaseKey, String productName, double price, String brand, String color, String category, double depth, String description, double height, String link, double ratings, String thumbnail, String vendor, double width, double reviews) {
        this.firebaseKey = firebaseKey;
        this.productName = productName;
        this.price = price;
        this.brand = brand;
        this.color = color;
        this.category = category;
        this.depth = depth;
        this.description = description;
        this.height = height;
        this.link = link;
        this.ratings = ratings;
        this.thumbnail = thumbnail;
        this.vendor = vendor;
        this.width = width;
        this.reviews = reviews;
    }

    protected ProductItemCard(Parcel in) {
        firebaseKey = in.readString();
        productName = in.readString();
        price = in.readDouble();
        brand = in.readString();
        color = in.readString();
        category = in.readString();
        depth = in.readDouble();
        description = in.readString();
        height = in.readDouble();
        link = in.readString();
        ratings = in.readDouble();
        thumbnail = in.readString();
        vendor = in.readString();
        width = in.readDouble();
        reviews = in.readDouble();
    }

    public static final Creator<ProductItemCard> CREATOR = new Creator<ProductItemCard>() {
        @Override
        public ProductItemCard createFromParcel(Parcel in) {
            return new ProductItemCard(in);
        }

        @Override
        public ProductItemCard[] newArray(int size) {
            return new ProductItemCard[size];
        }
    };

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setReviews(double reviews) {
        this.reviews = reviews;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(firebaseKey);
        dest.writeString(productName);
        dest.writeDouble(price);
        dest.writeString(brand);
        dest.writeString(color);
        dest.writeString(category);
        dest.writeDouble(depth);
        dest.writeString(description);
        dest.writeDouble(height);
        dest.writeString(link);
        dest.writeDouble(ratings);
        dest.writeString(thumbnail);
        dest.writeString(vendor);
        dest.writeDouble(width);
        dest.writeDouble(reviews);
    }
}
