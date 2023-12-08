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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.afinal.InitialActivity;
import edu.northeastern.afinal.MainActivity;
import edu.northeastern.afinal.R;
import edu.northeastern.afinal.databinding.FragmentUserBinding;

public class UserFragment extends Fragment {

    private FragmentUserBinding binding;

    private Button buttonLogout;
    private RecyclerView recyclerViewBookmarks;
    private BookmarksAdapter bookmarksAdapter;
    private List<Bookmark> bookmarksList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        System.out.println("UserFragment Create View");

        UserViewModel userViewModel =
                new ViewModelProvider(this).get(UserViewModel.class);

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerViewBookmarks = root.findViewById(R.id.recyclerViewBookmarks);
        recyclerViewBookmarks.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));



        loadBookmarks();
        bookmarksAdapter = new BookmarksAdapter(getContext(), bookmarksList);
        recyclerViewBookmarks.setAdapter(bookmarksAdapter);

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

//        final TextView textView = binding.textNotifications;
//        userViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    private void loadBookmarks() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            Log.d("UserFragment", "User ID: " + uid);
            DatabaseReference bookmarksRef = FirebaseDatabase.getInstance().getReference().child("decor-sense")
                    .child("users").child(uid).child("bookmarks");
            DatabaseReference furnitureRef = FirebaseDatabase.getInstance().getReference().child("decor-sense").child("furniture");

            bookmarksRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    bookmarksList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.d("UserFragment", "Processing bookmark: " + snapshot.getKey());
                        // Here 'key' is the index of the furniture item in the array
                        String key = snapshot.getKey();
                        if(snapshot.getValue(Boolean.class)){
                            // Using the key to get the furniture item details
                            furnitureRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot furnitureSnapshot) {
                                    String productName = furnitureSnapshot.child("name").getValue(String.class);
                                    String imageUrl = furnitureSnapshot.child("thumbnail").getValue(String.class);
                                    Log.d("UserFragment", "Furniture item - Name: " + productName + ", Image: " + imageUrl);
                                    bookmarksList.add(new Bookmark(key, imageUrl, productName));
                                    bookmarksAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e("UserFragment", "Error fetching furniture details.", error.toException());
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("UserFragment", "Failed to read bookmarks.", databaseError.toException());
                }
            });
        }

    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}