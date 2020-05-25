package com.example.gymapp;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

/**
 * Trieda slúži na ukladanie treningov tiež je schopná ich uložiť a načítať. Je vytvorená ako jedináčik.
 */
public class ZoznamTreningov {
    private static ZoznamTreningov udaje;
    private ArrayList<UdajeTrening> zoznamTreningov;
    private boolean nacitaneData;

    /**
     * Private konštruktor ktorý si vytvorí list Treningov.
     */
    private ZoznamTreningov()
    {
        zoznamTreningov = new ArrayList<>();
        nacitaneData = false;
    };

    /**
     * Ak už existuje táto trieda vráti ju.
     * @return
     */
    public static ZoznamTreningov getUdajeOTreningu()
    {
        if (udaje == null)
        {
            udaje = new ZoznamTreningov();
        }
        return udaje;
    }

    /**
     * Pridá trening.
     * @param meno
     */
    public void pridajTrening(String meno)
    {
        UdajeTrening udaj = new UdajeTrening(meno);
        zoznamTreningov.add(udaj);
    }

    /**
     * Vráti true ak uź existuje trening s zadaným menom.
     * @param meno
     * @return
     */
    public boolean skusPridaTrening(String meno)
    {
        for(UdajeTrening udaje : zoznamTreningov)
        {
            if (udaje.getNazov().equals(meno))
                return false;
        }
        return true;
    }

    /**
     * Vráti list názvou treningov.
     * @return
     */
    public ArrayList<String> getZoznamNazvou()
    {
        ArrayList<String> zoznamNazvou = new ArrayList<>();
        for (int i = 0; i < zoznamTreningov.size(); ++i)
        {
            zoznamNazvou.add(zoznamTreningov.get(i).getNazov());
        }
        Collections.sort(zoznamNazvou);
        return zoznamNazvou;
    }

    /**
     * Vráti trening podla názvu.
     * @param meno
     * @return
     */
    public UdajeTrening getTreningByNazov(String meno)
    {
        for(UdajeTrening udaje : zoznamTreningov)
        {
            if (udaje.getNazov().equals(meno))
                return udaje;
        }
        return null;
    }

    /**
     * Vráti názov treningu ktorý nemá žiadne cviky.
     * @return String
     */
    public String masPrazdneTreningy()
    {
        for(UdajeTrening udaje : zoznamTreningov)
        {
            if (udaje.getPocetCvikov() == 0)
                return udaje.getNazov();
        }
        return null;
    }

    /**
     * Zmaže trening.
     * @param meno
     */
    public void deleteTrening(String meno)
    {
        for (int i = 0; i < zoznamTreningov.size(); ++i)
        {
            if (zoznamTreningov.get(i).getNazov().equals(meno))
                zoznamTreningov.remove(i);
        }
    }

    /**
     * Uloží údaje o treningoch.
     * @param save
     */
    public void saveTrening(SharedPreferences.Editor save)
    {
        if (!nacitaneData)
            return;
        save.putInt("pocetTreningov", zoznamTreningov.size());
        for (int i = 0; i < zoznamTreningov.size(); ++i)
        {
            save.putString("trening" + i, zoznamTreningov.get(i).getNazov());
        }
        save.commit();
    }

    /**
     * Načíta údaje o treningoch.
     * @param load
     */
    public void loadTrening(SharedPreferences load)
    {
        if (nacitaneData)
            return;
        int pocet = load.getInt("pocetTreningov", 0);
        for (int i = 0; i < pocet; ++i)
        {
            String nazov = load.getString("trening" + i, "Eror");
            UdajeTrening udaj = new UdajeTrening(nazov);
            zoznamTreningov.add(udaj);
        }
        nacitaneData = true;
    }
}
