package com.example.thodasukoon;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.thodasukoon.R;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private static final String SELECTED_ITEM_KEY = "selected_item";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


    BottomNavigationView bottomNav;
    HomeFragment homeFragment = new HomeFragment();
    Book bookFragment = new Book();
    AiSupport aiSupportFragment = new AiSupport();
    Profile profileFragment = new Profile();
    Resource resourceFragment = new Resource();
    resource2 resource2 = new resource2();

    private PrefManager prefManager;

    int selectedItemId = R.id.home; // default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        prefManager = new PrefManager(this);
        String token = prefManager.getToken();

        if (token == null) {
            // User not logged in → go to LoginActivity
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish(); // prevent going back to MainActivity
        }
        // else, user is logged in → stay in MainActivity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }


        // Restore previously selected menu item
        if (savedInstanceState != null) {
            selectedItemId = savedInstanceState.getInt(SELECTED_ITEM_KEY, R.id.home);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNav = findViewById(R.id.bottomNav);

        // Set the selected item in BottomNavigationView
        bottomNav.setSelectedItemId(selectedItemId);

        // Open the fragment corresponding to the selected item
        if (selectedItemId == R.id.home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        } else if (selectedItemId == R.id.book) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, bookFragment).commit();
        } else if (selectedItemId == R.id.resource) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, resourceFragment).commit();
        } else if (selectedItemId == R.id.resource2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, resource2).commit();
        } else if (selectedItemId == R.id.chat) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, aiSupportFragment).commit();
        } else if (selectedItemId == R.id.profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
        }

        bottomNav.setOnItemSelectedListener(item -> {
            selectedItemId = item.getItemId();

            if (selectedItemId == R.id.home) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
            } else if (selectedItemId == R.id.book) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, bookFragment).commit();
            } else if (selectedItemId == R.id.resource) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, resourceFragment).commit();
            } else if (selectedItemId == R.id.resource2) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, resource2).commit();
            } else if (selectedItemId == R.id.chat) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, aiSupportFragment).commit();
            } else if (selectedItemId == R.id.profile) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
            }

            return true;
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                Toast.makeText(this, "Location permission granted.", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied
                Toast.makeText(this, "Location permission is required for some features.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_KEY, selectedItemId);
    }


}
