import java.awt.Graphics;
import java.awt.Color;


// Notification for all kinds of messages, e.g. warnings

public class Notif {
	String message;
	Type type;
	int timer;

	public enum Type {
		Normal,
		Warning;
	}

    // In den 2 Konstruktoren werden die Nachrichtenlängen unterschiedlich festgelegt
	public Notif(String message, Type type, int seconds) {
		this.message = message;
		this.type = type;
		if (message.length() > 100) message = message.substring(0, 100);
		timer = (int) (seconds * Config.FPS);
	}
	public Notif(String message, Type type) {
		this.message = message;
		this.type = type;
		if (message.length() > 100) message = message.substring(0, 100);
		timer = (int) (5 * Config.FPS);
	}

	// Makes notification disappear
	public boolean tick() {
		timer--;
		if (timer <= 0) {
			return false;
		}
		return true;
	}

	// Renders notification on screen
	public void render(Graphics gfx, int index) {
		Color color;
		switch (type) {
			case Warning:
				color = Config.RED;
				break;
			default:
				color = Config.WHITE;
		}
		gfx.setColor(color);
		gfx.setFont(Config.TEXT14);
		gfx.drawString(message, 5, TitleBar.HEIGHT + 16 * (index+1));
	}

	public String getMessage() { return message; }
}