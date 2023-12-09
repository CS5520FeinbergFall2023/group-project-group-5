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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import edu.northeastern.afinal.R;




public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.ViewHolder> {


    public interface BookmarkClickListener {
        void onBookmarkClick(String productId);
    }
    private List<Bookmark> bookmarksList;
    private LayoutInflater layoutInflater;

    private BookmarkClickListener listener;


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

    public BookmarksAdapter(Context context, List<Bookmark> bookmarksList, BookmarkClickListener listener) {
        this.bookmarksList = bookmarksList;
        this.layoutInflater = LayoutInflater.from(context);
        this.listener = listener;
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
//        Glide.with(viewHolder.getImageView().getContext())
//                .load(bookmark.getImageUrl())
//                .into(viewHolder.getImageView());
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(bookmark.getImageUrl());
        // Use Glide to load the image
        Glide.with(viewHolder.getImageView().getContext()).load(storageRef).into(viewHolder.imageView);

        viewHolder.itemView.setOnClickListener(v -> {
            listener.onBookmarkClick(bookmark.getProductId());
        });

    }

    @Override
    public int getItemCount() {
        return bookmarksList.size();
    }
}
