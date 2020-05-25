package com.example.gymapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

/**
 * Použíté zdroje https://www.tutorialspoint.com/android/android_notifications.htm
 * Trieda Slúži na vytváranie upozornení.
 */
public class Upozornenie extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Upozornenie na treningy ktoré nemajú žiaden cvik.
     * @param k
     * @param meno
     */
    public void showUpozornenie(Context k, String meno) // https://www.tutorialspoint.com/android/android_notifications.htm
    {
        NotificationCompat.Builder upozornenie = new NotificationCompat.Builder(k, "gymApp").setSmallIcon(R.drawable.upozorikona).setContentTitle("Upozornenie").setContentText(k.getString(R.string.prazdneTreningy));

        Intent intent = new Intent(k, Trening.class);
        intent.putExtra("sendValue", meno);
        PendingIntent pIntent = PendingIntent.getActivity(k, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        upozornenie.setContentIntent(pIntent);

        NotificationManager manager = (NotificationManager)k.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(3, upozornenie.build());
    }

    /**
     * Upozornenie na skončenie času prestávky.
     * @param k
     */
    public void showUpozornenie(Context k)
    {
        NotificationCompat.Builder upozornenie = new NotificationCompat.Builder(k, "gymApp").setSmallIcon(R.drawable.upozorikona).setContentTitle("Upozornenie").setContentText(k.getString(R.string.casCvicit));

        Intent intent = new Intent(k, DennyTrening.class);
        PendingIntent pIntent = PendingIntent.getActivity(k, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        upozornenie.setContentIntent(pIntent);

        NotificationManager manager = (NotificationManager)k.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, upozornenie.build());
    }
}
