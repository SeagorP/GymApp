package com.example.gymapp;

/**
 * Trieda mi vytvára druhý thread ktorý používam na zistovanie času prestávky.
 */
public class MyThread extends Thread {
    private DennyTrening daily;
    private boolean jeKoniec;

    /**
     * Nastavi koniec na false.
     */
    public MyThread ()
    {
        jeKoniec = false;
    }

    /**
     * Spustenie threadu ktorý sa uspí na 1 sekundu. Volá metódu zniž čas.
     */
    @Override
    public void run() {
        super.run();
        while(!jeKoniec)
        {
            try
            {
                Thread.sleep(1000);
                daily.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        daily.znizCas();
                    }
                });
            }
            catch (Exception e)
            {

            }
        }
    }

    /**
     * Zastavenie threadu.
     */
    public void zastav()
    {
        jeKoniec = true;
        daily = null;
    }

    /**
     * Spustenie threadu.
     * @param dailyTrening
     */
    public void spusti(DennyTrening dailyTrening)
    {
        this.daily = dailyTrening;
        jeKoniec = false;
        start();
    }

    /**
     * Nastavenie z ktoréj aktivity ma thread volať funkcie.
     * @param newDaily
     */
    public void setDaily(DennyTrening newDaily)
    {
        daily = newDaily;
    }
}
