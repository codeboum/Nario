import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


// Verwaltet Tastaturinput. Wird zum Spiel als KeyListener hinzugefügt

public class TastenModul extends KeyAdapter {
	private Spiel spiel;

	public TastenModul(Spiel spiel) {
		this.spiel = spiel;
	}


	// Bei Tastenanschlag aufgerufen
	public void keyPressed(KeyEvent evt) {
		int taste = evt.getKeyCode();   // Taste, die betätigt wurde
		Spiel.Status status = spiel.gibStatus();

		if (spiel.debugAktiv()) System.out.println("Taste " + taste + " angeschlagen");

		switch (taste) {
			// Mit ESC wird das Spiel beendet
			case KeyEvent.VK_ESCAPE:
				spiel.beenden();
				return;
		}

		// Je nach Taste werden verschiedene Anweisungen ausgeführt
		if (status == Spiel.Status.HauptMenu) {
			if (taste == 10) {
				spiel.benutzerLogin();
				return;
			}
		} 
		else if (status == Spiel.Status.BenutzerLogin || status == Spiel.Status.AdminLogin) {
			Menu menu = spiel.gibMenu();

			// Verwaltet Textinput-Funktion
			if ((taste >= 48 && taste <= 57) ||   // Zahlen
			(taste >= 65 && taste <= 90) ||   // Buchstaben
			taste == 45) {   // Bindestrich/Unterstrich
				if (evt.isShiftDown() && taste >= 48 && taste <= 57) return;
				if (!evt.isShiftDown() && taste >= 65 && taste <= 90) taste += 32;
				if (evt.isShiftDown() && taste == 45) taste = 95;
				char c = (char) taste;
				menu.eingabe(c);
				return;
			}
			else if (taste == 8) {
				menu.entf();
				return;
			}
			else if (taste == 10) {
				menu.eingabeEnde();
				return;
			}
			else if (taste != 16) {
				spiel.nachrichten.schicken(new Nachricht("Please only enter Letters, Numbers or hyphens/underscores"));
			}
		}
	}
}