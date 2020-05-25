package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Aktivita ktorá dovoluje uživatelovi pridať nový cvik.
 */
public class PridajCvik extends AppCompatActivity {
    private UdajeTrening trening;

    /**
     * Nastaví funkciu buttonov a hodnotu atributu trening.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pridaj_cvik);

        getSupportActionBar().hide();

        Intent intent = getIntent();
        trening = ZoznamTreningov.getUdajeOTreningu().getTreningByNazov(intent.getStringExtra("sendTreningToPridajCvik"));

        Button pridajCvik = findViewById(R.id.saveCvik);
        pridajCvik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pridajCvik();
            }
        });

        Button delCvik = findViewById(R.id.deleteCvik);
        delCvik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Skontroluje čo boli všetky údaje zadané a sú platné, ak nie zobrazí upozornenie. Potom ich pridá.
     */
    public void pridajCvik()
    {
        TextView nazov = (TextView)findViewById(R.id.editNameCvik);
        TextView pocetSerii = (TextView)findViewById(R.id.dajPocetSerii);
        TextView pocetOpak = (TextView)findViewById(R.id.dajPocetOpak);
        TextView prestavka = (TextView)findViewById(R.id.dajPrestavku);
        TextView popis = (TextView)findViewById(R.id.getPopis);

        if (nazov.getText().toString().isEmpty())
        {
            CharSequence text = getString(R.string.nezadalNazov);
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            return;
        }
        if (pocetSerii.getText().toString().isEmpty())
        {
            CharSequence text = getString(R.string.zadajSerie);
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            return;
        }
        if (pocetOpak.getText().toString().isEmpty())
        {
            CharSequence text = getString(R.string.zadajOpakovania);
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            return;
        }
        if (prestavka.getText().toString().isEmpty())
        {
            CharSequence text = getString(R.string.nezadalPrestavku);
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            return;
        }

        int numSerii = Integer.parseInt(pocetSerii.getText().toString());
        int numOpak = Integer.parseInt(pocetOpak.getText().toString());
        int numPrestavka = Integer.parseInt(prestavka.getText().toString());

        if (trening .pridajCvik(nazov.getText().toString(), numSerii, numOpak, numPrestavka, popis.getText().toString()) == false)
        {
            CharSequence text = getString(R.string.menoExistuje);
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        } else
            finish();
    }
}
