import java.awt.Graphics;


// Verwaltet die Anzeige von Spiel-Parametern - Level, Punkte, usw
// Erbt von Design, da viel darauf zugegriffen wird und man damit "Design." in der Notation spart

public class Anzeige extends Design {
	Spiel spiel;

	public Anzeige(Spiel spiel) {
		this.spiel = spiel;
	}

	public void render(Graphics gfx, Vek2 dim) {
		int x = dim.ix();
		int y = dim.iy();

		if (!spiel.debugAktiv()) return;
		// Debug Anzeige - FPS, Level
		gfx.setColor(WEISS);
		gfx.setFont(TEXT14);
		gfx.drawString("FPS", 5, y-25);
		gfx.drawString("Level", 55, y-25);
		gfx.setColor(DEBUG);
		gfx.setFont(TEXT18);
		gfx.drawString(spiel.gibFPS()+"", 5, y-5);
		gfx.drawString(spiel.gibLevel()+"", 55, y-5);
	}
}