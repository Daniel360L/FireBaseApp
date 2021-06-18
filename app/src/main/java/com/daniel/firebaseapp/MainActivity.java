package com.daniel.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {
 private FirebaseAuth auth = FirebaseAuth.getInstance();
 private Button btnlogout;
 private TextView textUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnlogout = findViewById(R.id.main_btn_logout);
        textUsuario = findViewById(R.id.main_textUsuario);

        btnlogout.setOnClickListener(v -> {
            auth.signOut();
            finish();
        });
        TextView textemail = findViewById(R.id.main_textEmail);
        textemail.setText(auth.getCurrentUser().getEmail());
        textUsuario.setText(auth.getCurrentUser().getDisplayName());





    }
}
