package com.daniel.firebaseapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daniel.firebaseapp.R;
import com.daniel.firebaseapp.StorageActivity;
import com.daniel.firebaseapp.UpdateActivity;
import com.daniel.firebaseapp.adapter.ImageAdapter;
import com.daniel.firebaseapp.model.Upload;
import com.daniel.firebaseapp.util.LoadingDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadFragment extends Fragment {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private TextView textUsuario;
    private DatabaseReference database  = FirebaseDatabase.getInstance()
            .getReference("uploads").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

    private ArrayList<Upload> listaUp = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    public UploadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_upload, container, false);
        textUsuario = layout.findViewById(R.id.main_textUsuario);
        recyclerView = layout.findViewById(R.id.main_recycler);

        imageAdapter = new ImageAdapter(getActivity(),listaUp);
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
                Intent intent = new Intent (getActivity(), UpdateActivity.class);
                intent.putExtra("upload",upload); //  envia o upload para outra activity
                startActivity(intent);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imageAdapter);


        return layout;
    }

    public void deleteUpload(Upload upload){
        LoadingDialog dialog = new LoadingDialog(getActivity(),R.layout.custom_dialog);
        dialog.startLoadingDialog();
        //deletando image no Storage Firebase
        StorageReference imagemRef = FirebaseStorage.getInstance().getReferenceFromUrl(upload.getUrl());
        imagemRef.delete().addOnSuccessListener(aVoid -> {
            //deletando no database
            database.child(upload.getId()).removeValue()
                    .addOnSuccessListener(aVoid1 -> {
                        Toast.makeText(getActivity(),"Item Deletado",Toast.LENGTH_SHORT).show();
                        dialog.dismissDialog();
                    });
        });
    }



    @Override
    public void  onStart(){
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
