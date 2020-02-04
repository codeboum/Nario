import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;


// Verwaltet die Anzeige von Spiel-Parametern - Level, Punkte, usw

public class Anzeige {
	Spiel spiel;

	public Anzeige(Spiel spiel) {
		this.spiel = spiel;
	}

	public void render(Graphics gfx, Vek2 bild) {
		int x = bild.ix();
		int y = bild.iy();


		// FPS-Anzeige
		gfx.setColor(Design.WEISS);
		gfx.setFont(Design.KLEINTEXT);
		gfx.drawString("FPS", 5, y-25);
		gfx.setColor(Design.GELB);
		gfx.setFont(Design.FPSTEXT);
		gfx.drawString(spiel.getFPS() + "", 5, y-5);
	}
}