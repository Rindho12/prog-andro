package com.example.myapplication.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myapplication.R;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_UNKNOWN);

        Toast.makeText(context, "Wifi berubah", Toast.LENGTH_LONG).show();
        switch (wifiStateExtra) {
            case WifiManager.WIFI_STATE_ENABLED:
                onNotificationReceive(context, "Wifi on");
                break;
            case WifiManager.WIFI_STATE_DISABLED:
                onNotificationReceive(context, "Wifi off");
                break;
        }
    }

    private void onNotificationReceive(Context context, String status) {
        String ID = "NOTIFIKASI";
        String judul = "Wifi state";
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(ID, "My Channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setContentTitle(judul)
                .setContentText(status)
                .build();
        int notificationID = 1;
        notificationManager.notify(notificationID, notification);

    }
}
