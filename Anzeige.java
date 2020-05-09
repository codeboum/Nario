import java.awt.Graphics;
import java.awt.Color;


// Verwaltet die Anzeige von Spiel-Parametern - Level, Punkte, usw
// Erbt von Design, da viel darauf zugegriffen wird und man damit "Design." in der Notation spart

public class Anzeige extends Konfig {
	Spiel spiel;

	public Anzeige(Spiel spiel) {
		this.spiel = spiel;
	}

	public void render(Graphics gfx, Vek2 dim) {
		if (spiel.gibStatus() == Spiel.Status.InGame) {
			double lebenDouble = spiel.spieler.gibLeben() * (255.0/(double) MAXLEBEN);
			int l = (int) lebenDouble;
			gfx.setColor(new Color(255-l, l, l/4));
			gfx.fillRect(20, 50, spiel.spieler.gibLeben()*2, 40);
			gfx.setColor(WEISS);
			gfx.drawRect(20, 50, (MAXLEBEN*2)-1, 39);
		}

		if (!spiel.testModusAktiv()) return;

		// Debug Anzeige - FPS, Level
		gfx.setColor(WEISS);
		gfx.setFont(TEXT14);
		gfx.drawString("FPS", 5, dim.iy()-25);
		gfx.drawString("Sound", 55, dim.iy()-25);
		gfx.drawString("Level", 105, dim.iy()-25);
		gfx.setColor(GELB);
		gfx.setFont(TEXT18);
		gfx.drawString(spiel.gibFPS()+"", 5, dim.iy()-5);
		if (SOUNDAN) {
			gfx.setColor(GRUEN);
			gfx.drawString("ON", 55, dim.iy()-5);
		} else {
			gfx.setColor(ROT);
			gfx.drawString("OFF", 55, dim.iy()-5);
		}
		gfx.setColor(GELB);
		gfx.drawString(spiel.level.gibTitel(), 105, dim.iy()-5);
	}
}