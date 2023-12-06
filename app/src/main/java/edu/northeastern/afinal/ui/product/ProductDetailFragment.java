package edu.northeastern.afinal.ui.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

import edu.northeastern.afinal.R;
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

        ViewPager2 productImageViewPager = root.findViewById(R.id.productImageViewPager);
        textViewProductName=root.findViewById(R.id.textViewProductName);
        textViewProductBrand=root.findViewById(R.id.textViewProductBrand);
        ratingBar=root.findViewById(R.id.ratingBar);
        textViewRatingCount=root.findViewById(R.id.textViewRatingCount);
        textViewProductPrice=root.findViewById(R.id.textViewProductPrice);
        detailsTextView=root.findViewById(R.id.detailsTextView);
        colorTextView=root.findViewById(R.id.colorTextView);
        sizeTextView=root.findViewById(R.id.sizeTextView);

        //get all images under /furniture/{productID}/images/
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String path = String.format("furniture/%s/images",productId);  // replace with your path
        StorageReference storageRef = storage.getReference(path);
        storageRef.listAll()
                .addOnSuccessListener(listResult -> {
                    int totalItems = listResult.getItems().size();
                    AtomicInteger counter = new AtomicInteger(0);

                    for (StorageReference item : listResult.getItems()) {
                        item.getDownloadUrl().addOnSuccessListener(uri -> {
                            // uri is the correct download URL for each item
                            String downloadUrl = uri.toString();
                            SliderItemCard sliderItemCard=new SliderItemCard(downloadUrl);
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
                            System.err.println("Error getting download URL: " + e.getMessage());
                        });
                    }
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error listing files: " + e.getMessage());
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
                    ratingBar.setRating((float) productItemCard.getRatings());
                    textViewRatingCount.setText(String.format("(%s)",(int)productItemCard.getReviews()));
                    textViewProductPrice.setText(String.format("$%s",productItemCard.getPrice()));
                    detailsTextView.setText(productItemCard.getDescription());
                    colorTextView.setText(String.format("Color: %s",productItemCard.getColor()));
                    sizeTextView.setText(String.format("Size: %s (H) × %s (W) × %s (D)",productItemCard.getHeight(),productItemCard.getWidth(),productItemCard.getDepth()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // todo: Handle errors
            }
        });

        return root;
    }
}
