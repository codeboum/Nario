import java.awt.Graphics;


public class Nachricht {
	String nachricht;
	int zeit;

	public Nachricht(String nachricht, int sekunden) {
		this.nachricht = nachricht;
		if (nachricht.length() > 100) nachricht = nachricht.substring(0, 100);
		zeit = (int) (sekunden * Spiel.FPS);
	}
	public Nachricht(String nachricht) {
		this.nachricht = nachricht;
		if (nachricht.length() > 100) nachricht = nachricht.substring(0, 100);
		zeit = (int) (5 * Spiel.FPS);
	}

	public boolean tick() {
		zeit--;
		if (zeit <= 0) {
			return false;
		}
		return true;
	}

	public void render(Graphics gfx) {
		gfx.setColor(Design.ROT);
		gfx.setFont(Design.TEXT14);
		gfx.drawString(nachricht, 5, Leiste.HOEHE + 16);
	}
}