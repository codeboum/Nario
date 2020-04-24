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

    public static final double FPS = 60.0;
    private Thread thread;   // Thread, in dem die Game-Loop des Spiels läuft
    private boolean laufend;   // ob der Thread läuft

    private String spielerName;

    private Status status;
    private boolean debug;
    private int level;
    private int fps;   // Speichert FPS, wird in der Game-Loop einmal pro Sekunde aktualisiert

    public  ObjektManager objekte;
    public  NachrichtenManager nachrichten;
    private DateiModul dateiModul;
    private TonSpieler ton;

    private Leiste leiste;
    private Anzeige anzeige;
    private Menu menu;

    BufferedImage hintergrund;
    BufferedImage bildSpieler;
    BufferedImage narioStand;

    public Spiel() {
        Dimension b = Toolkit.getDefaultToolkit().getScreenSize();
        Vek2 bildschirm = new Vek2(b);
        Animation narioLauf = new Animation(new LinkedList<BufferedImage>(), new Vek2(), 8);
        BildLader bildLader = new BildLader(); try {
            // Hier Sprites laden
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
        } catch (Exception e) { e.printStackTrace(); }

        laufend = false;
        spielerName = "";
        status = Status.HauptMenu;
        debug = true;
        level = 1;
        fps = 0;

        objekte = new ObjektManager();
        nachrichten = new NachrichtenManager();
        dateiModul = new DateiModul(Paths.get(System.getProperty("user.dir")));
        ton = new TonSpieler();

        leiste = new Leiste(this);
        anzeige = new Anzeige(this);
        menu = new Menu(this);

        addKeyListener(new TastenModul(this));   // Ein TastenModul wird als KeyListener hinzugefügt
        addMouseListener(new MausModul(this));   // Ein MausModul wird als MouseListener hinzugefügt


        // Hier SpielObjekte hinzufügen
        objekte.adden(new Spieler(bildschirm, narioLauf, narioStand, spielerName));


        new Fenster(this);   // Im Konstruktor des Fensters wird starten() aufgerufen, was dann den Thread startet
    }


    // Hier werden Logikupdates ausgeführt
    public void tick() {
        switch (status) {
            case InGame:
                objekte.tick();
                break;
            case Pausiert:
                break;
            case HauptMenu:
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

        // Hier alles zeichnen
        switch (status) {
            case InGame:
            case Pausiert:
                gfx.drawImage(hintergrund, 0, 0, null);
                objekte.render(gfx);
                anzeige.render(gfx, gibDim());
                break;
            case HauptMenu:
            case BenutzerLogin:
            case AdminLogin:
                gfx.drawImage(hintergrund, 0, 0, null);
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

    public void spielerNameSetzen(String n) {
        spielerName = n;
        SpielObjekt spielerSpielObjekt = objekte.suchen(SpielObjekt.Typ.Spieler);
        if (spielerSpielObjekt != null) {
            Spieler spieler = (Spieler) spielerSpielObjekt;
            spieler.setzName(spielerName);
        }
    }
    public String gibSpielerName() { return spielerName; }


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
        this.requestFocus();
        ton.spielen("\\res\\Nario.wav", true);
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
        double tickAnzahl = FPS;
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