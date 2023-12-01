package edu.northeastern.afinal.ui.product;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ProductItemCard implements Parcelable {
    private String id;
    private String productName;
    private double price;
    private String brand;

    private String imgURL;


    public ProductItemCard(String id, String productName, double price, String brand, String imgURL) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.brand = brand;
        this.imgURL = imgURL;
    }

    protected ProductItemCard(Parcel in) {
        id = in.readString();
        productName = in.readString();
        price = in.readDouble();
        brand = in.readString();
        imgURL = in.readString();
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

    public String getId() {
        return id;
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

    public String getImgURL() {
        return imgURL;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(productName);
        dest.writeDouble(price);
        dest.writeString(brand);
        dest.writeString(imgURL);
    }
}
