import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


// Verwaltet Mausinput. Wird zum Spiel als MouseListener hinzugefügt

public class MausModul extends MouseAdapter {
	private Spiel spiel;

	public MausModul(Spiel spiel) {
		this.spiel = spiel;
	}

	// Aufgerufen wenn Maus betätigt wird
	public void mousePressed(MouseEvent evt) {
		Vek2 mpos = new Vek2(evt.getPoint());   // Maus-Positionen auf Zeichenfläche
		Vek2 dim = spiel.gibDim();   // Dimension der Zeichenfläche
		int taste = evt.getButton();   // Gedrückte Maustaste

		if (spiel.debugAktiv()) System.out.println("Maustaste " + taste + " betaetigt");

		// Linksklick
		if (taste == MouseEvent.BUTTON1) {
			// Schliess-Button (Leiste)
			if (mpos.auf(new Vek2(dim.ix()-25, 5), new Vek2(20, 20))) {
				spiel.beenden();
				return;
			}
		}
		/*
		else if (taste == MouseEvent.BUTTON3) {
			// Rechtsklick
		}
		*/
	}
}