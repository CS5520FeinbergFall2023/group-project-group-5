package edu.northeastern.afinal.ui.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import edu.northeastern.afinal.R;

public class ProductDetailFragment extends Fragment {
    private View root;
    private ArrayList<SliderItemCard> itemList = new ArrayList<>();
    private SliderAdapter sliderAdapter;


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
                            System.out.println("Download URL: " + downloadUrl);

                            // Check if all download URLs have been obtained
                            if (counter.incrementAndGet() == totalItems) {
                                // All URLs retrieved, initialize and set the adapter
                                sliderAdapter = new SliderAdapter(requireContext(), itemList);
                                productImageViewPager.setAdapter(sliderAdapter);
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

        return root;
    }
}
