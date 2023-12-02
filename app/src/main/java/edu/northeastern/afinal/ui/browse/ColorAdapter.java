package edu.northeastern.afinal.ui.browse;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.afinal.R;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder> {

    private final Context context;
    private final List<String> colorOptions;
    private SparseBooleanArray selectedItems;

    public ColorAdapter(Context context, List<String> colorOptions) {
        this.context = context;
        this.colorOptions = colorOptions;
        this.selectedItems = new SparseBooleanArray();
        // initialize all items as checked
        for (int i = 0; i < colorOptions.size(); i++) {
            selectedItems.put(i, true);
        }
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, parent, false);
        return new ColorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        String colorOption = colorOptions.get(position);
        holder.checkedTextView.setText(colorOption);
        holder.checkedTextView.setChecked(selectedItems.get(position));    }

    @Override
    public int getItemCount() {
        return colorOptions.size();
    }

    public class ColorViewHolder extends RecyclerView.ViewHolder {
        CheckedTextView checkedTextView;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            checkedTextView = itemView.findViewById(R.id.checkedTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        // Toggle the checked state for the clicked item
                        selectedItems.put(adapterPosition, !selectedItems.get(adapterPosition));
                        // Update the UI to reflect the new state
                        checkedTextView.setChecked(selectedItems.get(adapterPosition));
                    }
                }
            });
        }
    }
}
