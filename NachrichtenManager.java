import java.awt.Graphics;
import java.util.LinkedList;


public class NachrichtenManager {
	private LinkedList<Nachricht> liste;

	public NachrichtenManager() {
		liste = new LinkedList<Nachricht>();
	}

	public void schicken(Nachricht neu) {
		for (Nachricht n : liste) {
			if (n.gibText() == neu.gibText()) {
				return;
			}
		}
		liste.add(neu);
	}

	public void tick() {
		for (Nachricht n : liste) {
			if (!n.tick()) {
				liste.remove(n);
			}
		}
	}

	public void render(Graphics gfx) {
		for (Nachricht n : liste) {
			n.render(gfx, liste.indexOf(n));
		}
	}
}