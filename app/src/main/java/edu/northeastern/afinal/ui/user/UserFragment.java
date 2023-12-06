package edu.northeastern.afinal.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;

import edu.northeastern.afinal.InitialActivity;
import edu.northeastern.afinal.MainActivity;
import edu.northeastern.afinal.R;
import edu.northeastern.afinal.databinding.FragmentUserBinding;

public class UserFragment extends Fragment {

    private FragmentUserBinding binding;

    private Button buttonLogout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        System.out.println("UserFragment Create View");

        UserViewModel userViewModel =
                new ViewModelProvider(this).get(UserViewModel.class);

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        buttonLogout=root.findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //log out
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                // get back to InitialActivity
                Intent intent = new Intent(getActivity(), InitialActivity.class);
                startActivity(intent);
                // Finish the current MainActivity
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });

        final TextView textView = binding.textNotifications;
        userViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}