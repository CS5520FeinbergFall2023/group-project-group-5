package edu.northeastern.afinal;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.Manifest;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import edu.northeastern.afinal.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;


    private final ActivityResultLauncher<String> requestCameraPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    navigateToScanFragment();
                } else {
                    showCameraPermissionExplanation();
                }
            });

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
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    navigateToScanFragment();
                } else {
                    requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA);
                }
            }
            // Clear the intent
            setIntent(new Intent());
        }
    }
    private void navigateToScanFragment() {
        navController.popBackStack(R.id.navigation_browse, true);
        navController.popBackStack(R.id.navigation_user, true);
        navController.navigate(R.id.navigation_scan);
    }

    private void showCameraPermissionExplanation() {
        new AlertDialog.Builder(this)
                .setTitle("Camera Permission Needed")
                .setMessage("Camera permission is necessary to use the scan suggestion feature. Please grant camera permission to continue.")
                .setPositiveButton("OK", (dialog, which) -> requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA))
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}



