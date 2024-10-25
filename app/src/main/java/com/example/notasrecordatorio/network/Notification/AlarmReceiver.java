package com.example.notasrecordatorio.network.Notification;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.notasrecordatorio.MainActivity;
import com.example.notasrecordatorio.R;
import com.example.notasrecordatorio.login.LoginActivity;
import com.example.notasrecordatorio.ui.home.HomeFragment;

public class AlarmReceiver extends BroadcastReceiver {
    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("AlarmReceiver", "onReceive() ejecutado");
        // 1. Obtener datos del intent (si los pasaste)
        String titulo = intent.getStringExtra("titulo");
        String descripcion = intent.getStringExtra("descripcion");

        // 2. Crear el canal de notificaciones (si es necesario)
        createNotificationChannel(context);

        Intent mainIntent = new Intent(context, HomeFragment.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE);
        // 3. Crear la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "canal_recordatorios")
                .setSmallIcon(R.drawable.post)
                .setContentTitle(titulo)
                //.setContentText(descripcion)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.post))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(descripcion))
                .setColor(ContextCompat.getColor(context, R.color.teal_200))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                //.setVibrate(new long[] { 500, 250, 500 });
                .addAction(R.drawable.post, "Abrir Recordatorio", pendingIntent);

        // 4. Mostrar la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context.getApplicationContext());
        notificationManager.notify(1,builder.build());
    }

    // Método para crear el canal de notification (si es necessarily)
    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Recordatorios";
            String description = "Canal para notificaciones de recordatorios";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("canal_recordatorios", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Log.d("AlarmReceiver", "Canal de notificaciones creado: " + channel.getId());
        }
    }
}
