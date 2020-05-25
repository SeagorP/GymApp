package com.example.gymapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import java.util.ArrayList;

/**
 * Aktivita ktorá zobrazuje tréningy a dovoluje ich pridávať.
 */
public class TreningyRecycler extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    private MyRecyclerViewAdapter adapter;
    private ArrayList<String> zoznamTreningov;
    private ZoznamTreningov zoznam;
    private static final int PRIDAJ_TRENING_RESULT = 0;
    private static final int OPEN_TRENING_RESULT = 0;

    /**
     * Načíta údaje o treningoch a zobrazí ich pomocov Recycler view
     * Nastaví button pridania tréningu.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teningy);

        getSupportActionBar().hide();

        //Button add trening
        Button pridajTrening = findViewById(R.id.addTrening);
        pridajTrening.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                pridajTrening();
            }
        });
    }

    /**
     * Po kliknutí na item v recycler view ho otvorí.
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(TreningyRecycler.this, Trening.class);
        intent.putExtra("sendValue", adapter.getItem(position));
        startActivityForResult(intent, OPEN_TRENING_RESULT);
    }

    /**
     * Keď sa ukončí aktivita ktorá bola spustená z tejto aktivity obnoví udaje v recycler view.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refresRecycler();
    }

    /**
     * Volá funkciu na načítanie údajov o treningu.
     */
    @Override
    protected void onStart() {
        super.onStart();
        //load
        zoznam = ZoznamTreningov.getUdajeOTreningu();
        SharedPreferences load = getPreferences(MODE_PRIVATE);
        zoznam.loadTrening(load);
        //nacitanie treningov
        zoznam = ZoznamTreningov.getUdajeOTreningu();
        refresRecycler();
    }

    /**
     * Volá funkciu na uloženie údajov o treningu.
     */
    @Override
    protected void onStop() {
        super.onStop();
        zoznam = ZoznamTreningov.getUdajeOTreningu();
        SharedPreferences.Editor load = getPreferences(MODE_PRIVATE).edit();
        zoznam.saveTrening(load);
    }

    /**
     * Obnoví udaje v recycler view.
     */
    public void refresRecycler()
    {
        zoznamTreningov = zoznam.getZoznamNazvou();
        RecyclerView recyclerView = findViewById(R.id.zoznamTest);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, zoznamTreningov);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Dialoch na pridanie treningu.
     * Pouźité zdroje zdroje https://stackoverflow.com/questions/18799216/how-to-make-a-edittext-box-in-a-dialog
     */
    public void pridajTrening()// zdroje https://stackoverflow.com/questions/18799216/how-to-make-a-edittext-box-in-a-dialog
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(TreningyRecycler.this);
        dialog.setTitle(R.string.zadajNazovTreningu);
        dialog.setMessage(R.string.menoUnikat);

        final EditText input = new EditText(TreningyRecycler.this);
        LinearLayout.LayoutParams layoutParametre = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(layoutParametre);
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
                        pridajTrening(input.getText().toString());
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
     * Pridá tréning ak je to moźné.
     * @param nazovTreningu
     */
    public void pridajTrening(String nazovTreningu)
    {
        if (!zoznam.skusPridaTrening(nazovTreningu))
        {
            CharSequence text = getString(R.string.WarningMeno);
            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            zoznam.pridajTrening(nazovTreningu);
            refresRecycler();
        }
    }

}
