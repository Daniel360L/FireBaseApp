package com.daniel.firebaseapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daniel.firebaseapp.R;
import com.daniel.firebaseapp.adapter.UserAdapter;
import com.daniel.firebaseapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private RecyclerView recyclerContatos;
    private UserAdapter userAdapter;
    private DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("users");
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private ArrayList<User> list = new ArrayList<>();
    private DatabaseReference request = FirebaseDatabase.getInstance().getReference("requests");
    private  User userLogged;
    private int cont;
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View layout =  inflater.inflate(R.layout.fragment_main, container, false);
      recyclerContatos = layout.findViewById(R.id.frag_main_recycler_user);
      userLogged = new User(auth.getCurrentUser().getUid(),
              auth.getCurrentUser().getEmail(),auth.getCurrentUser().getDisplayName());

      userAdapter = new UserAdapter(getContext(),list); //instaciando adapter
        userAdapter.setListener(new UserAdapter.ClickAdapterUser() {
            @Override
            public void adicionarContato(int position) {
                User u = list.get(position);
                //salvando solicitação request send
                request.child(userLogged.getId()).child("send").child(u.getId()).setValue(u);

                //retirar o usuario da recycler

                list.remove(position);
                userAdapter.notifyDataSetChanged();

                request.child(u.getId()).child("receive").child(userLogged.getId()).setValue(userLogged);
            }
        });


         recyclerContatos.setLayoutManager(new LinearLayoutManager(getContext())); // instacinado LayoutManager
         recyclerContatos.setAdapter(userAdapter);

      return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        getUserDatabase();
    }

    public void getUserDatabase(){
        //retorna os valores do nó
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot filho :snapshot.getChildren()){
                    User u =  filho.getValue(User.class);
                    //comparar com usuario logado
                    if(!userLogged.equals(u)){
                        if(cont%2==0){
                            u.setReceiveRequest(true);
                        }else {
                            u.setReceiveRequest(false);
                        }
                        list.add(u);
                        cont++;
                    }

                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
