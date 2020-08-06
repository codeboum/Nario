import java.awt.Graphics;
import java.util.LinkedList;


// Handles display and dissappearance of notifications onscreen

public class NotifHandler {
	private LinkedList<Notif> stack;

	public NotifHandler() {
		stack = new LinkedList<Notif>();
	}

	// Adds new notification to stack
	//  if there is no existing one with the same message
	public void send(Notif newNotif) {
		for (Notif n : stack) {
			if (n.getMessage() == newNotif.getMessage()) {
				return;
			}
		}
		stack.add(newNotif);
	}

	public void send(String message, Notif.Type type) { send(new Notif(message, type)); }

	// Makes message dissappear after period of time
	public void tick() {
		for (Notif n : stack) {
			if (!n.tick()) {
				stack.remove(n);
			}
		}
	}

	// Renders notifications
	public void render(Graphics gfx) {
		for (Notif n : stack) {
			n.render(gfx, stack.indexOf(n));
		}
	}
}