package com.example.gymapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Aktivita ktorá zobrazí osobné udaje a umožnuje ich nastavenie.
 */
public class OsProfilZobraz extends AppCompatActivity {
    private OsobnyProfil profil;

    /**
     * Zobrazi osobné údaje ak boli nastavené alebo defaultne hodnoty.
     * Nastaví funkciu buttonov.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_os_profil_zobraz);
        getSupportActionBar().hide();
        profil = OsobnyProfil.getOsobnyProfil();

        Button meno = findViewById(R.id.buttonMeno);
        meno.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                zmenProfil(getString(R.string.zadajMeno), getString(R.string.nemusiBytPrave), 1);
            }
        });
        Button priezvisko = findViewById(R.id.buttonPriezisko);
        priezvisko.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                zmenProfil(getString(R.string.zadajPriezvisko), getString(R.string.nemusiBytPrave), 2);
            }
        });
        Button vek = findViewById(R.id.buttonVek);
        vek.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                zmenProfil(getString(R.string.zadajVek), "", 3);
            }
        });
        Button vaha = findViewById(R.id.buttonHmot);
        vaha.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                zmenProfil(getString(R.string.zadajHmotnost), getString(R.string.jednotkyKila), 4);
            }
        });
        Button vahaCiel = findViewById(R.id.buttonCiel);
        vahaCiel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                zmenProfil(getString(R.string.zadajHmotnostCiel), getString(R.string.jednotkyKila), 5);
            }
        });
    }

    /**
     * Volá funkciu na načítanie osobných udajov.
     */
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences load = getPreferences(MODE_PRIVATE);
        profil.loadOsobnyProfil(load);
        refres();
    }

    /**
     * Volá funkciu na uloženie osobných údajov.
     */
    @Override
    protected void onStop() {
        super.onStop();
        final TextView popis = (TextView) findViewById(R.id.os_citat);
        profil.setMotivacia(popis.getText().toString());
        SharedPreferences.Editor save = getPreferences(MODE_PRIVATE).edit();
        profil.saveOsobnyProfil(save);
    }

    /**
     * Dialoch ktorý vyskočí pri kluknutí na niektorý z osobných udajoov.
     * Slúži na nastavovanie všetkých osobných udajov.
     * Upravuje osobný údaj podla zadanej hodnoty CisloAkcie.
     * Vypíše popiský ktoré dostane ako atribút.
     * Použíté zdroje zdroje https://stackoverflow.com/questions/18799216/how-to-make-a-edittext-box-in-a-dialog
     * @param alert1
     * @param alert2
     * @param cisloAkcie
     */
    public void zmenProfil(String alert1, String alert2, final int cisloAkcie)// zdroje https://stackoverflow.com/questions/18799216/how-to-make-a-edittext-box-in-a-dialog
    {

        AlertDialog.Builder dialog = new AlertDialog.Builder(OsProfilZobraz.this);
        dialog.setTitle(alert1);
        dialog.setMessage(alert2);

        final EditText input = new EditText(OsProfilZobraz.this);
        if (cisloAkcie != 1 && cisloAkcie != 2)
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
        LinearLayout.LayoutParams parametere = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        input.setLayoutParams(parametere);
        dialog.setView(input);
        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (input.getText().toString().isEmpty())
                        {
                            CharSequence text = getString(R.string.nezadanyText);
                            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (cisloAkcie == 1)
                            profil.setMeno(input.getText().toString());
                        if (cisloAkcie == 2)
                            profil.setPriezvisko(input.getText().toString());
                        if (cisloAkcie == 3)
                            profil.setVek(Integer.parseInt(input.getText().toString()));
                        if (cisloAkcie == 4)
                            profil.setVaha(Integer.parseInt(input.getText().toString()));
                        if (cisloAkcie == 5)
                            profil.setCielHmotnost(Integer.parseInt(input.getText().toString()));
                        refres();
                    }
                });
        dialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        dialog.show();
    }

    /**
     * Obnoví zobrazenie osobných udajov.
     */
    public void refres()
    {
        final TextView popis = (TextView) findViewById(R.id.os_citat);
        popis.setText(profil.getMotivacia());
        Button meno = findViewById(R.id.buttonMeno);
        meno.setText(profil.getMeno());
        Button priezvisko = findViewById(R.id.buttonPriezisko);
        priezvisko.setText(profil.getPriezvisko());

        Button vek = findViewById(R.id.buttonVek);
        vek.setText("" + profil.getVek() + " rokov");
        Button vaha = findViewById(R.id.buttonHmot);
        vaha.setText("" + profil.getVaha() + " kg");
        Button vahaCiel = findViewById(R.id.buttonCiel);
        vahaCiel.setText("" + profil.getCielHmotnost() + " kg");
    }
}
