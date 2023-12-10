package edu.northeastern.afinal;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import android.Manifest;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.RenderEffect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.northeastern.afinal.ui.browse.BrowseFragment;
import edu.northeastern.afinal.ui.login.LoginActivity;
//import edu.northeastern.afinal.ui.scan.ScanActivity;
import edu.northeastern.afinal.ui.scan.ScanFragment;

public class InitialActivity extends AppCompatActivity {

    private final ActivityResultLauncher<Intent> startMainActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Handle the result if needed
                }
            }
    );

    private final ActivityResultLauncher<String> requestCameraPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted, proceed to open MainActivity with scan fragment
//                    openScanFragment();
                } else {
                    // Permission is denied, we can show a message to the user explaining why the permission is needed
                    showCameraPermissionExplanation();

                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        FirebaseApp.initializeApp(this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                PlayIntegrityAppCheckProviderFactory.getInstance());

        Button userButton = (Button) findViewById(R.id.buttonUser);
        userButton.setOnClickListener(v->{
            //check if already logged in
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser user = auth.getCurrentUser();
            if(user==null) {
                Intent intent = new Intent(InitialActivity.this, LoginActivity.class);
//                startActivity(intent);
                startMainActivityForResult.launch(intent);

            }
            else {
                Intent intent = new Intent(InitialActivity.this, MainActivity.class);
                intent.putExtra("SHOW_USER_FRAGMENT", true);
//                startActivity(intent);
                startMainActivityForResult.launch(intent);

            }
        });

        // go to the browse fragment
        Button browseButton = (Button) findViewById(R.id.buttonBrowse);
        browseButton.setOnClickListener(v -> {
            Intent intent = new Intent(InitialActivity.this, MainActivity.class);
            startMainActivityForResult.launch(intent);
        });

    }

    private void openScanFragment() {
        Intent intent = new Intent(InitialActivity.this, MainActivity.class);
        intent.putExtra("SHOW_SCAN_FRAGMENT", true);
        startMainActivityForResult.launch(intent);
    }

    private void showCameraPermissionExplanation() {
        new AlertDialog.Builder(this)
                .setTitle("Camera Permission Needed")
                .setMessage("Camera permission is necessary to use the scan suggestion feature. Please grant camera permission in the settings menu to continue.")
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }
}