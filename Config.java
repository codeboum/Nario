import java.awt.Color;
import java.awt.Font;


// All Fonts and Colors used, as well as other important parameters are saved here

public abstract class Config {
	public static final String TITLE = "NARIO";
	public static final String TESTCODE = "";
	public static String       CURSOR = "_";
	public static final int    MAXNAMELENGTH = 20;
	public static final int    MAXHEALTH = 100;
	public static final double FPS = 60.0;
	public static boolean      SOUNDON = false;
	public static final Color  WHITE = Color.WHITE;
	public static final Color  LIGHT_GRAY = Color.LIGHT_GRAY;
	public static final Color  BLACK = Color.BLACK;
	public static final Color  RED = Color.RED;
	public static final Color  YELLOW = Color.YELLOW;
	public static final Color  GREEN = Color.GREEN;
	public static Color        TITLE_BAR_COLOR = new Color(150, 150, 150, 150);
	public static Font         TEXT_TITLE_FONT = new Font("Bahnschrift Semibold", 1, 20);
	public static Font         TEXT14 = new Font("Consolas", 1, 14);
	public static Font         TEXT18 = new Font("Consolas", 1, 18);
	public static Font         TEXT36 = new Font("Consolas", 1, 36);
}