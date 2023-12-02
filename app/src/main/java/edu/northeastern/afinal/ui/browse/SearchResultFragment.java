package edu.northeastern.afinal.ui.browse;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
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

import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.afinal.R;
import edu.northeastern.afinal.databinding.FragmentBrowseBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_KEYWORD = "keyword";
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
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        if (getArguments() != null) {
            keyword = getArguments().getString(ARG_KEYWORD);
        }
        colorFilterButton=view.findViewById(R.id.colorFilterButton);
        widthFilterButton=view.findViewById(R.id.widthFilterButton);
        heightFilterButton=view.findViewById(R.id.heightFilterButton);
        depthFilterButton=view.findViewById(R.id.depthFilterButton);
        spinner=view.findViewById(R.id.spinnerSorting);

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

        return view;
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



}