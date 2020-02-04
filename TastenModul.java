import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


// Verwaltet Tastaturinput. Wird zum SPiel als KeyListener hinzugefügt

public class TastenModul extends KeyAdapter {
	private Spiel spiel;

	public TastenModul(Spiel spiel) {
		this.spiel = spiel;
	}


	// Bei Tastenanschlag aufgerufen
	public void keyPressed(KeyEvent evt) {
		int taste = evt.getKeyCode();   // Taste, die betätigt wurde

		// Je nach Taste werden verschiedene Anweisungen ausgeführt
		switch (taste) {
			// Mit ESC wird das Spiel beendet
			case KeyEvent.VK_ESCAPE:
				spiel.beenden();
				break;
		}
	}
}