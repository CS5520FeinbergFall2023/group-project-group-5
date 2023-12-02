package edu.northeastern.afinal.ui.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import edu.northeastern.afinal.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductReviewHolder> {
    private ArrayList<ProductItemCard> itemList;

    private ProductItemClickListener productItemClickListener;
    private Context context;


    public ProductAdapter(Context context, ArrayList<ProductItemCard> itemList, ProductItemClickListener productItemClickListener) {
        this.itemList = itemList;
        this.context = context;
        this.productItemClickListener = productItemClickListener;
    }


    @NonNull
    @Override
    public ProductReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_card, parent, false);
        final ProductReviewHolder holder = new ProductReviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductReviewHolder holder, int position) {
        ProductItemCard currentItem = itemList.get(position);
        holder.brandName.setText(currentItem.getBrand());
        holder.productName.setText(currentItem.getProductName());
        holder.price.setText("$" + currentItem.getPrice());
        //load image from firebase
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(currentItem.getThumbnail());
        // Use Glide to load the image
        Glide.with(context).load(storageRef).into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            // open ProductDetail fragment with corresponding product ID
            if (productItemClickListener != null) {
                productItemClickListener.onItemClicked(currentItem.getFirebaseKey());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
