package com.daniel.firebaseapp.fragment;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDeepLinkBuilder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.daniel.firebaseapp.CadastroActivity;
import com.daniel.firebaseapp.MainActivity;
import com.daniel.firebaseapp.NavigationActivity;
import com.daniel.firebaseapp.R;
import com.daniel.firebaseapp.UpdateActivity;

import static com.daniel.firebaseapp.util.App.CHANNEL_1;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    private NotificationManagerCompat compat;
    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_notification, container, false);

        compat = NotificationManagerCompat.from(getContext());
        EditText editText = layout.findViewById(R.id.frag_not_title);
        EditText editM = layout.findViewById(R.id.frag_not_msg);
        Button btnsend = layout.findViewById(R.id.frag_not_btn);

        btnsend.setOnClickListener(v -> {
            String title = editText.getText().toString();
            String msn = editM.getText().toString();
            //Intent
            Intent intent = new Intent(getContext(), NavigationActivity.class);
            //objeto PendingIntent
           /* PendingIntent contenIntent = PendingIntent.getActivity(getContext(),0,intent,0 );*/

            PendingIntent contenIntent = new NavDeepLinkBuilder(getContext())
                    .setComponentName(NavigationActivity.class)
                    .setGraph(R.navigation.nav_graph)
                    .setDestination(R.id.nav_menu_listaUp)
                    .createPendingIntent();

            //Criar notificação
            Notification notification = new NotificationCompat.Builder(getContext(),CHANNEL_1)
                    .setContentTitle(title)
                    .setContentText(msn)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setSmallIcon(R.drawable.ic_person_outline_black_24dp)
                    .setContentIntent(contenIntent)
                    .addAction(R.drawable.ic_person_outline_black_24dp,"Toast",actionIntent)
                    .build();
            compat.notify(1,notification);
        });


        return layout;
    }
}
