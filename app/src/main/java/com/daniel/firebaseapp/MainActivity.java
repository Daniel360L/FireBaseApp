package com.daniel.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.daniel.firebaseapp.model.Upload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
 private FirebaseAuth auth = FirebaseAuth.getInstance();
 private Button btnlogout, btnstorage;
 private TextView textUsuario;
 private DatabaseReference database  = FirebaseDatabase.getInstance().getReference("uploads");

 private ArrayList<Upload> listaUp = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnlogout = findViewById(R.id.main_btn_logout);
        textUsuario = findViewById(R.id.main_textUsuario);
        btnstorage = findViewById(R.id.main_btn_storage);

        btnlogout.setOnClickListener(v -> {
            auth.signOut();
            finish();
        });
        TextView textemail = findViewById(R.id.main_textEmail);
        textemail.setText(auth.getCurrentUser().getEmail());
        textUsuario.setText(auth.getCurrentUser().getDisplayName());

        btnstorage.setOnClickListener(v ->{
            Intent intent = new Intent(getApplicationContext(),StorageActivity.class);
            startActivity(intent);
        });



    }
    @Override
    protected void  onStart(){
        //faz parte do ciclo de vida da activity
        //é executado quando app inicia
        super.onStart();
        getData();

    }

    public void getData(){
        //listener para o nó upload
        //-caso ocorra aluguma alteração ele retorna todos os dados
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( DataSnapshot no_filho : snapshot.getChildren()){
                    Upload upload = no_filho.getValue(Upload.class);
                    listaUp.add(upload);
                    Log.i("Database","id: "+upload.getId()+"nome: "+upload.getNomeImagem());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
