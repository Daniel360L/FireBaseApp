package com.daniel.firebaseapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class StorageActivity extends AppCompatActivity {
    private Button btnupload,btnGaleria;
    private ImageView imageView;
    private Uri imagemUri=null;
    private EditText editnome;
    //firebase storage
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        btnupload = findViewById(R.id.storage_btn_upload);
        imageView =  findViewById(R.id.storage_imagem_cel);
        btnGaleria = findViewById(R.id.storage_btn_galeria);
        editnome = findViewById(R.id.storage_editNome);

        btnupload.setOnClickListener(v ->{
            if(editnome.getText().toString().isEmpty()){
                Toast.makeText(this,"Digite nome para imagem",Toast.LENGTH_SHORT).show();
                return;
            }
            if(imagemUri!=null) {
                uploadImagemUri();
            }else{
                uploadImagemByte();
            }
        });

        btnGaleria.setOnClickListener(v ->{
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,12);
        });


    }

    private void uploadImagemUri() {
        String tipo = getFileExtension(imagemUri);
        //referencia no firebase
        Date d = new Date();
        String nome = editnome.getText().toString()+"-"+d.getTime();
        StorageReference imagemRef = storage.getReference().child("imagens/"+nome+"."+tipo);
        imagemRef.putFile(imagemUri)
        .addOnSuccessListener(taskSnapshot -> {
         Toast.makeText(this,"Upload feito som sucesso",Toast.LENGTH_SHORT).show();
        })
        .addOnFailureListener(e ->{
            e.printStackTrace();
        });
    }

    private String getFileExtension(Uri imagemUri) {
        //retorna o tipo
        ContentResolver cr = getContentResolver();
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(cr.getType(imagemUri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("resukt","requestCode: "+requestCode+",resultCode: "+resultCode);
        if(requestCode==12 && resultCode== Activity.RESULT_OK){
            //caso o usuario selecionou uma imagem da galeria

            //endereço da imagem
            imagemUri= data.getData();
            imageView.setImageURI(imagemUri);
        }
    }

    public byte[] convertByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        //objeto baos -> armazenar a imagem convertida
        ByteArrayOutputStream baos =  new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos); // comprimida
        byte[] data = baos.toByteArray();
        //storage.getReference().putBytes();
        return baos.toByteArray();
    }

    //fazer upload de uma imagem convertida para bytes
    public void uploadImagemByte(){
        byte[] data = convertByte(imageView);
        //criar uma referencai aimgaem no storage
        StorageReference imagemRef = storage.getReference().child("imagens/02.jpeg");
        //upload da imagem
        imagemRef.putBytes(data)
        .addOnSuccessListener(taskSnapshot -> {
            Toast.makeText(this,"Upload feito com sucesso",Toast.LENGTH_SHORT).show();
            Log.i("Upload","Sucesso");
        })

        .addOnFailureListener(e -> {
            e.printStackTrace();
        })
        ;
    }
}
