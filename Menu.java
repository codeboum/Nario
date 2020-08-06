import java.awt.Graphics;


// Handles all Menu Screens

public class Menu extends Config {
	private Game game;
	private String currentText, cursor;
	private int cursorIndex;

	public Menu(Game game) {
		this.game = game;
		prepareTextInput();
	}

	public void render(Graphics gfx) {
		Game.Status status = game.getStatus();
		Vec2 dim = game.getDim();
		int x = dim.ix();
		int y = dim.iy();

		// Renders different Menu Screens
		gfx.setColor(WHITE);
		gfx.setFont(TEXT36);
		switch (status) {
			case Paused:
				gfx.drawString("Pause Menu", 100, 100);
				gfx.drawString("P - Resume Game", 100, 140);
				gfx.drawString("ESC - Back to Main Menu", 100, 180);
				break;
			case MainMenu:
				gfx.drawString("Main Menu", 100, 100);
				gfx.drawString("1 - Play", 100, 140);
				gfx.drawString("2 - Enter Test Mode", 100, 180);
				gfx.drawString("3 - Highscores", 100, 220);
				gfx.drawString("4 - Close Game", 100, 260);
				break;
			case Highscores:
				gfx.drawString("Highscores", 100, 100);
				gfx.drawString("1 - Placeholder", 100, 140);
				gfx.drawString("ESC - Back to Main Menu", 100, 180);
				break;
			case TestMenu:
				gfx.drawString("Test Menu", 100, 100);
				gfx.drawString("1 - Test", 100, 140);
				gfx.drawString("2 - Edit Highscores", 100, 180);
				gfx.drawString("ESC - Back to Main Menu", 100, 220);
				break;
			case NamePrompt:
				gfx.drawString("Enter a Name", x/2-250, y/2-30);
				gfx.drawRect(x/2-250, y/2-25, 500, 50);
				gfx.drawString(currentText + cursor, x/2-245, y/2+12);
				break;
			case TestLogin:
				gfx.drawString("Enter Test Mode Code", x/2-250, y/2-30);
				gfx.drawRect(x/2-250, y/2-25, 500, 50);
				gfx.drawString(currentText + cursor, x/2-245, y/2+12);
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

	// Handles Text input
	public void input(char key) {
		if (currentText.length() < MAXNAMELENGTH) {
			currentText += key;
		}
		else {
			game.notifs.send("Name should not be longer than 20 characters", Notif.Type.Warning);
		}
	}
	public void del() {
		if (currentText.length() > 0) currentText = currentText.substring(0, currentText.length()-1);
	}

	// Validates Text input and starts game, in test mode it tests for correct test code
	public void validateInput(boolean testMode) {
		if (testMode) {
			if (currentText.equals(TESTCODE)) {
				game.activateTestMode();
				game.notifs.send("Test Mode activated!", Notif.Type.Normal);
				game.testMenu();
			}
			else {
				game.notifs.send("Invalid Test Mode Code!", Notif.Type.Warning);
				game.mainMenu();
			}
		}
		else {
			if (currentText.length() == 0) {
				game.notifs.send("The Name cannot be empty!", Notif.Type.Warning);
			}
			else {
				game.inGame();
			}
		}
		prepareTextInput();
	}

	public void prepareTextInput() {
		cursor = CURSOR;
		currentText = "";
		cursorIndex = 0;
	}
}