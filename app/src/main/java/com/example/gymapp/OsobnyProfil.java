package com.example.gymapp;

import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Trieda si uchováva údaje o uživatelovi ktorý si nastavil. Stará sa aj o ich ukladanie a načítanie.
 * Trieda je vytvorená ako jedináčik.
 */
public class OsobnyProfil {
    private static OsobnyProfil profil;
    private String meno;
    private String priezvisko;
    private int vaha;
    private int vek;
    private int cielHmotnost;
    private String motivacia;
    private boolean nacitaneData;

    /**
     * Nastaví hotnoty na základné. Konštruktor je private.
     */
    private OsobnyProfil()
    {
        meno = "Meno";
        priezvisko = "Priezvisko";
        vaha = 0;
        vek = 0;
        cielHmotnost = 0;
        motivacia = "Sem zadaj svoj osobný citát, motiváciu alebo inú správu ktorá ta vystihuje.";
        nacitaneData = false;
    };

    /**
     * Vráti seba ak už je vytvorená.
     * @return
     */
    public static OsobnyProfil getOsobnyProfil()
    {
        if (profil == null)
        {
            profil = new OsobnyProfil();
        }
        return profil;
    }

    /**
     * Permanentné uloženie osobných informacii.
     * @param save
     */
    public void saveOsobnyProfil(SharedPreferences.Editor save)
    {
        if (!nacitaneData)
            return;
        save.putString("os_meno", meno);
        save.putString("os_priezvisko", priezvisko);
        save.putString("os_motivacia", motivacia);
        save.putInt("os_vaha", vaha);
        save.putInt("os_vek", vek);
        save.putInt("os_ciel", cielHmotnost);
        save.apply();
    }

    /**
     * Načítanie informacií o uźivatelovi.
     * @param load
     */
    public void loadOsobnyProfil(SharedPreferences load)
    {
        if (nacitaneData)
            return;
        meno = load.getString("os_meno", "Error");
        priezvisko = load.getString("os_priezvisko", "Error");
        motivacia = load.getString("os_motivacia", "Error");
        vaha = load.getInt("os_vaha", 0);
        vek = load.getInt("os_vek", 0);
        cielHmotnost = load.getInt("os_ciel", 0);
        nacitaneData = true;
    }


    /**
     * Nastaví atribút motivácia.
     * @param motivacia
     */
    public void setMotivacia(String motivacia) {
        this.motivacia = motivacia;
    }

    /**
     * Nastaví atribút CielHmotnost.
     * @param cielHmotnost
     */
    public void setCielHmotnost(int cielHmotnost) {
        this.cielHmotnost = cielHmotnost;
    }

    /**
     * Nastaví atribút vek.
     * @param vek
     */
    public void setVek(int vek) {
        this.vek = vek;
    }

    /**
     * Nastaví atribút váha.
     * @param vaha
     */
    public void setVaha(int vaha) {
        this.vaha = vaha;
    }

    /**
     * Nastaví atribút priezvisko.
     * @param priezvisko
     */
    public void setPriezvisko(String priezvisko) {
        this.priezvisko = priezvisko;
    }

    /**
     * Nastaví atribút meno.
     * @param meno
     */
    public void setMeno(String meno) {
        this.meno = meno;
    }

    /**
     * Vráti atribút motivácia.
     * @return
     */
    public String getMotivacia() {
        return motivacia;
    }

    /**
     * Vráti atribút cielHmostnost.
     * @return
     */
    public int getCielHmotnost() {
        return cielHmotnost;
    }

    /**
     * Vráti atribút vek.
     * @return
     */
    public int getVek() {
        return vek;
    }

    /**
     * Vráti atribút vaha.
     * @return
     */
    public int getVaha() {
        return vaha;
    }

    /**
     * Vráti atribút priezvisko.
     * @return
     */
    public String getPriezvisko() {
        return priezvisko;
    }

    /**
     * Vráti atribút meno.
     * @return
     */
    public String getMeno() {
        return meno;
    }
}
