import java.awt.Graphics;

import java.util.Scanner;


// Erbt von Design, da viel darauf zugegriffen wird und man damit Design. in der Notation spart

public class Menu extends Design {
	private Spiel spiel;
	private String aktuellerText, cursor;
	private int cursorIndex;

	private Scanner scan;

	public Menu(Spiel spiel) {
		this.spiel = spiel;
		textInputVorbereiten();
	}

	public void render(Graphics gfx) {
		Spiel.Status status = spiel.gibStatus();
		Vek2 dim = spiel.gibDim();
		int x = dim.ix();
		int y = dim.iy();

		switch (status) {
			case HauptMenu:
				gfx.setColor(WEISS);
				gfx.setFont(TEXT36);
				gfx.drawString("Main Menu - Press Enter", 100, 100);
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
				gfx.drawString("Enter Admin Code", x/2-250, y/2-30);
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

	public void eingabe(char taste) {
		if (aktuellerText.length() < MAXNAMELAENGE) {
			aktuellerText += taste;
		}
		else {
			spiel.nachrichten.schicken(new Nachricht("Name should not be longer than 20 characters"));
		}
	}
	public void entf() {
		if (aktuellerText.length() > 0) aktuellerText = aktuellerText.substring(0, aktuellerText.length()-1);
	}

	public void eingabeEnde() {
		spiel.spielerNameSetzen(aktuellerText);
		spiel.inGame();
	}

	public void textInputVorbereiten() {
		cursor = CURSOR;
		aktuellerText = "";
		cursorIndex = 0;
	}
}