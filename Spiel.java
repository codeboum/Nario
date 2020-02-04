import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


public class Spiel extends Canvas implements Runnable{
    public static void main(String[] args) {
        System.out.println("*Mario Voice* - Here we go!");
        new Spiel();
    }

    private Thread thread;
    private boolean laufend;

    int fps = 0;

    private Fenster fenster;

    public Spiel() {
        laufend = false;

        fenster = new Fenster(this);
    }


    public void tick() {

    }

    public void render() {
        BufferStrategy buffer = this.getBufferStrategy();
        if (buffer == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics gfx = buffer.getDrawGraphics();

        gfx.setColor(Color.BLACK);
        gfx.fillRect(0, 0, this.getWidth(), this.getHeight());


        gfx.dispose();
        buffer.show();
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
        requestFocus();
        long letztZeit = System.nanoTime();
        double tickAnzahl = 60.0;
        double ns = 1000000000 / tickAnzahl;
        double delta = 0.0;
        long uhr = System.currentTimeMillis();
        int frames = 0;

        while (laufend) {
            long jetzt = System.nanoTime();
            delta += (jetzt - letztZeit) / ns;
            letztZeit = jetzt;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (laufend) render();
            frames++;
            if (System.currentTimeMillis() - uhr > 1000) {
                uhr += 1000;
                fps = frames;
                frames = 0;
            }
        }
        anhalten();
    }


    // Unwichtig - Unterdruckt ledeglich eine Warnung
    private static final long serialVerionUID = 1L;
}