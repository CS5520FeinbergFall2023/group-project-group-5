package edu.northeastern.afinal;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.graphics.RenderEffect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;

import edu.northeastern.afinal.ui.browse.BrowseFragment;
import edu.northeastern.afinal.ui.login.LoginActivity;

public class InitialActivity extends AppCompatActivity {

    private final ActivityResultLauncher<Intent> startMainActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Handle the result if needed
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        FirebaseApp.initializeApp(/*context=*/ this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                PlayIntegrityAppCheckProviderFactory.getInstance());

        Button userButton = (Button) findViewById(R.id.buttonUser);
        userButton.setOnClickListener(v->{
            Intent intent = new Intent(InitialActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // go to the browse fragment
        Button browseButton = (Button) findViewById(R.id.buttonBrowse);
        browseButton.setOnClickListener(v -> {
            Intent intent = new Intent(InitialActivity.this, MainActivity.class);
            intent.putExtra("SHOW_BROWSE_FRAGMENT", true);
            startMainActivityForResult.launch(intent);
        });
    }
}