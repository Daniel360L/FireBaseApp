package com.daniel.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
     private Button btnCadastrar,btnLogin;
     private EditText editEmail,editSenha;

     private FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnCadastrar = findViewById(R.id.login_btn_cadastrar);
        btnLogin = findViewById(R.id.login_btn_logar);
        editEmail = findViewById(R.id.login_edit_email);
        editSenha = findViewById(R.id.login_edit_senha);

        btnCadastrar.setOnClickListener(view ->{
                Intent intent = new Intent(getApplicationContext(),CadastroActivity.class);
                startActivity(intent);

        });

        btnLogin.setOnClickListener(v -> {
            logar();
        });
    }

    public void  logar(){
        String email =  editEmail.getText().toString();
        String senha =  editSenha.getText().toString();
        if(email.isEmpty() || senha.isEmpty()){
            Toast.makeText(this,"preencha os campos",Toast.LENGTH_SHORT).show();
            return;
        }
        //fazendo tarefa para login
       Task<AuthResult> t =  auth.signInWithEmailAndPassword(email,senha);

        //listener sucesso
        t.addOnSuccessListener(o -> {
           //o -> authResult
            Toast.makeText(this,"Bem-vindo!",Toast.LENGTH_SHORT).show();
            Intent intent  = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        });
        // listener de falha
        t.addOnFailureListener(e ->{
            Toast.makeText(this,"ERRO",Toast.LENGTH_SHORT).show();
        });
    }
}
