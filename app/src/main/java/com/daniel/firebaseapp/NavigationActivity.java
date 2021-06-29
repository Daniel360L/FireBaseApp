package com.daniel.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

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

        NavigationView navigationView = findViewById(R.id.navition_view);
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragmrnt);

        //juntar navController com navView(menu)
        //configurada troca de tela
        NavigationUI.setupWithNavController(navigationView,navController);


    }
}
