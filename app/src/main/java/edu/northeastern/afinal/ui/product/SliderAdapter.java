package edu.northeastern.afinal.ui.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import edu.northeastern.afinal.R;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.BannerViewHolder> {
    private List<SliderItemCard> bannerImages;
    private Context context;

    public SliderAdapter(Context context, List<SliderItemCard> bannerImages) {
        this.context = context;
        this.bannerImages = bannerImages;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_slider_card, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        SliderItemCard currentItem = bannerImages.get(position);
        //load image from firebase
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(currentItem.getImgUrl());
        // Use Glide to load the image
        Glide.with(context).load(storageRef).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return bannerImages.size();
    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivSliderImage);
        }
    }
}
