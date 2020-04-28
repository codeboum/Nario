import java.awt.Graphics;
import java.awt.Color;


// Nachrichten für jegliche Sachen wie Fehlermeldungen etc.

public class Nachricht {
	String text;
	Typ typ;
	int zeit;

	public enum Typ {
		Normal,
		Warnung;
	}

    // In den 2 Konstruktoren werden die Nachrichtenlängen unterschiedlich festgelegt
	public Nachricht(String text, Typ typ, int sekunden) {
		this.text = text;
		this.typ = typ;
		if (text.length() > 100) text = text.substring(0, 100);
		zeit = (int) (sekunden * Spiel.FPS);
	}
	public Nachricht(String text, Typ typ) {
		this.text = text;
		this.typ = typ;
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
		Color farbe;
		switch (typ) {
			case Warnung:
				farbe = Design.ROT;
				break;
			default:
				farbe = Design.WEISS;
		}
		gfx.setColor(farbe);
		gfx.setFont(Design.TEXT14);
		gfx.drawString(text, 5, Leiste.HOEHE + 16 * (index+1));
	}

	public String gibText() { return text; }
}