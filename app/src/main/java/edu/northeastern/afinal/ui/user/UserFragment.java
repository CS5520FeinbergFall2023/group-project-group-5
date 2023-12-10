package edu.northeastern.afinal.ui.user;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

    private RecyclerView recyclerViewPlans;
    private PlanAdapter planAdapter;
    private List<Plan> plansList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        System.out.println("UserFragment Create View");

        UserViewModel userViewModel =
                new ViewModelProvider(this).get(UserViewModel.class);

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerViewBookmarks = root.findViewById(R.id.recyclerViewBookmarks);
        recyclerViewBookmarks.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        loadUserData();
        loadBookmarks();

        bookmarksAdapter = new BookmarksAdapter(getContext(), bookmarksList, new BookmarksAdapter.BookmarkClickListener() {
            @Override
            public void onBookmarkClick(String productId) {
                // Navigation to ProductDetailFragment
                Bundle args = new Bundle();
                args.putString("PRODUCT_ID", productId);
                Navigation.findNavController(root).navigate(R.id.action_userFragment_to_productDetailFragment, args);
            }
        });
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


        // Inside onCreateView or onViewCreated
        recyclerViewPlans = root.findViewById(R.id.recyclerViewPlans);
        recyclerViewPlans.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        planAdapter = new PlanAdapter(getContext(), plansList, new PlanAdapter.PlanClickListener() {
            @Override
            public void onPlanClick(Plan plan) {
                showEnlargedImage(plan.getImageUrl());
            }
        });
        recyclerViewPlans.setAdapter(planAdapter);
        loadPlans();

//        final TextView textView = binding.textNotifications;
//        userViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void showEnlargedImage(String imageUrl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_custom_image, null);
        ImageView imageView = view.findViewById(R.id.dialog_imageview);

        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);

        // Use Glide to load the image into the ImageView
        Glide.with(this)
                .load(storageRef)
                .into(imageView);


        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void loadPlans() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            DatabaseReference plansRef = FirebaseDatabase.getInstance().getReference()
                    .child("decor-sense").child("plans");

            plansRef.orderByChild("user-id").equalTo(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    plansList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String imageUrl = snapshot.child("image").getValue(String.class);
                        String name = snapshot.child("name").getValue(String.class);
                        //String furnitureId = snapshot.child("furniture-id").getValue(String.class);

                        Long furnitureIdLong = snapshot.child("furniture-id").getValue(Long.class);
                        String furnitureId = furnitureIdLong != null ? String.valueOf(furnitureIdLong) : null;

                        Log.d("UserFragment", "Plan ID: " + snapshot.getKey());
                        Log.d("UserFragment", "Image URL: " + imageUrl);
                        Log.d("UserFragment", "Name: " + name);
                        Log.d("UserFragment", "Furniture ID: " + furnitureId);


                        if (imageUrl != null && name != null && furnitureId != null) {
                            plansList.add(new Plan(snapshot.getKey(), imageUrl, name, furnitureId));
                        }
                    }
                    if (plansList.isEmpty()) {
                        Log.d("UserFragment", "No plans found for user: " + uid);
                    } else {
                        planAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("UserFragment", "Error loading plans.", databaseError.toException());
                }
            });
        }
    }


    private void loadUserData() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("decor-sense").child("users").child(uid);


            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String username = dataSnapshot.child("username").getValue(String.class);

                    Log.d("UserFragment", "Username: " + username);
                    Log.d("UserFragment", "User UID: " + user.getUid());


                    TextView usernameTextView = binding.textViewUsername;
                    usernameTextView.setText(username != null ? username : "Username");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("UserFragment", "Error fetching user data.", databaseError.toException());
                }
            });
        }
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
                                    Log.d("UserFragment", "Snapshot: " + dataSnapshot.toString());
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