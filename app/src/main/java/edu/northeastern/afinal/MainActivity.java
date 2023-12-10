package edu.northeastern.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import edu.northeastern.afinal.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
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
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Check if there is an intent with a specific flag or extra indicating BrowseFragment should be shown
        Intent intent = getIntent();
        if (intent != null) {

            Bundle extras = intent.getExtras();
            if (extras != null) {
                for (String key : extras.keySet()) {
                    Object value = extras.get(key);
                    System.out.println("Intent Key: " + key + ", Value: " + value);
                }
            }

            // Check if the extra "SHOW_BROWSE_FRAGMENT" is present
            if (intent.hasExtra("SHOW_BROWSE_FRAGMENT")) {
                // Clear the back stack up to and including UserFragment and navigate to BrowseFragment
                navController.popBackStack(R.id.navigation_user, true);
                navController.popBackStack(R.id.navigation_scan, true);
                navController.navigate(R.id.navigation_browse);
            } else if (intent.hasExtra("SHOW_USER_FRAGMENT")) {
                // Clear the back stack up to and including BrowseFragment and navigate to UserFragment
                navController.popBackStack(R.id.navigation_browse, true);
                navController.popBackStack(R.id.navigation_scan, true);
                navController.navigate(R.id.navigation_user);
            } else if (intent.hasExtra("SHOW_SCAN_FRAGMENT")) {
                navController.popBackStack(R.id.navigation_browse, true);
                navController.popBackStack(R.id.navigation_user, true);
                navController.navigate(R.id.navigation_scan);
                
            }
            // Clear the intent to avoid navigating again on configuration changes (e.g., rotation)
            setIntent(new Intent());
        }


    }

}