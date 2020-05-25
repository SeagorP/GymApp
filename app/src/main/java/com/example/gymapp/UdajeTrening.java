package com.example.gymapp;

import android.content.SharedPreferences;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Trieda si uchováva údaje o treningu, je schopná si ich ukladať a načítať.
 */
public class UdajeTrening {
    private String nazov;
    private ArrayList<Cvik> zoznamCvikov;
    private boolean nacitaneData;

    /**
     * Vytvorenie treningu. Meno musí byť unikátne.
     * @param nazov
     */
    public UdajeTrening(String nazov)
    {
        this.nazov = nazov;
        zoznamCvikov = new ArrayList<Cvik>();
        nacitaneData = false;
    };

    /**
     * Vráti názov treningu.
     * @return
     */
    public String getNazov()
    {
        return nazov;
    }

    /**
     * Nastaví názov treningu.
     * @param newNazov
     */
    public void setNazov(String newNazov) {nazov = newNazov;}

    /**
     * Pokúsi sa pridať cvik ak sa to nepodarí vráti false.
     * @param nazov
     * @param pocetSerii
     * @param pocetOpakovani
     * @param pauza
     * @param popis
     * @return
     */
    public boolean pridajCvik(String nazov, int pocetSerii, int pocetOpakovani, int pauza, String popis)
    {
        for (Cvik tempCvik : zoznamCvikov)
        {
            if (tempCvik.getNazov().equals(nazov))
                return false;
        }
        Cvik novyCvik = new Cvik(nazov, pocetSerii, pocetOpakovani, pauza, popis);
        zoznamCvikov.add(novyCvik);
        return true;
    }

    /**
     * Vráti cvik podla mena.
     * @param meno
     * @return
     */
    public Cvik getCvik(String meno)
    {
        for (Cvik tempCvik : zoznamCvikov)
        {
            if (tempCvik.getNazov().equals(meno))
                return tempCvik;
        }
        return null;
    }

    /**
     * Vráti cvik podla indexu.
     * @param index
     * @return
     */
    public Cvik getCvikById(int index)
    {
        return zoznamCvikov.get(index);
    }

    /**
     * Vráti počet cvikikov.
     * @return
     */
    public int getPocetCvikov()
    {
        return zoznamCvikov.size();
    }

    /**
     * Zmaže cvik.
     * @param meno
     */
    public void zmazCvik(String meno)
    {
        for (int i = 0; i < zoznamCvikov.size(); ++i)
        {
            if (zoznamCvikov.get(i).getNazov().equals(meno))
                zoznamCvikov.remove(i);
        }
    }

    /**
     * Uloží údaje o všetkých cvikoch.
     * @param save
     */
    public void saveTrening(SharedPreferences.Editor save)
    {
        if (!nacitaneData)
            return;
        save.putInt("pocetCvikov" + nazov, zoznamCvikov.size());
        for (int i = 0; i < zoznamCvikov.size(); ++i)
        {
            save.putString(nazov + i + "nazov", zoznamCvikov.get(i).getNazov());
            save.putInt(nazov + i + "pocetSerii", zoznamCvikov.get(i).getPocetSerii());
            save.putInt(nazov + i + "pocetOpakovani", zoznamCvikov.get(i).getPocetOpakovani());
            save.putInt(nazov + i + "pauza", zoznamCvikov.get(i).getPauza());
            save.putString(nazov + i + "popis", zoznamCvikov.get(i).getPopis());
        }
        save.apply();
    }

    /**
     * Načíta údaje o všetkých cvikoch.
     * @param load
     */
    public void loadTrening(SharedPreferences load)
    {
        if (nacitaneData)
            return;
        int pocet = load.getInt("pocetCvikov" + nazov, 0);
        for (int i = 0; i < pocet; ++i)
        {
            String nazov = load.getString(this.nazov + i + "nazov", "Eror");
            int pocetSerii = load.getInt(this.nazov + i + "pocetSerii", 0);
            int pocetOpakovani = load.getInt(this.nazov + i + "pocetOpakovani", 0);
            int pauza = load.getInt(this.nazov + i + "pauza", 0);
            String popis = load.getString(this.nazov + i + "popis", "Eror");
            Cvik novyCvik = new Cvik(nazov, pocetSerii, pocetOpakovani, pauza, popis);
            zoznamCvikov.add(novyCvik);
        }
        nacitaneData = true;
    }

    /**
     * Vráti list názvou cvikov.
     * @return
     */
    public ArrayList<String> getListCvikov()
    {
        ArrayList<String> listCvikov = new ArrayList<>();
        for(Cvik cvik : zoznamCvikov)
        {
            listCvikov.add(cvik.getNazov());
        }
        return listCvikov;
    }
}
