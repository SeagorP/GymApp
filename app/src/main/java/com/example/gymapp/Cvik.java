package com.example.gymapp;

/**
 * Trieda Cvik
 * Slúži na ukladanie informácii o konkretnom cviku.
 */
public class Cvik {
    private String nazov;
    private int pocetSerii;
    private int pocetOpakovani;
    private int pauza;
    private String popis;

    /**
     * Konštruktor triedy cvik iba nastaví hodnoty parametrov.
     * @param nazov
     * @param pocetSerii
     * @param pocetOpakovani
     * @param pauza
     * @param popis
     */

    public Cvik(String nazov, int pocetSerii, int pocetOpakovani, int pauza, String popis)
    {
        this.nazov = nazov;
        this.pocetSerii = pocetSerii;
        this.pocetOpakovani = pocetOpakovani;
        this.pauza = pauza;
        this.popis = popis;
    }

    /**
     * Geter na názov.
     * @return nazov
     */
    public String getNazov() {return nazov;}

    /**
     * Geter na počet sérií.
     * @return pocetSerii
     */
    public int getPocetSerii() {
        return pocetSerii;
    }

    /**
     * Geter na počet opakovani.
     * @return pocetOpakovani
     */
    public int getPocetOpakovani() {
        return pocetOpakovani;
    }

    /**
     * Geter na čas prestavky.
     * @return pauza
     */
    public int getPauza() {
        return pauza;
    }

    /**
     * Geter na popis.
     * @return popis
     */
    public String getPopis() {
        return popis;
    }
}
