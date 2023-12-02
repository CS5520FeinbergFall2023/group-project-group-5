package edu.northeastern.afinal.ui.browse;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.afinal.R;
import edu.northeastern.afinal.databinding.FragmentBrowseBinding;
import edu.northeastern.afinal.ui.product.ProductAdapter;
import edu.northeastern.afinal.ui.product.ProductDetailFragment;
import edu.northeastern.afinal.ui.product.ProductItemCard;
import edu.northeastern.afinal.ui.product.ProductItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_KEYWORD = "keyword";
    private static final String KEY_ITEM_LIST = "KEY_ITEM_LIST";
    private ArrayList<ProductItemCard> itemList=new ArrayList<>(0);
    private RecyclerView recyclerView;
    private ProductAdapter rviewAdapter;

    private RecyclerView.LayoutManager rLayoutManger;
    private FragmentBrowseBinding binding;
    private View root;

    // TODO: Rename and change types of parameters
    private String keyword;
    private Button widthFilterButton;
    private Button heightFilterButton;
    private Button depthFilterButton;
    private Button colorFilterButton;
    private ChipGroup colorChipGroup;
    private ListPopupWindow colorPopupWindow;

    private Spinner spinner;
    private String sortType;

    public SearchResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param keyword keyword.
     * @return A new instance of fragment SearchResultFragment.
     */
    public static SearchResultFragment newInstance(String keyword) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_KEYWORD, keyword);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            keyword = getArguments().getString(ARG_KEYWORD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_search_result, container, false);
        if (getArguments() != null) {
            keyword = getArguments().getString(ARG_KEYWORD);
        }
        colorFilterButton=root.findViewById(R.id.colorFilterButton);
        widthFilterButton=root.findViewById(R.id.widthFilterButton);
        heightFilterButton=root.findViewById(R.id.heightFilterButton);
        depthFilterButton=root.findViewById(R.id.depthFilterButton);
        spinner=root.findViewById(R.id.spinnerSorting);

        // Set click listener for the color button
        colorFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = getLayoutInflater().inflate(R.layout.color_filter_dropdown_layout, null);
                RecyclerView recyclerView = popupView.findViewById(R.id.recyclerViewColorFilter);
                List<String> colorOptions = new ArrayList<>();
                colorOptions.add("white");
                colorOptions.add("black");
                ColorAdapter colorAdapter = new ColorAdapter(requireContext(), colorOptions);
                recyclerView.setAdapter(colorAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                int popupBackgroundColor = ContextCompat.getColor(requireContext(), R.color.alabaster);
                popupWindow.setBackgroundDrawable(new ColorDrawable(popupBackgroundColor));
                popupWindow.showAsDropDown(colorFilterButton);
            }
        });
        widthFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSizeFilterPopupWindow(widthFilterButton);
            }
        });

        heightFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSizeFilterPopupWindow(heightFilterButton);
            }
        });

        depthFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSizeFilterPopupWindow(depthFilterButton);
            }
        });


        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.sortings_array,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //fetch search result
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_ITEM_LIST)) {
            init(savedInstanceState);
        }
        else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference furnitureRef = database.getReference().child("decor-sense").child("furniture");
            itemList.clear();
            furnitureRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                        String productName = entrySnapshot.child("name").getValue(String.class);
                        String productDescription = entrySnapshot.child("description").getValue(String.class);
                        if (productName.toLowerCase().contains(keyword.toLowerCase()) ||
                                productDescription.toLowerCase().contains(keyword.toLowerCase())) {
                            ProductItemCard productItemCard=entrySnapshot.getValue(ProductItemCard.class);
                            productItemCard.setFirebaseKey(entrySnapshot.getKey());
                            itemList.add(productItemCard);
                            rviewAdapter.notifyItemInserted(itemList.size() - 1);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });
            createRecyclerView();
        }

        //search bar
        SearchView searchView = root.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission (e.g., launch search results fragment)
                NavController navController = Navigation.findNavController(requireView());
                SearchResultFragment searchResultFragment=SearchResultFragment.newInstance(query);
                navController.navigate(R.id.action_searchResultFragment_self,searchResultFragment.getArguments());
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


    private void showColorOptionsPopupWindow() {
        //
    }

    private void showSizeFilterPopupWindow(View anchorView) {
        View popupView = getLayoutInflater().inflate(R.layout.size_filter_dropdown_layout, null);

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        EditText editTextStart = popupView.findViewById(R.id.editTextStart);
        EditText editTextEnd = popupView.findViewById(R.id.editTextEnd);
        Button buttonConfirm = popupView.findViewById(R.id.buttonConfirm);
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new DecimalDigitsInputFilter();
        editTextStart.setFilters(filters);
        editTextEnd.setFilters(filters);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String min = editTextStart.getText().toString();
                String max = editTextEnd.getText().toString();
                popupWindow.dismiss();
            }
        });

        // Show the PopupWindow below the anchorView
        popupWindow.showAsDropDown(anchorView);
    }

    //for the recycler view
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
        recyclerView = root.findViewById(R.id.rvSearchResult);
        recyclerView.setHasFixedSize(true);
        ProductItemClickListener productItemClickListener = new ProductItemClickListener() {
            @Override
            public void onItemClicked(String productID) {
                //opens up the corresponding product detail page
                NavController navController = Navigation.findNavController(requireView());
                ProductDetailFragment productDetailFragment = ProductDetailFragment.newInstance(productID);
                navController.navigate(R.id.action_browseFragment_to_productDetailFragment,productDetailFragment.getArguments());
            }
        };
        rviewAdapter = new ProductAdapter(requireContext(),itemList,productItemClickListener);
        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);
    }


}