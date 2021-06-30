package com.daniel.firebaseapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.firebaseapp.R;
import com.daniel.firebaseapp.model.User;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserVH> {
    private ArrayList<User> list;
    private Context context;

    public UserAdapter(Context context, ArrayList<User> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_recycler,parent,false);
        return new UserVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserVH holder, int position) {
          User u = list.get(position);
          holder.textEmail.setText(u.getEmail());
          holder.textNome.setText(u.getNome());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class  UserVH extends RecyclerView.ViewHolder{
        TextView textNome;
        TextView textEmail;
        RoundedImageView imPH;
        Button btnAdd;


        public UserVH(@NonNull View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.user_recly_nome);
            textEmail = itemView.findViewById(R.id.user_recly_email);
            imPH = itemView.findViewById(R.id.user_recycler_foto);
            btnAdd = itemView.findViewById(R.id.user_recly_btnAdd);
        }




    }
}
