import java.awt.Color;
import java.awt.Font;


// Hier werden alle verwendeten Farben und Schriftarten des Spiels gespeichert, sowie andere nützliche Parameter

public abstract class Konfig {
	public static final String TITEL = "NARIO";
	public static final String ADMINCODE = "narioadmin";
	public static String       CURSOR = "_";
	public static int          MAXNAMELAENGE = 20;
	public static final double FPS = 60.0;
	public static boolean      SOUNDAN = true;
	public static final Color  WEISS = Color.WHITE;
	public static final Color  HELLGRAU = Color.LIGHT_GRAY;
	public static final Color  SCHWARZ = Color.BLACK;
	public static final Color  ROT = Color.RED;
	public static final Color  GELB = Color.YELLOW;
	public static final Color  GRUEN = Color.GREEN;
	public static Color        LEISTE = new Color(150, 150, 150, 150);
	public static Font         TEXTTITEL = new Font("Bahnschrift Semibold", 1, 20);
	public static Font         TEXT14 = new Font("Consolas", 1, 14);
	public static Font         TEXT18 = new Font("Consolas", 1, 18);
	public static Font         TEXT36 = new Font("Consolas", 1, 36);
}