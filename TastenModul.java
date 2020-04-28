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
		boolean admin = spiel.adminModusAktiv();

		if (admin) System.out.println("Taste " + taste + " angeschlagen");

		// Je nach Taste werden verschiedene Anweisungen ausgeführt
		if (taste == 83) {
			if (Design.SOUNDAN) {
				spiel.soundAus();
				spiel.nachrichten.schicken("Sound is now off", Nachricht.Typ.Normal);
			}
			else {
				spiel.soundAn();
				spiel.nachrichten.schicken("Sound is now on", Nachricht.Typ.Normal);
			}
		}
		if (status == Spiel.Status.InGame || status == Spiel.Status.Pausiert) {
			switch (taste) {
				case KeyEvent.VK_ESCAPE:
					if (!admin) {
						spiel.hauptMenu();
					}
					else {
						spiel.adminMenu();
					}
					return;
				case 80:   // P
					if (status == Spiel.Status.InGame) spiel.pausiert();
					else spiel.inGame();
			}
		}
		if (status == Spiel.Status.HauptMenu) {
			switch (taste) {
				case 49:
					spiel.benutzerLogin();
					break;
				case 50:
					spiel.adminLogin();
					break;
				case 51:
				case KeyEvent.VK_ESCAPE:
					spiel.beenden();
					return;
			}
		}
		else if (status == Spiel.Status.AdminMenu) {
			switch (taste) {
				case 49:
					spiel.inGame();
					break;
				case 50:
					spiel.adminModusBeenden();
					spiel.nachrichten.schicken("Logged out of Admin Mode", Nachricht.Typ.Normal);
					spiel.hauptMenu();
					break;
				case 51:
					spiel.nachrichten.schicken("Not implemented", Nachricht.Typ.Normal);
					break;
				case 52:
				case KeyEvent.VK_ESCAPE:
					spiel.beenden();
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
				menu.eingabeEnde(status == Spiel.Status.AdminLogin);
				return;
			}
			else if (taste != 16) {
				spiel.nachrichten.schicken("Please only enter Letters, Numbers or hyphens/underscores", Nachricht.Typ.Warnung);
			}
		}
	}
}