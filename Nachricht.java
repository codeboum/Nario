import java.awt.Graphics;


// Nachrichten für jegliche Sachen wie Fehlermeldungen etc.

public class Nachricht {
	String text;
	int zeit;

    // In den 2 Konstruktoren werden die Nachrichtenlängen unterschiedlich festgelegt
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

	// Regelt, dass die Nachrichten wieder verschwinden
	public boolean tick() {
		zeit--;
		if (zeit <= 0) {
			return false;
		}
		return true;
	}

	// Zeichnet die Nachricht auf den Bildschirm
	public void render(Graphics gfx, int index) {
		gfx.setColor(Design.ROT);
		gfx.setFont(Design.TEXT14);
		gfx.drawString(text, 5, Leiste.HOEHE + 16 * (index+1));
	}

	public String gibText() { return text; }
}