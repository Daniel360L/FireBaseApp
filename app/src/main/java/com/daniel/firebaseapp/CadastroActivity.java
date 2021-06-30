package com.daniel.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daniel.firebaseapp.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity {
    private Button btncadastrar;
    private EditText ediNome, editEmail,editSenha;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");
    //criando o nó "banco" no firebase
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
        String usuario = ediNome.getText().toString();
        if(email.isEmpty() || senha.isEmpty() || usuario.isEmpty()){
            Toast.makeText(this,"preencha os campos",Toast.LENGTH_SHORT).show();
            return;
        }
        //criar um ususario
        Task<AuthResult> t = auth.createUserWithEmailAndPassword(email,senha); // encadeamento // metodo so recebe email e senha
        t.addOnCompleteListener(task -> {
            //listener executado com sucesso ou fracasso
            if(task.isSuccessful()){
                // retorna true
                Toast.makeText(getApplicationContext(),"Usuario criado com sucesso",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"ERRO",Toast.LENGTH_SHORT).show();
            }
        });
        t.addOnSuccessListener(authResult -> {
            //setando nome do usuario
            //request para mudar o nome do usuario
            UserProfileChangeRequest update = new UserProfileChangeRequest.Builder().setDisplayName(usuario).build();
            //inserir no database
            User u = new User(authResult.getUser().getUid(),email,usuario);
            database.child(u.getId()).setValue(u);

            authResult.getUser().updateProfile(update);
        });

    }
}
