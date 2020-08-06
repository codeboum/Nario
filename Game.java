import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;
import java.util.LinkedList;


// Handles all central Game tasks, e.g. game loop, rendering
// Inherints from Canvas - Gets added to Window as Canvas, which is also how rendering is done
// Implements Runnable, which is run by a separate Thread that runs the game loop

public class Game extends Canvas implements Runnable {
    public static void main(String[] args) {
        // Creates a Game object, which creates the Window in its constructor,
        //  which launches the game
        new Game();
    }

    private Thread thread;   // Thread that runs the game loop
    private boolean running;   // whether that thread is running

    private boolean testMode;
    private Status status;
    private int fps;   // Speichert FPS, wird in der Game-Loop einmal pro Sekunde aktualisiert

    
    public  NotifHandler notifs;
    private DataModule dataModule;

    private TitleBar titleBar;
    private Display display;
    private Menu menu;

    
    public  Player player;
    public  Level level;

    public int points;

    BufferedImage backgroundImage;
    Sound musicSound;

    // Initializiert alle Attribute und Referenzen des Spiels und lädt Dateien
    public Game() {
        Dimension sd = Toolkit.getDefaultToolkit().getScreenSize();
        Vec2 screenDim = new Vec2(sd);   // Dimensions of screen as Vec2
        
        BufferedImage narioStanding = null;
        BufferedImage narioStandingJump = null;
        BufferedImage narioMovingJump = null;
        BufferedImage spriteStone = null;
        Animation narioRunAnim = new Animation(new LinkedList<BufferedImage>(), new Vec2(), 8);
        ImageLoader imageLoader = new ImageLoader(); try {
            // Images are loaded here
            BufferedImage backgroundOriginal = imageLoader.load("\\res\\Hintergrund.png");
            Image scaledBG = backgroundOriginal.getScaledInstance(screenDim.ix(), screenDim.iy(), Image.SCALE_SMOOTH);
            backgroundImage = new BufferedImage(screenDim.ix(), screenDim.iy(), 1);
            backgroundImage.getGraphics().drawImage(scaledBG, 0, 0, null);
            narioStanding = imageLoader.load("\\res\\Nario_Stand.png");
            narioStandingJump = imageLoader.load("\\res\\Nario_Stand_Sprung.png");
            narioMovingJump = imageLoader.load("\\res\\Nario_Lauf_Sprung.png");
            spriteStone = imageLoader.load("\\res\\stone.png");
            LinkedList<BufferedImage> narioRunFrames = new LinkedList<BufferedImage>();
            for(int i = 0; i < 8; i++) {
                narioRunFrames.add(imageLoader.load("\\res\\Nario_Lauf_"+i+".png"));
            }
            narioRunAnim = new Animation(narioRunFrames, new Vec2(160, 224), 8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        musicSound = new Sound("\\res\\Nario.wav");

        running = false;
        testMode = false;
        status = Status.MainMenu;
        fps = 0;

        
        notifs = new NotifHandler();
        dataModule = new DataModule(Paths.get(System.getProperty("user.dir")));

        titleBar = new TitleBar(this);
        display = new Display(this);
        menu = new Menu(this);

        addKeyListener(new KeyModule(this));   // A KeyModule gets added as KeyListener
        addMouseListener(new MouseModule(this));   // A MouseModule gets added as MouseListener


        player = new Player(screenDim, narioRunAnim, narioStanding, narioStandingJump, narioMovingJump, "");
        String levelData = dataModule.load("\\levels\\level1.txt");
        level = new Level(levelData, screenDim, spriteStone);
        points = 0;

        new Window(this);   // Calls launch() in its constructor, which starts the thread
    }


    // Logic updates
    public void tick() {
        switch (status) {
            case InGame:
                player.tick();
                level.tick(player);
                break;
            case Paused:
                break;
            case MainMenu:
            case Highscores:
            case TestMenu:
                break;
            case NamePrompt:
            case TestLogin:
                menu.cursorTick();
                break;
        }
        notifs.tick();
    }

    // Canvas gets drawn on and shown
    public void render() {
        // Buffer Context und Graphics Objects are fetched
        BufferStrategy buffer = this.getBufferStrategy();
        if (buffer == null) {   // If there is no buffer, create it (is the case on first frame)
            this.createBufferStrategy(3);
            return;
        }
        Graphics gfx = buffer.getDrawGraphics();   // All rendering commands are done on the Graphics object

        // Black Background
        gfx.setColor(Color.BLACK);
        gfx.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Hier alle SpielObjekte etc zeichnen
        gfx.drawImage(backgroundImage, 0, 0, null);
        switch (status) {
            case InGame:
                level.render(gfx);
                player.render(gfx);
                display.render(gfx, getDim());
                break;
            case Paused:
                level.render(gfx);
                player.render(gfx);
            case MainMenu:
            case Highscores:
            case TestMenu:
            case NamePrompt:
            case TestLogin:
                gfx.setColor(new Color(0, 0, 0, 150));
                gfx.fillRect(0, 0, this.getWidth(), this.getHeight());
                display.render(gfx, getDim());
                menu.render(gfx);
                break;
        }
        titleBar.render(gfx);
        notifs.render(gfx);


        // Graphics object is disposed of and frame is shown
        gfx.dispose();
        buffer.show();
    }


    public Status getStatus() { return status; }
    // Methoden zur Statusveränderung
    public void inGame() {
        status = Status.InGame;
        this.requestFocus();
    }
    public void paused() {
        status = Status.Paused;
        this.requestFocus();
    }
    public void mainMenu() {
        status = Status.MainMenu;
        this.requestFocus();
    }
    public void highscores() {
        status = Status.Highscores;
        this.requestFocus();
    }
    public void testMenu() {
        status = Status.TestMenu;
        this.requestFocus();
    }
    public void namePrompt() {
        menu.prepareTextInput();
        status = Status.NamePrompt;
        this.requestFocus();
    }
    public void testLogin() {
        menu.prepareTextInput();
        status = Status.TestLogin;
        this.requestFocus();
    }

    public boolean testModeActive() { return testMode; }
    public void activateTestMode() {
        testMode = true;
    }
    public void endTestMode() {
        testMode = false;
    }

    public void soundOn() {
        Config.SOUNDON = true;
        musicSound.play(true);
    }
    public void soundOff() {
        Config.SOUNDON = false;
        musicSound.stop();
    }

    public int getFPS() { return fps; }
    public Vec2 getDim() { return new Vec2(this.getSize()); }
    public DataModule getDataModule() { return dataModule; }
    public Menu getMenu() { return menu; }


    // Ends the whole program - Saving of data is gonna go here
    public void end() {
        System.exit(0);
    }


    public enum Status {
        InGame,
        Paused,
        MainMenu,
        Highscores,
        TestMenu,
        NamePrompt,
        TestLogin;
    }


    // Creates and starts the game loop thread
    public synchronized void launch() {
        thread = new Thread(this);
        thread.start();
        running = true;
        this.requestFocus();
        musicSound.play(true);
        if (!Config.SOUNDON) soundOff();
        // Temporary
        activateTestMode();
		testMenu();
    }

    // Terminates the Thread
    // Normally the program is simply exited which terminates the thread automatically
    public synchronized void stopThread() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Game Loop
    // Is run automaticall upon starting of the thread - because of run() - called by Runnable
    // Game Loop runs as fast as possible, rendering on every iteration - render()
    // 60x per second (defined in amountOfTicks) tick() is run
    // FPS is calculated once per second
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = Config.FPS;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0.0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                fps = frames;
                frames = 0;
            }
        }
        stopThread();
    }
    
    // Not important and unused - Suppressed a warning from the Canvas class
    private static final long serialVersionUID = 1L;
}