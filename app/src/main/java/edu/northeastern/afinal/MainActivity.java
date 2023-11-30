package edu.northeastern.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import edu.northeastern.afinal.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        String userEmail = getIntent().getStringExtra("email");
//        // show login success toast
//        Toast.makeText(MainActivity.this, "Welcome " + userEmail,
//                Toast.LENGTH_SHORT).show();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_browse, R.id.navigation_scan, R.id.navigation_user)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Check if there is an intent with a specific flag or extra indicating BrowseFragment should be shown
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("SHOW_BROWSE_FRAGMENT")) {
//            navController.navigate(R.id.navigation_browse);
            // Clear the intent to avoid navigating again on configuration changes (e.g., rotation)
            setIntent(new Intent());

            // Navigate to BrowseFragment only if it's not already on the top of the stack
            if (navController.getCurrentDestination().getId() != R.id.navigation_browse) {
                navController.navigate(R.id.navigation_browse);
            }
        }
    }

}