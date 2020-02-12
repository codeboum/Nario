import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;


// Verwaltet alle zentralen Spielprozesse wie bsp. Game-Loop, Zeichnen
// Erbt von Canvas - Wird im Fenster als Canvas eingefügt, worüber dann auch das Zeichnen erfolgt
// Implementiert Runnable, damit wird ein separater Thread erzeugt, der die Game-Loop ausführt

public class Spiel extends Canvas implements Runnable {
    public static void main(String[] args) {
        // Beim Start wird diese Methode automatisch ausgeführt,
        //  kreiert ein Spiel Objekt, welches im Konstruktor
        //  ein Fenster kreiert, welches das Spiel dann startet
        new Spiel();
    }

    private Thread thread;   // Thread, in dem die Game-Loop des Spiels läuft
    private boolean laufend;   // ob der Thread läuft

    private Status status;
    private boolean debug;
    private int level;
    private int fps;   // Speichert FPS, wird in der Game-Loop einmal pro Sekunde aktualisiert

    private ObjektManager objekte;
    private DateiModul dateiModul;

    private Leiste leiste;
    private Anzeige anzeige;
    private Menu menu;

    BufferedImage bildSpieler;

    public Spiel() {
        BildLader bildLader = new BildLader(); try {
            // Hier Sprites laden
            bildSpieler = bildLader.laden("\\res\\Nario.png");
        } catch (Exception e) { e.printStackTrace(); }

        laufend = false;
        status = Status.BenutzerLogin;
        debug = true;
        level = 1;
        fps = 0;

        objekte = new ObjektManager();
        dateiModul = new DateiModul(Paths.get(System.getProperty("user.dir")));

        leiste = new Leiste(this);
        anzeige = new Anzeige(this);
        menu = new Menu(this);

        addKeyListener(new TastenModul(this));   // Ein TastenModul wird als KeyListener hinzugefügt
        addMouseListener(new MausModul(this));   // Ein MausModul wird als MouseListener hinzugefügt


        // Hier SpielObjekte hinzufügen
        Dimension b = Toolkit.getDefaultToolkit().getScreenSize();
        Vek2 bildschirm = new Vek2(b);
        objekte.adden(new Spieler(bildschirm, new Animation(bildSpieler, 1, new Vek2(80, 100))));


        new Fenster(this);   // Im Konstruktor des Fensters wird starten() aufgerufen, was dann den Thread startet
    }


    // Hier werden Logikupdates ausgeführt
    public void tick() {
        switch (status) {
            case InGame:
            case Pausiert:
                objekte.tick();
                break;
            case HauptMenu:
                break;
            case BenutzerLogin:
            case AdminLogin:
                menu.cursorTick();
                break;
        }
    }

    // Hier wird die Zeichenfläche bearbeitet bzw bemalt
    public void render() {
        // Buffer-Kontext und Grafik-Objekt wird geholt
        BufferStrategy buffer = this.getBufferStrategy();
        if (buffer == null) {   // Falls es noch keinen gibt (Beim ersten Frame wird er kreiert)
            this.createBufferStrategy(3);
            return;
        }
        Graphics gfx = buffer.getDrawGraphics();   // Durch das Grafik-Objekt werden alle Zeichenkommandos aufgeführt

        // Schwarzen Hintergrund zeichnen
        gfx.setColor(Color.BLACK);
        gfx.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Hier alles zeichnen
        switch (status) {
            case InGame:
            case Pausiert:
                objekte.render(gfx);
                anzeige.render(gfx, gibDim());
                break;
            case HauptMenu:
            case BenutzerLogin:
            case AdminLogin:
                anzeige.render(gfx, gibDim());
                menu.render(gfx);
                break;
        }
        leiste.render(gfx);


        // Grafik-Objekt wird entsorgt und Frame wird angezeigt
        gfx.dispose();
        buffer.show();
    }


    public Status gibStatus() { return status; }
    public void inGame() {
        status = Status.InGame;
        this.requestFocus();
    }
    public void pausiert() {
        status = Status.Pausiert;
        this.requestFocus();
    }
    public void hauptMenu() {
        status = Status.HauptMenu;
        this.requestFocus();
    }
    public void benutzerLogin() {
        menu.textInputVorbereiten();
        status = Status.BenutzerLogin;
        this.requestFocus();
    }
    public void adminLogin() {
        menu.textInputVorbereiten();
        status = Status.AdminLogin;
        this.requestFocus();
    }

    public boolean debugAktiv() { return debug; }
    public int gibLevel() { return level; }
    public int gibFPS() { return fps; }
    public Vek2 gibDim() { return new Vek2(this.getSize()); }
    public DateiModul gibDateiModul() { return dateiModul; }
    public Menu gibMenu() { return menu; }


    // Beendet das ganze Programm - Hier wird später das Speichern von Dateien stattfinden
    public void beenden() {
        System.exit(0);
    }


    public enum Status {
        InGame,
        Pausiert,
        HauptMenu,
        BenutzerLogin,
        AdminLogin;
    }


    // Erzeugt und startet den Thread, in dem die Game-Loop läuft
    public synchronized void starten() {
        thread = new Thread(this);
        thread.start();
        laufend = true;
    }

    // Beendet den Thread
    // Normalerweise wird das Programm einfach geschlossen, wodurch der Thread automatisch entsorgt wird
    public synchronized void anhalten() {
        try {
            thread.join();
            laufend = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Diese Methode beinhaltet die Game-Loop
    // Sie wird beim Starten des Thread automatisch ausgeführt
    // Die Game-Loop ruft 60x pro Sekunde (definiert als tickAnzahl) tick() und render() auf,
    //  und berechnet einmal pro Sekunde die Bildrate (fps)
    public void run() {
        this.requestFocus();
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

    // Unwichtig und unbenutzt - Unterdrückt ledeglich eine Warnung aus der Canvas-Klasse
    private static final long serialVersionUID = 1L;
}