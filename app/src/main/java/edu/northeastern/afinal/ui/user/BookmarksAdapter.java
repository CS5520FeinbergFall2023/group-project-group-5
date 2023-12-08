package edu.northeastern.afinal.ui.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.northeastern.afinal.R;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.ViewHolder> {

    private List<Bookmark> bookmarksList;
    private LayoutInflater layoutInflater;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageViewBookmarkProduct);
            textView = view.findViewById(R.id.textViewBookmarkProductName);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public BookmarksAdapter(Context context, List<Bookmark> bookmarksList) {
        this.bookmarksList = bookmarksList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_bookmark, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Bookmark bookmark = bookmarksList.get(position);
        viewHolder.getTextView().setText(bookmark.getProductName());
        Glide.with(viewHolder.getImageView().getContext())
                .load(bookmark.getImageUrl())
                .into(viewHolder.getImageView());
    }

    @Override
    public int getItemCount() {
        return bookmarksList.size();
    }
}
