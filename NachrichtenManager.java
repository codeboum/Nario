import java.awt.Graphics;
import java.util.LinkedList;


// Verwaltet Ausgabe und Verschwinden der Nachrichten auf dem Bildschirm

public class NachrichtenManager {
	private LinkedList<Nachricht> liste;

	public NachrichtenManager() {
		liste = new LinkedList<Nachricht>();
	}

	// Überprüfung, ob die Nachrichten schon vorhanden sind,
 	//  und Filterung der Nachrichten auf dem Bildschirm
	public void schicken(Nachricht neu) {
		for (Nachricht n : liste) {
			if (n.gibText() == neu.gibText()) {
				return;
			}
		}
		liste.add(neu);
	}

	public void schicken(String n, Nachricht.Typ typ) { schicken(new Nachricht(n, typ)); }

	// Entfernen der Nachrichten nach einer gewissen Zeit vom Bildschirm 
	public void tick() {
		for (Nachricht n : liste) {
			if (!n.tick()) {
				liste.remove(n);
			}
		}
	}

	// Zeichnen der Nachrichten 
	public void render(Graphics gfx) {
		for (Nachricht n : liste) {
			n.render(gfx, liste.indexOf(n));
		}
	}
}