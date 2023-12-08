package edu.northeastern.afinal.ui.product;

import android.os.Build;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import edu.northeastern.afinal.R;
import edu.northeastern.afinal.ui.browse.BrowseFragment;
import edu.northeastern.afinal.ui.scan.ScanFragment;
import me.relex.circleindicator.CircleIndicator3;


public class ProductDetailFragment extends Fragment {
    private View root;
    private ArrayList<SliderItemCard> itemList = new ArrayList<>();
    private SliderAdapter sliderAdapter;
    private TextView textViewProductName;
    private TextView textViewProductBrand;
    private RatingBar ratingBar;
    private TextView textViewRatingCount;
    private TextView textViewProductPrice;
    private TextView detailsTextView;
    private TextView colorTextView;
    private TextView sizeTextView;
    private TextView noLoginTextView;
    private ConstraintLayout addToPlanLayout;
    private ConstraintLayout bookmarkLayout;
    private ImageButton addToPlanButton;
    private ImageButton bookmarkButton;


    public static ProductDetailFragment newInstance(String productId) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString("PRODUCT_ID", productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_product_detail, container, false);
        String productId = getArguments().getString("PRODUCT_ID", "");
        if (productId.isEmpty()) {
            Snackbar.make(root.findViewById(R.id.product_detail_view), "Error reading product.",
                    Snackbar.LENGTH_LONG).show();
            Log.e("ProductDetailFragment", "PRODUCT_ID is empty.");
            return root;
        }
        ViewPager2 productImageViewPager = root.findViewById(R.id.productImageViewPager);
        textViewProductName = root.findViewById(R.id.textViewProductName);
        textViewProductBrand = root.findViewById(R.id.textViewProductBrand);
        ratingBar = root.findViewById(R.id.ratingBar);
        textViewRatingCount = root.findViewById(R.id.textViewRatingCount);
        textViewProductPrice = root.findViewById(R.id.textViewProductPrice);
        detailsTextView = root.findViewById(R.id.detailsTextView);
        colorTextView = root.findViewById(R.id.colorTextView);
        sizeTextView = root.findViewById(R.id.sizeTextView);
        noLoginTextView = root.findViewById(R.id.noLoginTextView);
        addToPlanLayout = root.findViewById(R.id.addToPlanLayout);
        bookmarkLayout = root.findViewById(R.id.bookmarkLayout);
        addToPlanButton = root.findViewById(R.id.addToPlanButton);
        bookmarkButton = root.findViewById(R.id.bookmarkButton);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            // User is signed in
            String uid = user.getUid();
            noLoginTextView.setVisibility(View.INVISIBLE);
            addToPlanLayout.setVisibility(View.VISIBLE);
            bookmarkLayout.setVisibility(View.VISIBLE);

            // Button functions
            // add to plan button
            addToPlanButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //opens up the scan page and pass the object ID
                    NavController navController = Navigation.findNavController(requireView());
                    ScanFragment scanFragment = ScanFragment.newInstance(productId);
                    navController.navigate(R.id.action_productDetailFragment_to_scanFragment,scanFragment.getArguments());
                }
            });

            // bookmark button
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference bookmarkRef = database.getReference().child("decor-sense")
                    .child("users").child(uid).child("bookmarks").child(productId);
            bookmarkRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean isBookmarked = snapshot.exists();
                    // Update the image of the button based on the existence of the record
                    if (isBookmarked) {
                        bookmarkButton.setImageResource(R.drawable.baseline_bookmark_24);
                    } else {
                        bookmarkButton.setImageResource(R.drawable.baseline_bookmark_border_24);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle errors, if any
                }
            });

            bookmarkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    bookmarkRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // The product is bookmarked by the current user
                                // unbookmark now
//                                bookmarkButton.setImageResource(R.drawable.baseline_bookmark_24);
                                bookmarkRef.removeValue();

                            } else {
                                // The product is not bookmarked by the current user
                                // bookmark now
//                                bookmarkButton.setImageResource(R.drawable.baseline_bookmark_border_24);
                                bookmarkRef.setValue(true);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });

        } else {
            // No user signed in
            noLoginTextView.setVisibility(View.VISIBLE);
            addToPlanLayout.setVisibility(View.INVISIBLE);
            bookmarkLayout.setVisibility(View.INVISIBLE);
        }

        //get all images under /furniture/{productID}/images/
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String path = String.format("furniture/%s/images", productId);  // replace with your path
        StorageReference storageRef = storage.getReference(path);
        storageRef.listAll()
                .addOnSuccessListener(listResult -> {
                    int totalItems = listResult.getItems().size();
                    AtomicInteger counter = new AtomicInteger(0);

                    for (StorageReference item : listResult.getItems()) {
                        item.getDownloadUrl().addOnSuccessListener(uri -> {
                            // uri is the correct download URL for each item
                            String downloadUrl = uri.toString();
                            SliderItemCard sliderItemCard = new SliderItemCard(downloadUrl);
                            itemList.add(sliderItemCard);
                            // Check if all download URLs have been obtained
                            if (counter.incrementAndGet() == totalItems) {
                                // All URLs retrieved, initialize and set the adapter
                                sliderAdapter = new SliderAdapter(requireContext(), itemList);
                                productImageViewPager.setAdapter(sliderAdapter);
                                CircleIndicator3 indicator = root.findViewById(R.id.indicator);
                                indicator.setViewPager(productImageViewPager);
                                sliderAdapter.notifyDataSetChanged();
                            }

                        }).addOnFailureListener(e -> {
                            // Handle failure to get download URL
                            Log.e("ProductDetailFragment", "Error getting download URL: " + e.getMessage());
                        });
                    }
                })
                .addOnFailureListener(e -> {
                    Snackbar.make(root.findViewById(R.id.product_detail_view), "Error reading product images.",
                            Snackbar.LENGTH_LONG).show();
                    Log.e("ProductDetailFragment", "Error listing files: " + e.getMessage());
                });

        // get product info from firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference furnitureRef = database.getReference().child("decor-sense").child("furniture").child(productId);
        furnitureRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot entrySnapshot) {
                ProductItemCard productItemCard = entrySnapshot.getValue(ProductItemCard.class);
                textViewProductName.setText(productItemCard.getName());
                textViewProductBrand.setText(productItemCard.getBrand());
                ratingBar.setRating(Math.round(productItemCard.getRatings()));
                textViewRatingCount.setText(String.format("(%s)", (int) productItemCard.getReviews()));
                textViewProductPrice.setText(String.format("$%s", productItemCard.getPrice()));
                detailsTextView.setText(productItemCard.getDescription());
                colorTextView.setText(String.format("Color: %s", productItemCard.getColor()));
                sizeTextView.setText(String.format("Size: %s (H) × %s (W) × %s (D)",
                        productItemCard.getHeight(), productItemCard.getWidth(), productItemCard.getDepth()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(root.findViewById(R.id.product_detail_view), "Error getting product details.",
                        Snackbar.LENGTH_LONG).show();
                Log.e("ProductDetailFragment", "Error reading product details: " + databaseError.getMessage());
            }
        });

        return root;
    }
}
