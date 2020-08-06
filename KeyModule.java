import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


// Handles Keyboard input. Added to Game as KeyListener

public class KeyModule extends KeyAdapter {
	private Game game;

	public KeyModule(Game game) {
		this.game = game;
	}


	public void keyPressed(KeyEvent evt) {
		int key = evt.getKeyCode();   // Key that was pressed
		Game.Status status = game.getStatus();
		boolean testMode = game.testModeActive();

		if (testMode) System.out.println("Taste " + key + " angeschlagen");

		// Je nach Taste werden verschiedene Anweisungen ausgeführt
		if (key == 83 && status != Game.Status.NamePrompt && status != Game.Status.TestLogin) {
			if (Config.SOUNDON) {
				game.soundOff();
				game.notifs.send("Sound is now off", Notif.Type.Normal);
			}
			else {
				game.soundOn();
				game.notifs.send("Sound is now on", Notif.Type.Normal);
			}
			return;
		}
		if (status == Game.Status.InGame || status == Game.Status.Paused) {
			switch (key) {
				case KeyEvent.VK_ESCAPE:
					if (!testMode) {
						game.mainMenu();
					}
					else {
						game.testMenu();
					}
					return;
				case 80:   // P
					if (status == Game.Status.InGame) game.paused();
					else game.inGame();
					return;
			}
			if (status == Game.Status.InGame) {
				switch (key) {
					case KeyEvent.VK_SPACE:
					game.player.jump();
						return;
				}
			}
		}
		if (status == Game.Status.MainMenu) {
			switch (key) {
				case 49:
					game.inGame();
					return;
				case 50:
					game.testLogin();
					return;
				case 51:
					game.highscores();
					return;
				case 52:
				case KeyEvent.VK_ESCAPE:
					game.end();
					return;
			}
		}
		else if (status == Game.Status.Highscores) {
			switch (key) {
				case 49:
					// placeholder
					return;
				case KeyEvent.VK_ESCAPE:
					game.mainMenu();
					return;
			}
		}
		else if (status == Game.Status.TestMenu) {
			switch (key) {
				case 49:
					game.inGame();
					return;
				case 50:
					game.notifs.send("Not implemented", Notif.Type.Normal);
					return;
				case KeyEvent.VK_ESCAPE:
					game.endTestMode();
					game.notifs.send("Logged out of Test Mode", Notif.Type.Normal);
					game.mainMenu();
					return;
			}
		}
		else if (status == Game.Status.NamePrompt || status == Game.Status.TestLogin) {
			Menu menu = game.getMenu();

			// Verwaltet Textinput-Funktion
			if ((key >= 48 && key <= 57) ||   // Zahlen
			(key >= 65 && key <= 90) ||   // Buchstaben
			key == 45) {   // Bindestrich/Unterstrich
				if (evt.isShiftDown() && key >= 48 && key <= 57) return;
				if (!evt.isShiftDown() && key >= 65 && key <= 90) key += 32;
				if (evt.isShiftDown() && key == 45) key = 95;
				char c = (char) key;
				menu.input(c);
				return;
			}
			else if (key == 8) {
				menu.del();
				return;
			}
			else if (key == 10) {
				menu.validateInput(status == Game.Status.TestLogin);
				return;
			}
			else if (key != 16) {
				game.notifs.send("Please only enter Letters, Numbers or hyphens/underscores", Notif.Type.Warning);
				return;
			}
		}
	}
}