import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;
import java.util.LinkedList;


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

    private boolean adminModus;
    private Status status;
    private int fps;   // Speichert FPS, wird in der Game-Loop einmal pro Sekunde aktualisiert

    
    public  NachrichtenManager nachrichten;
    private DateiModul dateiModul;

    private Leiste leiste;
    private Anzeige anzeige;
    private Menu menu;

    
    public  Spieler spieler;
    public  Level level;

    BufferedImage hintergrund;
    Ton musik;

    // Initializiert alle Attribute und Referenzen des Spiels und lädt Dateien
    public Spiel() {
        Dimension b = Toolkit.getDefaultToolkit().getScreenSize();
        Vek2 bildschirm = new Vek2(b);
        
        BufferedImage narioStand = null;
        Animation narioLauf = new Animation(new LinkedList<BufferedImage>(), new Vek2(), 8);
        BildLader bildLader = new BildLader(); try {
            // Hier werden Bilder geladen
            BufferedImage hintergrundOriginal = bildLader.laden("\\res\\Hintergrund.png");
            Image geScaled = hintergrundOriginal.getScaledInstance(bildschirm.ix(), bildschirm.iy(), Image.SCALE_SMOOTH);
            hintergrund = new BufferedImage(bildschirm.ix(), bildschirm.iy(), 1);
            hintergrund.getGraphics().drawImage(geScaled, 0, 0, null);
            narioStand = bildLader.laden("\\res\\Nario_Stand.png");
            LinkedList<BufferedImage> narioLaufBilder = new LinkedList<BufferedImage>();
            for(int i = 0; i < 8; i++) {
                narioLaufBilder.add(bildLader.laden("\\res\\Nario_Lauf_"+i+".png"));
            }
            narioLauf = new Animation(narioLaufBilder, new Vek2(80, 100), 8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        musik = new Ton("\\res\\Nario.wav");

        laufend = false;
        adminModus = false;
        status = Status.HauptMenu;
        fps = 0;

        
        nachrichten = new NachrichtenManager();
        dateiModul = new DateiModul(Paths.get(System.getProperty("user.dir")));

        leiste = new Leiste(this);
        anzeige = new Anzeige(this);
        menu = new Menu(this);

        addKeyListener(new TastenModul(this));   // Ein TastenModul wird als KeyListener hinzugefügt
        addMouseListener(new MausModul(this));   // Ein MausModul wird als MouseListener hinzugefügt


        spieler = new Spieler(bildschirm, narioLauf, narioStand, "");
        String levelDaten = dateiModul.laden("\\levels\\level1.txt");
        level = new Level(levelDaten);


        new Fenster(this);   // Im Konstruktor des Fensters wird starten() aufgerufen, was dann den Thread startet
    }


    // Hier werden Logikupdates ausgeführt
    public void tick() {
        switch (status) {
            case InGame:
                spieler.tick();
                level.tick(spieler);
                break;
            case Pausiert:
                break;
            case HauptMenu:
            case AdminMenu:
                break;
            case BenutzerLogin:
            case AdminLogin:
                menu.cursorTick();
                break;
        }
        nachrichten.tick();
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

        // Hier alle SpielObjekte etc zeichnen
        gfx.drawImage(hintergrund, 0, 0, null);
        switch (status) {
            case InGame:
                level.render(gfx);
                spieler.render(gfx);
                anzeige.render(gfx, gibDim());
                break;
            case Pausiert:
                level.render(gfx);
                spieler.render(gfx);
            case HauptMenu:
            case AdminMenu:
            case BenutzerLogin:
            case AdminLogin:
                gfx.setColor(new Color(0, 0, 0, 150));
                gfx.fillRect(0, 0, this.getWidth(), this.getHeight());
                anzeige.render(gfx, gibDim());
                menu.render(gfx);
                break;
        }
        leiste.render(gfx);
        nachrichten.render(gfx);


        // Grafik-Objekt wird entsorgt und Frame wird angezeigt
        gfx.dispose();
        buffer.show();
    }


    public Status gibStatus() { return status; }
    // Methoden zur Statusveränderung
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
    public void adminMenu() {
        status = Status.AdminMenu;
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

    public boolean adminModusAktiv() { return adminModus; }
    public void adminModusAktivieren() {
        adminModus = true;
        spieler.setzName("ADMIN");
    }
    public void adminModusBeenden() {
        adminModus = false;
        spieler.setzName("");
    }

    public void soundAn() {
        Konfig.SOUNDAN = true;
        musik.spielen(true);
    }
    public void soundAus() {
        Konfig.SOUNDAN = false;
        musik.stoppen();
    }

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
        AdminMenu,
        BenutzerLogin,
        AdminLogin;
    }


    // Erzeugt und startet den Thread, in dem die Game-Loop läuft
    public synchronized void starten() {
        thread = new Thread(this);
        thread.start();
        laufend = true;
        this.requestFocus();
        musik.spielen(true);
        // Vorübergehend
        adminModusAktivieren();
		adminMenu();
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
    // Die Game-Loop ruft so schnell wie möglich, das bild wird in jedem Durchlauf gezeichnet (render())
    // 60x pro Sekunde (definiert als tickAnzahl) ruft sie tick() auf,
    //  und berechnet einmal pro Sekunde die Bildrate (fps)
    public void run() {
        this.requestFocus();
        long letztZeit = System.nanoTime();
        double tickAnzahl = Konfig.FPS;
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