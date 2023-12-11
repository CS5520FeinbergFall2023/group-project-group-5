package edu.northeastern.afinal.ui.browse;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
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
        System.out.println("BrowseFragment Create View");
        BrowseViewModel browseViewModel =
                new ViewModelProvider(this).get(BrowseViewModel.class);

        binding = FragmentBrowseBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        // hiding the label bar on the top
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_ITEM_LIST)) {
            init(savedInstanceState);
        }


        else {
            String[] recommendationProductIDs = new String[]{"0", "1", "2", "3", "4", "5"};
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference furnitureRef = database.getReference().child("decor-sense").child("furniture");
            itemList.clear();
            for (String id : recommendationProductIDs) {
                final String finalId = id;
                DatabaseReference productRef = furnitureRef.child(finalId);
                productRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            ProductItemCard productItemCard=dataSnapshot.getValue(ProductItemCard.class);
                            productItemCard.setFirebaseKey(dataSnapshot.getKey());
                            itemList.add(productItemCard);
                            rviewAdapter.notifyItemInserted(itemList.size() - 1);
                        } else {
                            Log.d("BrowseFragment","Product with ID " + finalId + " does not exist.");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("BrowseFragment","Error reading product details: " + databaseError.getMessage());
                        Snackbar.make(root.findViewById(R.id.browse_view), "Error reading product details from database.",
                                Snackbar.LENGTH_LONG).show();
                    }
                });
            }
            createRecyclerView();
        }

        //search bar
        SearchView searchView = root.findViewById(R.id.searchView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission (e.g., launch search results fragment)
                NavController navController = Navigation.findNavController(requireView());
                SearchResultFragment searchResultFragment=SearchResultFragment.newInstance(query);
                navController.navigate(R.id.action_browseFragment_to_searchResultFragment,searchResultFragment.getArguments());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search query changes
                return true;
            }
        });
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
                NavController navController = Navigation.findNavController(requireView());
                ProductDetailFragment productDetailFragment = ProductDetailFragment.newInstance(productID);
                navController.navigate(R.id.action_browseFragment_to_productDetailFragment,productDetailFragment.getArguments());
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