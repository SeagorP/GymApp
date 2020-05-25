package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Aktivita ktorá zobrazuje údaje o cviku.
 */
public class CvikZobrazenie extends AppCompatActivity {
    private Cvik cvik;
    private UdajeTrening trening;

    /**
     * Pri vytvorení si načíta hodnoty z triedy UdajeTrening, zobrazí ich
     * a nastaví funkcie buttonov.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cvik_zobrazenie);

        getSupportActionBar().hide();

        Intent intent = getIntent();
        String nazov = intent.getStringExtra("sendTreningToCvik");
        String nazovTreningu = intent.getStringExtra("sendTreningToCvik2");


        trening = ZoznamTreningov.getUdajeOTreningu().getTreningByNazov(nazovTreningu);
        cvik = trening.getCvik(nazov);

        final TextView viewNazov = (TextView)findViewById(R.id.nazovCvik);
        viewNazov.setText(cvik.getNazov());
        final TextView serii = (TextView)findViewById(R.id.pocetSerii);
        serii.setText("" + cvik.getPocetSerii());
        final TextView opakovani = (TextView)findViewById(R.id.pocetOpakovani);
        opakovani.setText("" + cvik.getPocetOpakovani());
        final TextView prestavka = (TextView)findViewById(R.id.casPrestavka);
        prestavka.setText("" + cvik.getPauza() + " sec");
        final TextView popis = (TextView)findViewById(R.id.popisCviku);
        popis.setText(cvik.getPopis());

        Button delCvik = findViewById(R.id.delCvikIn);
        delCvik.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                trening.zmazCvik(cvik.getNazov());
                finish();
            }
        });

    }
}
