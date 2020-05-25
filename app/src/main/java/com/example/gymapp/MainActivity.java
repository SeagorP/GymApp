package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Uvodné menu.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Nastaví buttony úvodného menu a vytvorí kanál pre upozornenia.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        Button zoznamTeningov = findViewById(R.id.treningy);
        zoznamTeningov.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TreningyRecycler.class);
                startActivity(intent);
            }
        });

        Button profil = findViewById(R.id.osobneUdaje);
        profil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OsProfilZobraz.class);
                startActivity(intent);
            }
        });

        Button dennyTrening = findViewById(R.id.dailyTraining);
        dennyTrening.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DennyTrening.class);
                startActivity(intent);
            }
        });

        //Vytvorenie kanalu
        vytvorUpozornenie();
        startService(new Intent(this, Upozornenie.class));
    }

    /**
     * Skontroluje či existuju nejaké treningy ktoré nemaju žiaden cvik a ak ano zobrazi upozornenie.
     */
    @Override
    protected void onStart() {
        super.onStart();
        String menoTreningu = ZoznamTreningov.getUdajeOTreningu().masPrazdneTreningy();
        if(menoTreningu != null)
        {
            new Upozornenie().showUpozornenie(getApplicationContext(), menoTreningu);
        }
    }

    /**
     * Vytvorenie kanalu. Použil som zdroje z https://www.tutorialspoint.com/android/android_notifications.htm.
     */
    public void vytvorUpozornenie() // https://www.tutorialspoint.com/android/android_notifications.htm
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel kanal = new NotificationChannel("gymApp", "GymApp", NotificationManager.IMPORTANCE_DEFAULT);
            kanal.setDescription("Upozornenie");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(kanal);
        }
    }
}
