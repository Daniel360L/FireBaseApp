package com.daniel.firebaseapp.util;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.navigation.NavDeepLinkBuilder;

import com.daniel.firebaseapp.CadastroActivity;
import com.daniel.firebaseapp.NavigationActivity;
import com.daniel.firebaseapp.R;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("toast");
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

        Intent intent1  = new Intent();
        intent1.setClassName("com.daniel.firebaseapp","com.daniel.firebaseapp.NavigationActivity");
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);


        /*PendingIntent intentFrag = new NavDeepLinkBuilder(context)
                .setComponentName(NavigationActivity.class)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.nav_menu_cadastro)
                .createPendingIntent();*/

        context.startActivity(intent1);
    }
}
