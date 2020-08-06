import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


// Handles Mouse input. Added to Game as Mouse Listener

public class MouseModule extends MouseAdapter {
	private Game game;

	public MouseModule(Game game) {
		this.game = game;
	}

	public void mousePressed(MouseEvent evt) {
		Vec2 mpos = new Vec2(evt.getPoint());   // Mouse Position on Canvas
		Vec2 dim = game.getDim();   // Dimension of Canvas
		int btn = evt.getButton();   // Pressed Button

		if (game.testModeActive()) System.out.println("Mouse button " + btn + " pressed");

		// Left Click
		if (btn == MouseEvent.BUTTON1) {
			// Schliess-Button (Leiste)
			if (mpos.intersects(new Vec2(dim.ix()-40, 0), new Vec2(40, 30))) {
				game.end();
				return;
			}
		}
		/*
		else if (btn == MouseEvent.BUTTON3) {
			// Right Click
		}
		*/
	}
}