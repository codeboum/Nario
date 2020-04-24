import java.awt.Graphics;


// Klasse stellt Titelleiste dar

public class Leiste {
	public static final int HOEHE = 30;
	Spiel spiel;

	public Leiste(Spiel spiel) {
		this.spiel = spiel;
	}

	// Setzt die Titelleiste auf die gewünschten Einstellungen und zeichnet sie
	public void render(Graphics gfx) {
		Vek2 dim = spiel.gibDim();

		gfx.setColor(Design.LEISTE);
		gfx.fillRect(0, 0, dim.ix(), HOEHE);

		gfx.setFont(Design.TEXTTITEL);
		gfx.setColor(Design.WEISS);
		String titel = Design.TITEL;
		titel += (spiel.debugAktiv()) ? " - DEBUG" : "";
		gfx.drawString(titel, 5, 22);

		gfx.setColor(Design.ROT);
		gfx.fillRect(dim.ix()-40, 0, 40, 30);
		gfx.setColor(Design.WEISS);
		gfx.drawLine(dim.ix()-28, 7, dim.ix()-13, 22);
		gfx.drawLine(dim.ix()-28, 22, dim.ix()-13, 7);

		gfx.setColor(Design.WEISS);
		gfx.drawLine(0, 29, dim.ix()-1, 29);
	}
}