package com.example.gymapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Aktivita ktorá zobrazuje všetky cviky daného treningu, nastaviť ich ako daily alebo ich zmazať.
 */
public class Trening extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{
    private MyRecyclerViewAdapter adapter;
    private UdajeTrening trening;
    private String nazov;

    /**
     * Načítanie cvikov daného treningu a zobrazenie pomocou recycler view.
     * Nastavenie buttonov.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trening);

        getSupportActionBar().hide();

        Intent intent = getIntent();
        nazov = intent.getStringExtra("sendValue");

        final ZoznamTreningov zoznam = ZoznamTreningov.getUdajeOTreningu();
        trening = zoznam.getTreningByNazov(nazov);

        final TextView menoText = (TextView) findViewById(R.id.TreningNazov);
        menoText.setText(nazov);

        Button addTrening = findViewById(R.id.addTrening);
        addTrening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trening.this, PridajCvik.class);
                intent.putExtra("sendTreningToPridajCvik", trening.getNazov());
                startActivity(intent);
            }
        });

        Button deleteTrening = findViewById(R.id.deleteTrening);
        deleteTrening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoznam.deleteTrening(nazov);
                finish();
            }
        });

        Button setToDaily = findViewById(R.id.setToDaily);
        setToDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trening.getPocetCvikov() == 0)
                {
                    CharSequence text = "Trening neobsahuje žiadne cviky.";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                    return;
                }
                DataDennyTrening.getDennyTrening().setTrening(trening);
                CharSequence text = getString(R.string.setDaily);
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });

        Button editTrening = findViewById(R.id.editTrening);
        editTrening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zmenNazov();
            }
        });
    }

    /**
     * Volá funkciu ktorá načíta údaje o cvikoch a obnoví recycler view.
     */
    @Override
    protected void onStart() {
        super.onStart();
        //load
        SharedPreferences load = getPreferences(MODE_PRIVATE);
        trening.loadTrening(load);
        refresRecycler();
    }

    /**
     * Volá funkciu na uloženie údajov o cvikoch.
     */
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor save = getPreferences(MODE_PRIVATE).edit();
        trening.saveTrening(save);
    }

    /**
     * Obnový dáta ktoré zobrazuje recycler view.
     */
    public void refresRecycler()
    {
        ArrayList<String> test = trening.getListCvikov();
        RecyclerView recyclerView = findViewById(R.id.recyclerCvik);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, test);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Po kliknutí na daný údaj v recycler viu ho otvori.
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(Trening.this, CvikZobrazenie.class);
        intent.putExtra("sendTreningToCvik", adapter.getItem(position));
        intent.putExtra("sendTreningToCvik2", trening.getNazov());
        startActivity(intent);
    }

    /**
     * Vytvorenie dialogu na zmenu mena
     * Použité zdroje zdroje https://stackoverflow.com/questions/18799216/how-to-make-a-edittext-box-in-a-dialog
     */
    public void zmenNazov()// zdroje https://stackoverflow.com/questions/18799216/how-to-make-a-edittext-box-in-a-dialog
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Trening.this);
        dialog.setTitle(R.string.zadajNazovCviku);
        dialog.setMessage(R.string.menoUnikat);

        final EditText input = new EditText(Trening.this);
        LinearLayout.LayoutParams layoutParametre = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(layoutParametre);
        dialog.setView(input);

        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editName(input.getText().toString());
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
     * Funkcia ktorá upraví meno ak je to možné
     * @param meno
     */
    public void editName(String meno)
    {
        if (meno.equals(trening.getNazov()))
        {
            trening.setNazov(meno);
            return;
        }
        ZoznamTreningov zoznam = ZoznamTreningov.getUdajeOTreningu();
        if (!zoznam.skusPridaTrening(meno))
        {
            CharSequence text = getString(R.string.WarningMeno);
            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            trening.setNazov(meno);
            nazov = meno;
            final TextView menoText = (TextView) findViewById(R.id.TreningNazov);
            menoText.setText(nazov);
        }
    }
}
