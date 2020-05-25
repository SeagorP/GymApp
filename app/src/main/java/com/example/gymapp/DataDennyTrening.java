package com.example.gymapp;

import java.util.ArrayList;

/**
 * Trieda je vytvorená ako jedináčik. Ukladá všetky potrebné údaje na spustenie tréningu.
 */
public class DataDennyTrening {
    private static DataDennyTrening udaje;
    private UdajeTrening aktualnyTrening;
    private MyThread myThread;
    private boolean hotovyTrening;
    private int cas;
    private int cisloCviku;
    private int pocetSerii;

    /**
     * Konštruktor ktorý je private nastaví úvodné hodnoty.
     */
    private DataDennyTrening()
    {
        hotovyTrening = false;
        cisloCviku = 0;
        pocetSerii = 0;
    };

    /**
     * Ak už existuje trieda DataDennyTrening vráti ju, ak nie vytvorí novú.
     * @return udaje
     */
    public static DataDennyTrening getDennyTrening()
    {
        if (udaje == null)
        {
            udaje = new DataDennyTrening();
        }
        return udaje;
    }

    /**
     * Posunie trening o jeden krok ďalej.
     * @return
     */
    public void pokracuj()
    {
        if (ostavaSerii() == 1) {
            cisloCviku++;
            pocetSerii = 0;
        }
        else
            pocetSerii++;
        cas = getAktualnyCvik().getPauza();
    }

    /**
     * Ak môže pokračovať trening vráti true ak nie false.
     * @return
     */
    public boolean mozemPokracovat()
    {
        if (cas == 0 && !jeKoniec())
            return true;
        return false;
    }

    /**
     * Ak sa v treningu uź nedá pokračovať vráti false.
     * @return
     */
    public boolean jeKoniec()
    {
        if (cisloCviku == aktualnyTrening.getPocetCvikov() - 1 && ostavaSerii() == 1)
            return true;
        return false;
    }

    /**
     * Vráti počet sérií ktoré ostávaju.
     * @return
     */
    public int ostavaSerii()
    {
        return aktualnyTrening.getCvikById(cisloCviku).getPocetSerii() - pocetSerii;
    }

    /**
     * Vráti aktualny cvik ktorý je spustený.
     * @return
     */
    public Cvik getAktualnyCvik()
    {
        return aktualnyTrening.getCvikById(cisloCviku);
    }

    /**
     * Ak existuje trening zastaví ho a nastaví nový.
     * @param trening
     */
    public void setTrening(UdajeTrening trening)
    {
        if (myThread != null)
            zastavThread();
        pocetSerii = 0;
        hotovyTrening = false;
        cisloCviku = 0;
        cas = 0;
        aktualnyTrening = trening;
    }

    /**
     * Vráti aktualny trening.
     * @return
     */
    public UdajeTrening getTrening()
    {
        return aktualnyTrening;
    }

    /**
     * Vráti true ak je nastavený nejaký trening, v opačnom pripade vráti false.
     * @return
     */
    public boolean jeZvolenyTrening()
    {
        if (aktualnyTrening == null)
            return false;
        return true;
    }

    /**
     * Spusti thred ktorý odpočítava pauzu.
     * @param daily
     */
    public void spustiThread(DennyTrening daily)
    {
        if(myThread != null)
        {
            myThread.zastav();
        }
        myThread = new MyThread();
        myThread.spusti(daily);

    }

    /**
     * Zastaví thred ktorý odpočítava pauzu.
     */
    public void zastavThread()
    {
        myThread.zastav();
        myThread = null;
    }

    /**
     * Nastaví threadu aktivitu ktorá je aktuálna.
     * @param daily
     */
    public void setThread(DennyTrening daily)
    {
        if(myThread != null)
            myThread.setDaily(daily);
    }

    /**
     * Vráti čas pauzy.
     * @return cas
     */
    public int getCas()
    {
        return cas;
    }

    /**
     * Zníži čas a vráti true ak je čas 0.
     * @return
     */
    public boolean znizCas()
    {
        cas--;
        if (cas == 0)
            return true;
        return false;
    }

}
