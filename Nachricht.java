import java.awt.Graphics;


public class Nachricht {
	String text;
	int zeit;

	public Nachricht(String text, int sekunden) {
		this.text = text;
		if (text.length() > 100) text = text.substring(0, 100);
		zeit = (int) (sekunden * Spiel.FPS);
	}
	public Nachricht(String text) {
		this.text = text;
		if (text.length() > 100) text = text.substring(0, 100);
		zeit = (int) (5 * Spiel.FPS);
	}

	public boolean tick() {
		zeit--;
		if (zeit <= 0) {
			return false;
		}
		return true;
	}

	public void render(Graphics gfx, int index) {
		gfx.setColor(Design.ROT);
		gfx.setFont(Design.TEXT14);
		gfx.drawString(text, 5, Leiste.HOEHE + 16 * (index+1));
	}

	public String gibText() { return text; }
}