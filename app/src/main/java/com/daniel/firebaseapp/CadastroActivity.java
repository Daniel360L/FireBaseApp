package com.daniel.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {
    private Button btncadastrar;
    private EditText ediNome, editEmail,editSenha;

    private FirebaseAuth auth  = FirebaseAuth.getInstance(); // instancia um objeto uma unica vez // referencia a autenticação
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        btncadastrar = findViewById(R.id.cadastro_btn_logar);
        ediNome = findViewById(R.id.cadastro_edit_nome);
        editEmail = findViewById(R.id.cadastro_edit_email);
        editSenha = findViewById(R.id.cadastro_edit_senha);

        btncadastrar.setOnClickListener(v -> {
            cadastrar();
        });
    }
    public  void cadastrar(){
        String email =  editEmail.getText().toString();
        String senha =  editSenha.getText().toString();
        if(email.isEmpty() || senha.isEmpty()){
            Toast.makeText(this,"preencha os campos",Toast.LENGTH_SHORT).show();
            return;
        }
        //criar um ususario
        Task t = auth.createUserWithEmailAndPassword(email,senha);

        t.addOnCompleteListener(task -> {
            //listener executado com sucesso ou fracasso
            if(task.isSuccessful()){
                // retorna true
                Toast.makeText(getApplicationContext(),"Usuario criado com sucesso",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"ERRO",Toast.LENGTH_SHORT).show();
            }
        });

    }
}