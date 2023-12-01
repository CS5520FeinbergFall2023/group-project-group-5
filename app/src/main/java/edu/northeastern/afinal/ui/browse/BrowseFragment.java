package edu.northeastern.afinal.ui.browse;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.northeastern.afinal.R;
import edu.northeastern.afinal.databinding.FragmentBrowseBinding;
import edu.northeastern.afinal.ui.product.ProductAdapter;
import edu.northeastern.afinal.ui.product.ProductDetailFragment;
import edu.northeastern.afinal.ui.product.ProductItemCard;
import edu.northeastern.afinal.ui.product.ProductItemClickListener;

public class BrowseFragment extends Fragment {
    //recommendation products
    private ArrayList<ProductItemCard> itemList=new ArrayList<>(0);
    private RecyclerView recyclerView;
    private ProductAdapter rviewAdapter;

    private RecyclerView.LayoutManager rLayoutManger;
    private FragmentBrowseBinding binding;

    private static final String KEY_ITEM_LIST = "KEY_ITEM_LIST";

    private View root;

    public BrowseFragment() {
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BrowseViewModel browseViewModel =
                new ViewModelProvider(this).get(BrowseViewModel.class);

        binding = FragmentBrowseBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        // hide the label bar on the top
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        init(savedInstanceState);
//        final TextView textView = binding.textHome;
//        browseViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //when first enter the page, show recommendation products
        String[] recommendationProductIDs=new String[]{"0","1","2","3","4","5"};
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference furnitureRef = database.getReference().child("decor-sense").child("furniture");
        for (String id : recommendationProductIDs) {
            final String finalId = id; // Declare a final variable
            DatabaseReference productRef = furnitureRef.child(finalId);
            productRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Retrieve details
                        Double price = dataSnapshot.child("price").getValue(Double.class);
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String brand = dataSnapshot.child("brand").getValue(String.class);
                        String thumbnailURL = dataSnapshot.child("thumbnail").getValue(String.class);
                        // Print or use the details as needed
                        System.out.println("ID: " + finalId + ", Price: " + price + ", Name: " + name + ", Brand: " + brand+", Thumbnail URL: "+thumbnailURL);

                        itemList.add(new ProductItemCard(finalId,name,price,brand,thumbnailURL));
                        rviewAdapter.notifyItemInserted(itemList.size()-1);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_ITEM_LIST, itemList);
    }

    private void init(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_ITEM_LIST)) {
            itemList = savedInstanceState.getParcelableArrayList(KEY_ITEM_LIST);
        }
        createRecyclerView();
    }

    private void createRecyclerView() {
        int orientation = getResources().getConfiguration().orientation;
        rLayoutManger = (orientation == Configuration.ORIENTATION_PORTRAIT)?new GridLayoutManager(requireContext(), 2):new GridLayoutManager(requireContext(), 4);
        recyclerView = root.findViewById(R.id.rvRecommendation);
        recyclerView.setHasFixedSize(true);
        ProductItemClickListener productItemClickListener = new ProductItemClickListener() {
            @Override
            public void onItemClicked(String productID) {
                //opens up the corresponding product detail page
                ProductDetailFragment productDetailFragment = ProductDetailFragment.newInstance(productID);
//                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//                transaction.replace(R.id.nav_host_fragment_activity_main, productDetailFragment);
//                transaction.addToBackStack(null);
//                transaction.commit();

                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.action_browseFragment_to_productDetailFragment);
            }
        };
        rviewAdapter = new ProductAdapter(requireContext(),itemList,productItemClickListener);

        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);
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