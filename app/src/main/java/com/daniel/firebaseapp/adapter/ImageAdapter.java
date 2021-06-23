package com.daniel.firebaseapp.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daniel.firebaseapp.R;
import com.daniel.firebaseapp.model.Upload;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageVH> {
    private Context context;
    private ArrayList<Upload> listaUpload;
    private OnItemClicklistener listener;

    public void setListener( OnItemClicklistener listener){
        this.listener = listener;
    }

    public ImageAdapter(Context c , ArrayList<Upload> l ){
        this.context=c;
        this.listaUpload=l;
    }


    @NonNull
    @Override
    public ImageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.imagem_item,parent,false);
        return new ImageVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageVH holder, int position) {
        Upload upload = listaUpload.get(position);
        holder.textNome.setText(upload.getNomeImagem());
        Glide.with(context).load(upload.getUrl()).into(holder.imageView);
        upload.getUrl();

    }

    @Override
    public int getItemCount() {
        return listaUpload.size();
    }

    public class ImageVH extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
       TextView textNome;
       ImageView imageView;

        public ImageVH(@NonNull View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.imagemItem_text);
            imageView = itemView.findViewById(R.id.image_item_view);
            itemView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Selecionar ação");
            MenuItem deletar =  menu.add(0,1,1,"Deletar");
            menu.add(0,2,2,"Atualizar");

            deletar.setOnMenuItemClickListener(item -> {
                if(listener!=null){
                    int position = getAdapterPosition();
                    listener.onDeleteClick(position);
                }
                return true;
            });
        }
    }

    public interface OnItemClicklistener{
        void  onDeleteClick(int position);
        void onUpdateClick(int position);
    }

}
