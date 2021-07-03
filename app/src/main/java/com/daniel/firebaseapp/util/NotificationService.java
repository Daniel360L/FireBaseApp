package com.daniel.firebaseapp.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.daniel.firebaseapp.R;
import com.daniel.firebaseapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.daniel.firebaseapp.util.App.CHANNEL_1;

public class NotificationService extends Service {
    private ValueEventListener listener;
    private DatabaseReference receive;

    @Override
    public void onCreate() {
        super.onCreate();//é executado quando o serviço é criado -> uma ve

    }

    public void showNotify(User user){
        //criando notificação
        Notification notification = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_1)
                .setSmallIcon(R.drawable.ic_person_outline_black_24dp)
                .setContentTitle("Alteração")
                .setContentText(user.getNome())
                .setPriority(Notification.PRIORITY_HIGH)
                .build();


        //enviando ao canal
       NotificationManager nm = (NotificationManager) getApplicationContext()
               .getSystemService(Context.NOTIFICATION_SERVICE);
       nm.notify(1,notification);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        receive = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(FirebaseAuth.getInstance()
                        .getCurrentUser().getUid());


        listener = receive.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                showNotify(user);
                stopSelf();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

   /* @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);

    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        receive.removeEventListener(listener);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //é executado quando o serviço é chmado
        return null;
    }


}
