import java.awt.Graphics;
import java.util.LinkedList;


public class NachrichtenManager {
	private LinkedList<Nachricht> nachrichten;

	public NachrichtenManager() {
		nachrichten = new LinkedList<Nachricht>();
	}

	public void schicken(Nachricht n) {
		nachrichten.add(n);
	}

	public void tick() {
		for (Nachricht n : nachrichten) {
			if (!n.tick()) {
				nachrichten.remove(n);
			}
		}
	}

	public void render(Graphics gfx) {
		for (Nachricht n : nachrichten) {
			n.render(gfx);
		}
	}
}