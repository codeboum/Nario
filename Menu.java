import java.awt.Graphics;


// Verwaltet alle Menu-Bildschirme
// Erbt von Design, da viel darauf zugegriffen wird und man damit Design. in der Notation spart

public class Menu extends Konfig {
	private Spiel spiel;
	private String aktuellerText, cursor;
	private int cursorIndex;

	public Menu(Spiel spiel) {
		this.spiel = spiel;
		textInputVorbereiten();
	}

	public void render(Graphics gfx) {
		Spiel.Status status = spiel.gibStatus();
		Vek2 dim = spiel.gibDim();
		int x = dim.ix();
		int y = dim.iy();

		// Zeichnet die verschiedenen Menu-Bildschirme
		switch (status) {
			case Pausiert:
				gfx.setColor(WEISS);
				gfx.setFont(TEXT36);
				gfx.drawString("Pause Menu", 100, 100);
				gfx.drawString("P - Resume Game", 100, 140);
				gfx.drawString("ESC - Back to Main Menu", 100, 180);
				break;
			case HauptMenu:
				gfx.setColor(WEISS);
				gfx.setFont(TEXT36);
				gfx.drawString("Main Menu", 100, 100);
				gfx.drawString("1 - Play", 100, 140);
				gfx.drawString("2 - Enter Test Mode", 100, 180);
				gfx.drawString("3 - Highscores", 100, 220);
				gfx.drawString("4 - Close Game", 100, 260);
				break;
			case Highscore:
				gfx.setColor(WEISS);
				gfx.setFont(TEXT36);
				gfx.drawString("Highscores", 100, 100);
				gfx.drawString("1 - Placeholder", 100, 140);
				gfx.drawString("ESC - Back to Main Menu", 100, 180);
				break;
			case TestMenu:
				gfx.setColor(WEISS);
				gfx.setFont(TEXT36);
				gfx.drawString("Test Menu", 100, 100);
				gfx.drawString("1 - Test", 100, 140);
				gfx.drawString("2 - Edit Highscores", 100, 220);
				gfx.drawString("ESC - Back to Main Menu", 100, 260);
				break;
			case BenutzerLogin:
				gfx.setColor(WEISS);
				gfx.setFont(TEXT36);
				gfx.drawString("Enter a Name", x/2-250, y/2-30);
				gfx.drawRect(x/2-250, y/2-25, 500, 50);
				gfx.drawString(aktuellerText + cursor, x/2-245, y/2+12);
				break;
			case AdminLogin:
				gfx.setColor(WEISS);
				gfx.setFont(TEXT36);
				gfx.drawString("Enter Test Mode Code", x/2-250, y/2-30);
				gfx.drawRect(x/2-250, y/2-25, 500, 50);
				gfx.drawString(aktuellerText + cursor, x/2-245, y/2+12);
				break;
			default:
				break;
		}
	}

	public void cursorTick() {
		cursorIndex++;
		if (cursorIndex >= 30) {
			cursorIndex = 0;
			cursor = (cursor == "") ? CURSOR : "";
		}
	}

	// Verwaltet Texteingabe
	public void eingabe(char taste) {
		if (aktuellerText.length() < MAXNAMELAENGE) {
			aktuellerText += taste;
		}
		else {
			spiel.nachrichten.schicken("Name should not be longer than 20 characters", Nachricht.Typ.Warnung);
		}
	}
	public void entf() {
		if (aktuellerText.length() > 0) aktuellerText = aktuellerText.substring(0, aktuellerText.length()-1);
	}

	// Validiert Texteingabe und startet Spiel, falls admin true ist wird auf den admincode getestet
	public void eingabeEnde(boolean admin) {
		if (admin) {
			if (aktuellerText.equals(TESTCODE)) {
				spiel.testModusAktivieren();
				spiel.nachrichten.schicken("Test Mode activated!", Nachricht.Typ.Normal);
				spiel.testMenu();
			}
			else {
				spiel.nachrichten.schicken("Invalid Test Mode Code!", Nachricht.Typ.Warnung);
				spiel.hauptMenu();
			}
		}
		else {
			if (aktuellerText.length() == 0) {
				spiel.nachrichten.schicken("The Name cannot be empty!", Nachricht.Typ.Warnung);
			}
			else {
				spiel.spieler.setzName(aktuellerText);
				spiel.inGame();
			}
		}
		textInputVorbereiten();
	}

	public void textInputVorbereiten() {
		cursor = CURSOR;
		aktuellerText = "";
		cursorIndex = 0;
	}
}