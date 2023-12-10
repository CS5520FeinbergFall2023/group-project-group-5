package edu.northeastern.afinal.ui.user;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import edu.northeastern.afinal.R;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder>{


    private List<Plan> planList;
    private LayoutInflater mInflater;


    private PlanClickListener planClickListener;

    public interface PlanClickListener {
        void onPlanClick(Plan plan);
    }

    // Constructor
    public PlanAdapter(Context context, List<Plan> planList, PlanClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.planList = planList;
        this.planClickListener = listener;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_plan, parent, false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        Plan plan = planList.get(position);

        Log.d("PlanAdapter", "Binding plan: " + plan.getTitle() + " with image URL: " + plan.getImageUrl());

        holder.getTextView().setText(plan.getTitle());

        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(plan.getImageUrl());

        // Use Glide with the storage reference
        Glide.with(holder.itemView.getContext())
                .load(storageRef)
                .error(R.drawable.outline_photo_camera_24) // Provide a default image in case of an error
                .into(holder.imageViewPlan);
    }


    @Override
    public int getItemCount() {
        return planList.size();
    }

    public class PlanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewPlan;
        TextView textViewTitle;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPlan = itemView.findViewById(R.id.imageViewPlan);
            textViewTitle = itemView.findViewById(R.id.textViewPlanTitle);
            //itemView.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (planClickListener != null) {
                        Plan plan = planList.get(getAdapterPosition());
                        planClickListener.onPlanClick(plan);
                    }
                }
            });

        }

        public ImageView getImageView() {
            return imageViewPlan;
        }

        public TextView getTextView() {
            return textViewTitle;
        }

        @Override
        public void onClick(View view) {
            if (planClickListener != null) {
                Plan plan = planList.get(getAdapterPosition());
                planClickListener.onPlanClick(plan);
            }
        }

    }

    // Item click listener interface
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
