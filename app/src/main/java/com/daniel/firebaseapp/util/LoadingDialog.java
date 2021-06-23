package com.daniel.firebaseapp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDialog {
    private Activity activity;
    private AlertDialog dialog;
    private  int layout;

    public LoadingDialog(Activity activity, int layout){
        this.activity = activity;
        this.layout = layout;
        // layout -> R.layout.custom_dialog.xml
    }

    public void  startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();

        builder.setView(inflater.inflate(layout,null)); // setando o layout do custom_dialog
        builder.setCancelable(false);
        //mostrar o dialog
        dialog = builder.create();
        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
