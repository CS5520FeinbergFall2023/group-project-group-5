package edu.northeastern.afinal.ui.browse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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