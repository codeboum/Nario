import java.awt.*;


public class Spiel extends Canvas implements Runnable{
    public static void main(String[] args) {
        System.out.println("*Mario Voice* - Here we go!");
        new Spiel();
    }

    private Thread thread;
    private boolean laufend;

    private Fenster fenster;

    public Spiel() {
        laufend = false;

        fenster = new Fenster(this);
    }


    public synchronized void starten() {
        thread = new Thread(this);
        thread.start();
        laufend = true;
    }

    public synchronized void anhalten() {
        try {
            thread.join();
            laufend = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        System.out.println("Run");
    }


    // Unwichtig - Unterdruckt ledeglich eine Warnung
    private static final long serialVerionUID = 1L;
}