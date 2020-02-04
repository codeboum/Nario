import java.awt.Graphics;


// Verwaltet die Anzeige von Spiel-Parametern - Level, Punkte, usw
// Erbt von Design, da viel darauf zugegriffen wird und man damit Design. in der Notation spart

public class Anzeige extends Design {
	Spiel spiel;

	public Anzeige(Spiel spiel) {
		this.spiel = spiel;
	}

	public void render(Graphics gfx, Vek2 bild) {
		int x = bild.ix();
		int y = bild.iy();

		gfx.setColor(WEISS);
		gfx.setFont(TEXT36);
		gfx.drawString("Mit ESC beenden", 100, 100);

		// Untere Anzeige - FPS, Level
		gfx.setFont(TEXT14);
		gfx.drawString("FPS", 5, y-25);
		gfx.drawString("Level", 55, y-25);
		gfx.setColor(GELB);
		gfx.setFont(TEXT18);
		gfx.drawString(spiel.getFPS()+"", 5, y-5);
		gfx.setColor(FARBE);
		gfx.drawString(spiel.getLevel()+"", 55, y-5);
	}
}