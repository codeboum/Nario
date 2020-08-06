import java.awt.Graphics;


// Implements Title Bar

public class TitleBar {
	public static final int HEIGHT = 30;
	Game game;

	public TitleBar(Game game) {
		this.game = game;
	}

	// Renders Titlebar with appropriate parameters
	public void render(Graphics gfx) {
		Vec2 dim = game.getDim();

		gfx.setColor(Config.TITLE_BAR_COLOR);
		gfx.fillRect(0, 0, dim.ix(), HEIGHT);

		gfx.setFont(Config.TEXT_TITLE_FONT);
		gfx.setColor(Config.WHITE);
		String text = Config.TITLE;
		text += (game.testModeActive()) ? " - TEST MODE" : "";
		gfx.drawString(text, 5, 22);

		gfx.setColor(Config.RED);
		gfx.fillRect(dim.ix()-40, 0, 40, 30);
		gfx.setColor(Config.WHITE);
		gfx.drawLine(dim.ix()-28, 7, dim.ix()-13, 22);
		gfx.drawLine(dim.ix()-28, 22, dim.ix()-13, 7);

		gfx.setColor(Config.WHITE);
		gfx.drawLine(0, 29, dim.ix()-1, 29);
	}
}