package com.daniel.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.widget.ImageView;

public class NavigationActivity extends AppCompatActivity {
      ImageView imageView;
      DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        imageView = findViewById(R.id.nav_imageView);
        drawerLayout = findViewById(R.id.dragDown);

        imageView.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });


    }
}
