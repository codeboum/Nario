import java.awt.Graphics;


// Klasse stellt Titelleiste dar

public class Leiste {
	Spiel spiel;

	public Leiste(Spiel spiel) {
		this.spiel = spiel;
	}

	public void render(Graphics gfx) {
		Vek2 dim = spiel.gibDim();

		gfx.setColor(Design.LEISTE);
		gfx.fillRect(0, 0, dim.ix(), 30);

		gfx.setColor(Design.WEISS);
		gfx.drawLine(0, 29, dim.ix()-1, 29);

		gfx.setFont(Design.TEXTTITEL);
		String titel = Design.TITEL;
		titel += (spiel.debugAktiv()) ? " - DEBUG" : "";
		gfx.drawString(titel, 5, 22);
	}
}