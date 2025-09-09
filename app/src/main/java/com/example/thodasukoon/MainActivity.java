package com.example.thodasukoon;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.thodasukoon.R;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    HomeFragment homeFragment = new HomeFragment();
    Book bookFragment = new Book();
    AiSupport aiSupportFragment = new AiSupport();
    Profile profileFragment = new Profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNav = findViewById(R.id.bottomNav);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, homeFragment).commit();
                    return true;
                } else if (id == R.id.book) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, bookFragment).commit();
                    return true;
                } else if (id == R.id.chat) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, aiSupportFragment).commit();
                    return true;
                } else if (id == R.id.profile) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, profileFragment).commit();
                    return true;
                }

                return false;
            }
        });


    }
}