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

		/*
		if (btn == MouseEvent.BUTTON1) {
			// Linksklick
		}
		else if (btn == MouseEvent.BUTTON3) {
			// Rechtsklick
		}
		*/
	}

	// Private Methode, die testet ob ein Punkt eine Fläche schneidet - benutzt um Mausposition auf Flächen zu testen
	// pos - Position der Fläche (obere linke Ecke)
	// dim - Dimension der Fläche
	private boolean schneidet(Vek2 maus, Vek2 pos, Vek2 dim) {
		return maus.x > pos.x && maus.x < pos.x + dim.x && maus.y > pos.y && maus.y < pos.y + dim.y;
	}
}