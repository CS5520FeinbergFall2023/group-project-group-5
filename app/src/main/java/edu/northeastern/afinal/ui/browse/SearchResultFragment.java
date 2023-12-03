package edu.northeastern.afinal.ui.browse;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.SparseBooleanArray;
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

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;

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
public class SearchResultFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_KEYWORD = "keyword";
    private static final String KEY_ITEM_LIST = "KEY_ITEM_LIST";
    private static final String KEY_MIN_WIDTH = "KEY_MIN_WIDTH";
    private static final String KEY_MAX_WIDTH = "KEY_MAX_WIDTH";

    private static final String KEY_MIN_HEIGHT = "KEY_MIN_HEIGHT ";
    private static final String KEY_MAX_HEIGHT = "KEY_MAX_HEIGHT ";

    private static final String KEY_MIN_DEPTH = "KEY_MIN_DEPTH";
    private static final String KEY_MAX_DEPTH = "KEY_MAX_DEPTH";
    private static final String KEY_COLOR_OPTIONS = "KEY_COLOR_OPTIONS";
    private static final String KEY_COLOR_SELECTED_ITEMS = "KEY_COLOR_SELECTED_ITEMS";

    private String minWidth = "";
    private String maxWidth = "";

    private String minDepth = "";
    private String maxDepth = "";

    private String minHeight = "";
    private String maxHeight = "";
    private ArrayList<ProductItemCard> itemList = new ArrayList<>(0);
    private ArrayList<String> colorOptions = new ArrayList<>();

    private SparseBooleanArray selectedColorItems=new SparseBooleanArray();
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
        colorFilterButton = root.findViewById(R.id.colorFilterButton);
        widthFilterButton = root.findViewById(R.id.widthFilterButton);
        heightFilterButton = root.findViewById(R.id.heightFilterButton);
        depthFilterButton = root.findViewById(R.id.depthFilterButton);
        spinner = root.findViewById(R.id.spinnerSorting);


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
        spinner.setOnItemSelectedListener(this);

        //init filter values
        initFilter(savedInstanceState);
        //fetch search result
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_ITEM_LIST)) {
            init(savedInstanceState);
        } else {
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
                            ProductItemCard productItemCard = entrySnapshot.getValue(ProductItemCard.class);
                            productItemCard.setFirebaseKey(entrySnapshot.getKey());
                            itemList.add(productItemCard);
                            if (!colorOptions.contains(productItemCard.getColor().toLowerCase())) {
                                colorOptions.add(productItemCard.getColor().toLowerCase());
                                selectedColorItems.put(selectedColorItems.size(), true);
                            }
//                            rviewAdapter.notifyItemInserted(itemList.size() - 1);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });
            //by default sort with top sellers
            itemList.sort(Comparator.comparing(ProductItemCard::getReviews).reversed());
            createRecyclerView(applyFilter(itemList));
        }

        //search bar
        SearchView searchView = root.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission (e.g., launch search results fragment)
                NavController navController = Navigation.findNavController(requireView());
                SearchResultFragment searchResultFragment = SearchResultFragment.newInstance(query);
                navController.navigate(R.id.action_searchResultFragment_self, searchResultFragment.getArguments());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search query changes
                return true;
            }
        });

        // Set click listener for the color button
        colorFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = getLayoutInflater().inflate(R.layout.color_filter_dropdown_layout, null);
                RecyclerView recyclerView = popupView.findViewById(R.id.recyclerViewColorFilter);
                ColorAdapter colorAdapter = new ColorAdapter(requireContext(), colorOptions, selectedColorItems);
                recyclerView.setAdapter(colorAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                int popupBackgroundColor = ContextCompat.getColor(requireContext(), R.color.alabaster);
                popupWindow.setBackgroundDrawable(new ColorDrawable(popupBackgroundColor));
                popupWindow.showAsDropDown(colorFilterButton);
            }
        });

        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sortType = parent.getItemAtPosition(position).toString();
        switch (sortType) {
            case "Top Sellers":
                itemList.sort(Comparator.comparing(ProductItemCard::getReviews).reversed());
                createRecyclerView(applyFilter(itemList));
                break;
            case "Price Low to High":
                itemList.sort(Comparator.comparing(ProductItemCard::getPrice));
                createRecyclerView(applyFilter(itemList));
                break;
            case "Price High to Low":
                itemList.sort(Comparator.comparing(ProductItemCard::getPrice).reversed());
                createRecyclerView(applyFilter(itemList));
                break;
            case "Top Rated":
                itemList.sort(Comparator.comparing(ProductItemCard::getRatings).reversed());
                createRecyclerView(applyFilter(itemList));
                break;
            default:
                //todo:error
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        if (anchorView == widthFilterButton) {
            editTextStart.setText(minWidth);
            editTextEnd.setText(maxWidth);
        } else if (anchorView == heightFilterButton) {
            editTextStart.setText(minHeight);
            editTextEnd.setText(maxHeight);
        } else if (anchorView == depthFilterButton) {
            editTextStart.setText(minDepth);
            editTextEnd.setText(maxDepth);
        } else {
        }
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double min = editTextStart.getText().toString().isEmpty() ? 0 : Double.parseDouble(editTextStart.getText().toString());
                double max = editTextEnd.getText().toString().isEmpty() ? Double.MAX_VALUE : Double.parseDouble(editTextEnd.getText().toString());
                // filter search result
//                ArrayList<ProductItemCard> filtered = new ArrayList<>(itemList);
                if (anchorView == widthFilterButton) {
//                    filtered.removeIf(item -> (item.getWidth() < min || item.getWidth() > max));
//                    createRecyclerView(filtered);
                    minWidth = editTextStart.getText().toString();
                    maxWidth = editTextEnd.getText().toString();
                } else if (anchorView == heightFilterButton) {
//                    filtered.removeIf(item -> (item.getHeight() < min || item.getHeight() > max));
//                    createRecyclerView(filtered);
                    minHeight = editTextStart.getText().toString();
                    maxHeight = editTextEnd.getText().toString();
                } else if (anchorView == depthFilterButton) {
//                    filtered.removeIf(item -> (item.getDepth() < min || item.getDepth() > max));
//                    createRecyclerView(filtered);
                    minDepth = editTextStart.getText().toString();
                    maxDepth = editTextEnd.getText().toString();
                } else {
                }
                //clear list and repaint
                popupWindow.dismiss();
                createRecyclerView(applyFilter(itemList));
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
        outState.putString(KEY_MIN_WIDTH, minWidth);
        outState.putString(KEY_MAX_WIDTH, maxWidth);
        outState.putString(KEY_MIN_HEIGHT, minHeight);
        outState.putString(KEY_MAX_HEIGHT, maxHeight);
        outState.putString(KEY_MIN_DEPTH, minDepth);
        outState.putString(KEY_MAX_DEPTH, maxDepth);
        outState.putStringArrayList(KEY_COLOR_OPTIONS, colorOptions);
        outState.putBooleanArray(KEY_COLOR_SELECTED_ITEMS, sparseBooleanArrayToBooleanArray(selectedColorItems));
    }

    private boolean[] sparseBooleanArrayToBooleanArray(SparseBooleanArray sparseBooleanArray) {
        int size = sparseBooleanArray.size();
        boolean[] booleanArray = new boolean[size];

        for (int i = 0; i < size; i++) {
            booleanArray[i] = sparseBooleanArray.valueAt(i);
        }

        return booleanArray;
    }

    private SparseBooleanArray booleanArrayToSparseBooleanArray(boolean[] booleanArray) {
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        for (int i = 0; i < booleanArray.length; i++) {
            sparseBooleanArray.put(i, booleanArray[i]);
        }
        return sparseBooleanArray;
    }

    private void initFilter(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_MIN_WIDTH)) {
                minWidth = savedInstanceState.getString(KEY_MIN_WIDTH);
            }
            if (savedInstanceState.containsKey(KEY_MAX_WIDTH)) {
                maxWidth = savedInstanceState.getString(KEY_MAX_WIDTH);
            }
            if (savedInstanceState.containsKey(KEY_MIN_WIDTH)) {
                minWidth = savedInstanceState.getString(KEY_MIN_WIDTH);
            }
            if (savedInstanceState.containsKey(KEY_MAX_WIDTH)) {
                maxWidth = savedInstanceState.getString(KEY_MAX_WIDTH);
            }
            if (savedInstanceState.containsKey(KEY_MIN_WIDTH)) {
                minWidth = savedInstanceState.getString(KEY_MIN_WIDTH);
            }
            if (savedInstanceState.containsKey(KEY_MAX_WIDTH)) {
                maxWidth = savedInstanceState.getString(KEY_MAX_WIDTH);
            }
            if (savedInstanceState.containsKey(KEY_COLOR_OPTIONS)) {
                colorOptions = savedInstanceState.getStringArrayList(KEY_COLOR_OPTIONS);
            }
            if (savedInstanceState.containsKey(KEY_COLOR_SELECTED_ITEMS)) {
                selectedColorItems = booleanArrayToSparseBooleanArray
                        (savedInstanceState.getBooleanArray(KEY_COLOR_SELECTED_ITEMS));
            }
        }
    }

    private ArrayList<ProductItemCard> applyFilter(ArrayList<ProductItemCard> itemList) {
        double minWidthDouble = minWidth.isEmpty() ? 0 : Double.parseDouble(minWidth);
        double maxWidthDouble = maxWidth.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxWidth);
        double minHeightDouble = minHeight.isEmpty() ? 0 : Double.parseDouble(minHeight);
        double maxHeightDouble = maxHeight.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxHeight);
        double minDepthDouble = minDepth.isEmpty() ? 0 : Double.parseDouble(minDepth);
        double maxDepthDouble = maxDepth.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxDepth);

        ArrayList<ProductItemCard> filtered = new ArrayList<>(itemList);
        filtered.removeIf(item -> (item.getWidth() < minWidthDouble || item.getWidth() > maxWidthDouble));
        filtered.removeIf(item -> (item.getHeight() < minHeightDouble || item.getHeight() > maxHeightDouble));
        filtered.removeIf(item -> (item.getDepth() < minDepthDouble || item.getDepth() > maxDepthDouble));

        //checked colors

        return filtered;
    }

    private void init(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_ITEM_LIST)) {
            itemList = savedInstanceState.getParcelableArrayList(KEY_ITEM_LIST);
        }
        createRecyclerView(applyFilter(itemList));
    }

    private void createRecyclerView(ArrayList<ProductItemCard> itemList) {
        int orientation = getResources().getConfiguration().orientation;
        rLayoutManger = (orientation == Configuration.ORIENTATION_PORTRAIT) ? new GridLayoutManager(requireContext(), 2) : new GridLayoutManager(requireContext(), 4);
        recyclerView = root.findViewById(R.id.rvSearchResult);
        recyclerView.setHasFixedSize(true);
        ProductItemClickListener productItemClickListener = new ProductItemClickListener() {
            @Override
            public void onItemClicked(String productID) {
                //opens up the corresponding product detail page
                NavController navController = Navigation.findNavController(requireView());
                ProductDetailFragment productDetailFragment = ProductDetailFragment.newInstance(productID);
                navController.navigate(R.id.action_browseFragment_to_productDetailFragment, productDetailFragment.getArguments());
            }
        };
        rviewAdapter = new ProductAdapter(requireContext(), itemList, productItemClickListener);
        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);
    }


}