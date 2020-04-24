import java.awt.Color;
import java.awt.Font;


// Hier werden alle verwendeten Farben und Schriftarten des Spiels gespeichert, sowie andere nützliche Parameter

public abstract class Design {
	public static String TITEL = "NARIO";
	public static String ADMINCODE = "";
	public static String CURSOR = "_";
	public static int    MAXNAMELAENGE = 20;
	public static Color  WEISS = Color.WHITE;
	public static Color  SCHWARZ = Color.BLACK;
	public static Color  ROT = Color.RED;
	public static Color  DEBUG = Color.YELLOW;
	public static Color  FARBE = Color.GREEN;
	public static Color  LEISTE = new Color(150, 150, 150, 150);
	public static Font   TEXTTITEL = new Font("Bahnschrift Semibold", 1, 20);
	public static Font   TEXT14 = new Font("Consolas", 1, 14);
	public static Font   TEXT18 = new Font("Consolas", 1, 18);
	public static Font   TEXT36 = new Font("Consolas", 1, 36);
}