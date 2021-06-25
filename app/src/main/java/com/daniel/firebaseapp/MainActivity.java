package com.daniel.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daniel.firebaseapp.adapter.ImageAdapter;
import com.daniel.firebaseapp.model.Upload;
import com.daniel.firebaseapp.util.LoadingDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
 private FirebaseAuth auth = FirebaseAuth.getInstance();
 private Button btnlogout, btnstorage;
 private TextView textUsuario;
 private DatabaseReference database  = FirebaseDatabase.getInstance().getReference("uploads");

 private ArrayList<Upload> listaUp = new ArrayList<>();

 private RecyclerView recyclerView;
 private ImageAdapter imageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnlogout = findViewById(R.id.main_btn_logout);
        textUsuario = findViewById(R.id.main_textUsuario);
        btnstorage = findViewById(R.id.main_btn_storage);
        recyclerView = findViewById(R.id.main_recycler);

        imageAdapter = new ImageAdapter(getApplicationContext(),listaUp);
        //metodos da interface criada no ImageAdapter
        imageAdapter.setListener(new ImageAdapter.OnItemClicklistener() {
            @Override
            public void onDeleteClick(int position) {
                   Upload upload = listaUp.get(position);
                   deleteUpload(upload);
            }

            @Override
            public void onUpdateClick(int position) {
                Upload upload = listaUp.get(position);
                Intent intent = new Intent (getApplicationContext(),UpdateActivity.class);
                intent.putExtra("upload",upload); //  envia o upload para outra activity
                startActivity(intent);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imageAdapter);

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

    public void deleteUpload(Upload upload){
        LoadingDialog dialog = new LoadingDialog(this,R.layout.custom_dialog);
        dialog.startLoadingDialog();
        //deletando image no Storage Firebase
        StorageReference imagemRef = FirebaseStorage.getInstance().getReferenceFromUrl(upload.getUrl());
        imagemRef.delete().addOnSuccessListener(aVoid -> {
            //deletando no database
            database.child(upload.getId()).removeValue()
                    .addOnSuccessListener(aVoid1 -> {
                        Toast.makeText(getApplicationContext(),"Item Deletado",Toast.LENGTH_SHORT).show();
                        dialog.dismissDialog();
                    });
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
                listaUp.clear();
                //retorna os dados pelo "snapshot"
                for( DataSnapshot no_filho : snapshot.getChildren()){
                    Upload upload = no_filho.getValue(Upload.class);
                    listaUp.add(upload);
                    Log.i("Database","id: "+upload.getId()+"nome: "+upload.getNomeImagem());

                }
                imageAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
