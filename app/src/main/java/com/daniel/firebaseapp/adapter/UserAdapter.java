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
    private ArrayList<User> listContatos;
    private Context context;
    private ClickAdapterUser listener;
    private static  final  int TIPO_ADICIONAR = 0;
    private static  final  int TIPO_SOLICITAR = 1;


    public   void setListener(ClickAdapterUser listener){
        this.listener = listener;
    }

    public UserAdapter(Context context, ArrayList<User> list){
        this.context = context;
        this.listContatos = list;
    }

    @NonNull
    @Override
    public UserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_recycler,parent,false);
        if(viewType==TIPO_SOLICITAR){
            Button b = v.findViewById(R.id.user_recly_btnAdd);
            b.setText("Solicitado");
            b.setEnabled(false);
        }
        return new UserVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserVH holder, int position) {
          User u = listContatos.get(position);
          holder.textEmail.setText(u.getEmail());
          holder.textNome.setText(u.getNome());
          //caso usuário nao foi adicionado
          if(!u.getReceiveRequest()) {
              holder.onClick();
          }
          //caso não adicionar -> cria evento de click

    }

    @Override
    public int getItemCount() {
        return listContatos.size();
    }

    @Override
    public int getItemViewType(int i){
        User contato = listContatos.get(i);
        if(contato.getReceiveRequest()){
           return TIPO_SOLICITAR;
        }
        return TIPO_ADICIONAR;
    }

    public class  UserVH extends RecyclerView.ViewHolder{
        TextView textNome;
        TextView textEmail;
        RoundedImageView imPH;
        Button btnAdd;

        public void onClick(){
            btnAdd.setOnClickListener(v -> {
                if(listener!=null){
                    int position = getAdapterPosition();

                    listener.adicionarContato(position);
                }

            });
        }


        public UserVH(@NonNull View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.user_recly_nome);
            textEmail = itemView.findViewById(R.id.user_recly_email);
            imPH = itemView.findViewById(R.id.user_recycler_foto);
            btnAdd = itemView.findViewById(R.id.user_recly_btnAdd);


        }





    }

    public interface ClickAdapterUser{
        void adicionarContato(int position);
    }
}
