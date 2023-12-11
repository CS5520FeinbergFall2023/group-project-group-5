package edu.northeastern.afinal.ui.browse;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
    public static final String ARG_KEYWORD = "keyword";

    public static final String ARG_MIN_WIDTH = "min_width";
    public static final String ARG_MAX_WIDTH = "max_width";
    public static final String ARG_MIN_HEIGHT = "min_height";
    public static final String ARG_MAX_HEIGHT = "max_height";
    public static final String ARG_MIN_DEPTH = "min_depth";
    public static final String ARG_MAX_DEPTH = "max_depth";

    private static final String KEY_ITEM_LIST = "KEY_ITEM_LIST";
    private static final String KEY_QUERY = "KEY_QUERY";
    private static final String KEY_MIN_WIDTH = "KEY_MIN_WIDTH";
    private static final String KEY_MAX_WIDTH = "KEY_MAX_WIDTH";

    private static final String KEY_MIN_HEIGHT = "KEY_MIN_HEIGHT ";
    private static final String KEY_MAX_HEIGHT = "KEY_MAX_HEIGHT ";

    private static final String KEY_MIN_DEPTH = "KEY_MIN_DEPTH";
    private static final String KEY_MAX_DEPTH = "KEY_MAX_DEPTH";
    private static final String KEY_COLOR_OPTIONS = "KEY_COLOR_OPTIONS";
    private static final String KEY_COLOR_SELECTED_ITEMS = "KEY_COLOR_SELECTED_ITEMS";
    private static final String KEY_TAGS_LIST = "KEY_TAGS_LIST";
    private static final String KEY_CHECKED_TAGS_LIST = "KEY_CHECKED_TAGS_LIST";

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

    private String keyword;
    private Button widthFilterButton;
    private Button heightFilterButton;
    private Button depthFilterButton;
    private Button colorFilterButton;

    private SearchView searchView;
    private Spinner spinner;
    private String sortType;

    private ChipGroup chipGroup;
    private ArrayList<String> tagList=new ArrayList<>();
    private ArrayList<String> checkedTagList=new ArrayList<>();

    private ImageView icon;



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
            minWidth=getArguments().getString(ARG_MIN_WIDTH,"");
            maxWidth=getArguments().getString(ARG_MAX_WIDTH,"");
            minHeight=getArguments().getString(ARG_MIN_HEIGHT,"");
            maxHeight=getArguments().getString(ARG_MAX_HEIGHT,"");
            minDepth=getArguments().getString(ARG_MIN_DEPTH,"");
            maxDepth=getArguments().getString(ARG_MAX_WIDTH,"");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_search_result, container, false);
        if (getArguments() != null) {
            keyword = getArguments().getString(ARG_KEYWORD);
            if(keyword==null || keyword.isEmpty()) {
                Snackbar.make(root.findViewById(R.id.search_result_view), "Empty search keyword!",
                        Snackbar.LENGTH_LONG).show();
                Log.e("SearchResultFragment", "keyword is null or empty.");
                return root;
            }
        }
        else {
            Snackbar.make(root.findViewById(R.id.search_result_view), "Empty search keyword!",
                    Snackbar.LENGTH_LONG).show();
            Log.e("SearchResultFragment", "getArguments returns null.");
            return root;
        }
        colorFilterButton = root.findViewById(R.id.colorFilterButton);
        widthFilterButton = root.findViewById(R.id.widthFilterButton);
        heightFilterButton = root.findViewById(R.id.heightFilterButton);
        depthFilterButton = root.findViewById(R.id.depthFilterButton);
        spinner = root.findViewById(R.id.spinnerSorting);
        chipGroup=root.findViewById(R.id.tagChipGroup);
        icon=root.findViewById(R.id.iconImageView);
        searchView=root.findViewById(R.id.searchView);
        searchView.setQuery(keyword,false);
        searchView.setIconifiedByDefault(false);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to BrowseFragment
                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.action_searchResultFragment_to_browseFragment);
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


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.sortings_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_ITEM_LIST)) {
            init(savedInstanceState);
            initFilter(savedInstanceState);
        } else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference furnitureRef = database.getReference().child("decor-sense").child("furniture");
            itemList.clear();
            furnitureRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                            String tags=productItemCard.getTags();
                            if(!tags.isEmpty())
                            {
                                for (String tag:tags.split(","))
                                {
                                    if(!tagList.contains(tag.toLowerCase()))
                                    {
                                        tagList.add(tag.toLowerCase());
                                        addTagChip(tag.toLowerCase());
                                    }
                                }
                            }
                        }
                    }
                    itemList.sort(Comparator.comparing(ProductItemCard::getReviews).reversed());
                    createRecyclerView(applyFilter(itemList));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Snackbar.make(root.findViewById(R.id.search_result_view), "Error getting product search result.",
                            Snackbar.LENGTH_LONG).show();
                    Log.e("SearchResultFragment", "Error getting product search result: " + databaseError.getMessage());
                }
            });

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
                ColorClickListener colorClickListener=new ColorClickListener() {
                    @Override
                    public void onItemClicked() {
                        createRecyclerView(applyFilter(itemList));
                    }
                };
                colorAdapter.setColorClickListener(colorClickListener);
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

    private void addTagChip(String tag)
    {
        Chip chip = new Chip(getContext());
        chip.setText(tag);
        chip.setCheckable(true);
        chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String chipText = chip.getText().toString();
                if(isChecked)
                {
                    if(!checkedTagList.contains(chipText))
                    {
                        checkedTagList.add(chipText);
                    }
                }
                else {
                    if(checkedTagList.contains(chipText))
                    {
                        checkedTagList.remove(chipText);
                    }
                }
                createRecyclerView(applyFilter(itemList));
            }
        });

        chipGroup.addView(chip);
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
                // filter search result
                if (anchorView == widthFilterButton) {
                    minWidth = editTextStart.getText().toString();
                    maxWidth = editTextEnd.getText().toString();
                } else if (anchorView == heightFilterButton) {
                    minHeight = editTextStart.getText().toString();
                    maxHeight = editTextEnd.getText().toString();
                } else if (anchorView == depthFilterButton) {
                    minDepth = editTextStart.getText().toString();
                    maxDepth = editTextEnd.getText().toString();
                } else {
                }
                popupWindow.dismiss();
                createRecyclerView(applyFilter(itemList));
            }
        });

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
        outState.putStringArrayList(KEY_CHECKED_TAGS_LIST, checkedTagList);
        outState.putStringArrayList(KEY_TAGS_LIST, tagList);
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
            if (savedInstanceState.containsKey(KEY_MIN_HEIGHT)) {
                minHeight = savedInstanceState.getString(KEY_MIN_HEIGHT);
            }
            if (savedInstanceState.containsKey(KEY_MAX_HEIGHT)) {
                maxHeight = savedInstanceState.getString(KEY_MAX_HEIGHT);
            }
            if (savedInstanceState.containsKey(KEY_MIN_DEPTH)) {
                minDepth = savedInstanceState.getString(KEY_MIN_DEPTH);
            }
            if (savedInstanceState.containsKey(KEY_MAX_DEPTH)) {
                maxDepth = savedInstanceState.getString(KEY_MAX_DEPTH);
            }
            if (savedInstanceState.containsKey(KEY_COLOR_OPTIONS)) {
                colorOptions = savedInstanceState.getStringArrayList(KEY_COLOR_OPTIONS);
            }
            if (savedInstanceState.containsKey(KEY_COLOR_SELECTED_ITEMS)) {
                selectedColorItems = booleanArrayToSparseBooleanArray
                        (savedInstanceState.getBooleanArray(KEY_COLOR_SELECTED_ITEMS));
            }
            if (savedInstanceState.containsKey(KEY_TAGS_LIST)) {
                tagList = savedInstanceState.getStringArrayList(KEY_TAGS_LIST);
            }
            if (savedInstanceState.containsKey(KEY_CHECKED_TAGS_LIST)) {
                checkedTagList = savedInstanceState.getStringArrayList(KEY_CHECKED_TAGS_LIST);
            }
            if(!tagList.isEmpty()) {
                for (String tag : tagList) {
                    addTagChip(tag);
                }
                //restore tag selections
                if(!checkedTagList.isEmpty()) {
                    for (int i = 0; i < chipGroup.getChildCount(); i++) {
                        View child = chipGroup.getChildAt(i);
                        if (child instanceof Chip) {
                            Chip chip = ((Chip) child);
                            chip.setChecked(checkedTagList.contains(chip.getText()));
                        }
                    }
                }
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
        ArrayList<String> colorsToBeRemoved=new ArrayList<>();
        for(int i=0;i<colorOptions.size();i++)
        {
            if(!selectedColorItems.get(i))
            {
                colorsToBeRemoved.add(colorOptions.get(i));
            }
        }
        filtered.removeIf(item -> colorsToBeRemoved.contains(item.getColor().toLowerCase()));
        //checked tags
        filtered.removeIf(item -> !ifHasTag(item.getTags(),checkedTagList));

        return filtered;
    }

    private boolean ifHasTag(String tags, ArrayList<String> desiredTags)
    {
        if(tags.isEmpty())
        {
            return false;
        }
        if(desiredTags.isEmpty())
        {
            return true;
        }
        for(String desiredTag:desiredTags)
        {
            if(Arrays.asList(tags.split(",")).contains(desiredTag))
            {
                return true;
            }
        }
        return false;
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
        ProductItemClickListener productItemClickListener = new ProductItemClickListener() {
            @Override
            public void onItemClicked(String productID) {
                //opens up the corresponding product detail page
                NavController navController = Navigation.findNavController(requireView());
                ProductDetailFragment productDetailFragment = ProductDetailFragment.newInstance(productID);
                navController.navigate(R.id.action_searchResultFragment_to_productDetailFragment, productDetailFragment.getArguments());
            }
        };
        rviewAdapter = new ProductAdapter(requireContext(), itemList, productItemClickListener);
        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);
    }


}