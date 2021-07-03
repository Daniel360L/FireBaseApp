package com.daniel.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daniel.firebaseapp.util.NotificationService;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class NavigationActivity extends AppCompatActivity {
      ImageView imageView;
      DrawerLayout drawerLayout;
      FirebaseAuth auth = FirebaseAuth.getInstance();
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
        TextView textNome = navigationView.getHeaderView(0).findViewById(R.id.nav_header_usuario);
        TextView textEmail = navigationView.getHeaderView(0).findViewById(R.id.nav_header_email);
        navigationView.getMenu().findItem(R.id.nav_menu_layout).setOnMenuItemClickListener(item -> {
          auth.signOut();
          finish();
            return true;
        });
        textNome.setText(auth.getCurrentUser().getDisplayName());
        textEmail.setText(auth.getCurrentUser().getEmail());

        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragmrnt);

        //juntar navController com navView(menu)
        //configurada troca de tela
        NavigationUI.setupWithNavController(navigationView,navController);

        Intent service = new Intent(getApplicationContext(), NotificationService.class);
        getApplicationContext().startService(service);



    }
}
