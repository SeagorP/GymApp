package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Aktivita ktorá zobrazuje spustený trening.
 */
public class DennyTrening extends AppCompatActivity {
    private DataDennyTrening dennyTrening;

    /**
     * Nastaví buttony. V prípade že je trening nastavený spusti ho, v opačnom prípade čaká na nastavenie.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denny_trening);
        getSupportActionBar().hide();

        dennyTrening = DataDennyTrening.getDennyTrening();

        //skryje buttony
        if (dennyTrening.getTrening() == null)
            schovajItemy();
        else
            spustiTrening();

        Button startTrening = findViewById(R.id.buttonStart);
        startTrening.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (!dennyTrening.jeZvolenyTrening()) {
                    CharSequence text = "Nemáš zvolený trening.";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                } else
                    spustiTrening();
            }
        });

        Button nastavTrening = findViewById(R.id.buttonSetTrening);
        nastavTrening.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DennyTrening.this, TreningyRecycler.class);
                startActivity(intent);
            }
        });

        Button pokracuj = findViewById(R.id.buttonContinue);
        pokracuj.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                pokracujTrening();
            }
        });
        Button showTrening = findViewById(R.id.showTrening);
        showTrening.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DennyTrening.this, Trening.class);
                intent.putExtra("sendValue", dennyTrening.getTrening().getNazov());
                startActivity(intent);
            }
        });
        Button exitTrening = findViewById(R.id.exitTrening);
        exitTrening.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                koniec();
                finish();
            }
        });
    }

    /**
     * Skryje úvodné buttony a zobrazí nové.
     */
    public void spustiTrening()
    {
        Button startTrening = findViewById(R.id.buttonStart);
        Button nastavTrening = findViewById(R.id.buttonSetTrening);
        startTrening.setVisibility(View.GONE);// skryje button start
        nastavTrening.setVisibility(View.GONE);// skryje button change

        Button exitTrening = findViewById(R.id.exitTrening);
        exitTrening.setVisibility(View.VISIBLE);
        Button showTrening = findViewById(R.id.showTrening);
        showTrening.setVisibility(View.VISIBLE);
        Button pokracuj = findViewById(R.id.buttonContinue);
        pokracuj.setVisibility(View.VISIBLE);
        TextView nazov = (TextView)findViewById(R.id.nazovDT);
        nazov.setVisibility(View.VISIBLE);
        TextView timer = (TextView)findViewById(R.id.timer);
        timer.setVisibility(View.VISIBLE);
        TextView serie = (TextView)findViewById(R.id.serieDT);
        serie.setVisibility(View.VISIBLE);
        TextView serieOstava = (TextView)findViewById(R.id.serieOstavaDT);
        serieOstava.setVisibility(View.VISIBLE);
        TextView opakovanie = (TextView)findViewById(R.id.opakovanieDT);
        opakovanie.setVisibility(View.VISIBLE);
        prekresli();
    }

    /**
     * Prekreslí časovať ak bol spustený a uživatel sa vrátil do aktivity.
     */
    @Override
    protected void onStart() {
        super.onStart();
        dennyTrening.setThread(this);
        if (dennyTrening.getTrening() != null)
        {
            prekresli();
            if (dennyTrening.jeKoniec())
                koniec();
            return;
        }
    }

    /**
     * Funkcia ktorá sa volá pri buttone pokračuj.
     * Ak je možne spusti časovač na dalšiu prestávku.
     */
    public void pokracujTrening()
    {
        if (dennyTrening.jeKoniec())
        {
            koniec();
            return;
        }
        if (dennyTrening.mozemPokracovat()) {
            dennyTrening.pokracuj();
            dennyTrening.spustiThread(this);
            prekresli();
        }
    }

    /**
     * Zníži čas prestávky. Funkcia volaná threadom.
     * Zobrazi upozornenie keď skončí čas.
     */
    public void znizCas()
    {
        if (dennyTrening.znizCas()) {
            dennyTrening.zastavThread();
            new Upozornenie().showUpozornenie(getApplicationContext());
        }
        setCas();
    }

    /**
     * Prekreslovanie času.
     */
    public void setCas()
    {
        TextView timer = (TextView)findViewById(R.id.timer);
        if (timer != null) {
            if (dennyTrening.getCas() < 10)
                timer.setText("0" + dennyTrening.getCas() + " s");
            else
                timer.setText("" + dennyTrening.getCas() + " s");
        }
    }

    /**
     * Prekreslovanie aktuálnych udajov o treningu.
     */
    public void prekresli()
    {
        TextView nazov = (TextView)findViewById(R.id.nazovDT);
        nazov.setText(dennyTrening.getAktualnyCvik().getNazov());
        TextView serie = (TextView)findViewById(R.id.serieDT);
        serie.setText("Počet sérií : "+ dennyTrening.getAktualnyCvik().getPocetSerii());
        TextView serieOstava = (TextView)findViewById(R.id.serieOstavaDT);
        serieOstava.setText("Ostáva : "+ dennyTrening.ostavaSerii());
        TextView opakovanie = (TextView)findViewById(R.id.opakovanieDT);
        opakovanie.setText("Počet opakovaní: "+ dennyTrening.getAktualnyCvik().getPocetOpakovani());
        setCas();
    }

    /**
     * Ukončí trening. Skryje button pokračovať.
     */
    public void koniec()
    {
        TextView serieOstava = (TextView)findViewById(R.id.serieOstavaDT);
        serieOstava.setText("Ostáva : 0");
        Button pokracuj = findViewById(R.id.buttonContinue);
        pokracuj.setVisibility(View.GONE);
        dennyTrening.setTrening(null);
    }

    /**
     * Skryje komponenty ktorá sa nemajú zobrazovať ak nie je spustený trening
     */
    public void schovajItemy()
    {
        Button pokracuj = findViewById(R.id.buttonContinue);
        pokracuj.setVisibility(View.GONE);
        Button showTrening = findViewById(R.id.showTrening);
        showTrening.setVisibility(View.GONE);
        Button exitTrening = findViewById(R.id.exitTrening);
        exitTrening.setVisibility(View.GONE);
        TextView nazov = (TextView)findViewById(R.id.nazovDT);
        nazov.setVisibility(View.GONE);
        TextView serie = (TextView)findViewById(R.id.serieDT);
        serie.setVisibility(View.GONE);
        TextView serieOstava = (TextView)findViewById(R.id.serieOstavaDT);
        serieOstava.setVisibility(View.GONE);
        TextView opakovanie = (TextView)findViewById(R.id.opakovanieDT);
        opakovanie.setVisibility(View.GONE);
        TextView timer = (TextView)findViewById(R.id.timer);
        timer.setText("00 s");
        timer.setVisibility(View.GONE);
    }
}
