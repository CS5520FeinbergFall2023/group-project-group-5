package edu.northeastern.afinal.ui.browse;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.northeastern.afinal.databinding.FragmentBrowseBinding;

public class BrowseFragment extends Fragment {

    private FragmentBrowseBinding binding;

    public BrowseFragment() {
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BrowseViewModel browseViewModel =
                new ViewModelProvider(this).get(BrowseViewModel.class);

        binding = FragmentBrowseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // hide the label bar on the top
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

//        final TextView textView = binding.textHome;
//        browseViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //when first enter the page, show recommendation products
        int[] recommendationProductIDs=new int[]{0,1,2,3,4,5};
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference furnitureRef = database.getReference().child("decor-sense").child("furniture");
        for (int id : recommendationProductIDs) {
            final int finalId = id; // Declare a final variable
            DatabaseReference productRef = furnitureRef.child(String.valueOf(finalId));
            productRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Retrieve details
                        Double price = dataSnapshot.child("price").getValue(Double.class);
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String brand = dataSnapshot.child("brand").getValue(String.class);
                        // Print or use the details as needed
                        System.out.println("ID: " + finalId + ", Price: " + price + ", Name: " + name + ", Brand: " + brand);
                    } else {
                        System.out.println("Product with ID " + finalId + " does not exist.");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("Error reading product details: " + databaseError.getMessage());
                }
            });
        }
        //if user initialize a search, show search result
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static BrowseFragment newInstance() {
        return new BrowseFragment();
    }
}