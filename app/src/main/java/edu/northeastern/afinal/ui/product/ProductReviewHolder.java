package edu.northeastern.afinal.ui.product;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.afinal.R;

public class ProductReviewHolder extends RecyclerView.ViewHolder {
    TextView productName;
    TextView brandName;
    TextView price;
    ImageView image;


    public ProductReviewHolder(@NonNull View itemView) {
        super(itemView);
        productName=itemView.findViewById(R.id.productNameTextView);
        brandName=itemView.findViewById(R.id.productBrandTextView);
        price=itemView.findViewById(R.id.productPriceTextView);
        image=itemView.findViewById(R.id.productImageView);

    }
}
