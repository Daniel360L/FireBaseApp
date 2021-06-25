package com.daniel.firebaseapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daniel.firebaseapp.model.Upload;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class UpdateActivity extends AppCompatActivity {
    private Button btnupload,btnGaleria;
    private ImageView imageView;
    private Uri imagemUri=null;
    private EditText editnome;
    //firebase storage
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    //refererencoia para o nó
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("uploads");
    private Upload upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        btnupload = findViewById(R.id.update_btn_upload);
        imageView =  findViewById(R.id.update_imagem_cel);
        btnGaleria = findViewById(R.id.update_btn_galeria);
        editnome = findViewById(R.id.update_editNome);
        //recupearar upload selecionado
        upload = (Upload) getIntent().getSerializableExtra("upload");
        editnome.setText(upload.getNomeImagem());//mostrando nome da imagem
        Glide.with(this).load(upload.getUrl()).into(imageView);//mosttando imagem

        btnGaleria.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            //inicia uma activity e espera o retorno(foto)
            startActivityForResult(intent,12);
        });

        btnupload.setOnClickListener(v -> {
            if(editnome.getText().toString().isEmpty()){
                Toast.makeText(this,"Sem nome",Toast.LENGTH_SHORT).show();
                return;
            }
            //caso imagem não tenha sido atualizada
            if(imagemUri==null){
                //atualizar nome
                String nome = editnome.getText().toString();
                upload.setNomeImagem(nome);
                database.child(upload.getId()).setValue(upload)
                        .addOnSuccessListener(aVoid -> {
                           finish();
                        });
                return;
            }

            atualizarImagem();

        });
    }
    public void atualizarImagem(){
        //deletar a imagem antiga no storage
        //fazer upload da imagem atualizada no storage
        //listener para recuperar a url da imagem no storage
        //atualizar no database


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==12 && resultCode== Activity.RESULT_OK){
            //caso o usuario selecionou uma imagem da galeria
            //resultCode retorna o status da opereção

            //endereço da imagem
            imagemUri= data.getData();
            imageView.setImageURI(imagemUri);
        }
    }
}
