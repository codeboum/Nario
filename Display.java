import java.awt.Graphics;
import java.awt.Color;



// Handles Display of Game parameters - Level, Points, etc
// Inherits from Config, since it is accessed a lot - saves unnecessary typing

public class Display extends Config {
	Game game;

	public Display(Game game) {
		this.game = game;
	}

	public void render(Graphics gfx, Vec2 dim) {
		if (game.getStatus() == Game.Status.InGame) {
			double healthDouble = game.player.getHealth() * (255.0/(double) MAXHEALTH);
			int h = (int) healthDouble;
			gfx.setColor(new Color(255-h, h, h/4));
			gfx.fillRect(20, 50, game.player.getHealth()*2, 35);
			gfx.setColor(WHITE);
			gfx.drawRect(20, 50, (MAXHEALTH*2)-1, 34);

			gfx.setFont(TEXT14);
			gfx.drawString("Score", 230, 62);
			gfx.setColor(YELLOW);
			gfx.setFont(TEXT18);
			gfx.drawString(game.points+"", 230, 82);
		}

		if (!game.testModeActive()) return;


		// Debug Display - FPS, Level
		gfx.setColor(WHITE);
		gfx.setFont(TEXT14);
		gfx.drawString("FPS", 5, dim.iy()-25);
		gfx.drawString("Sound", 55, dim.iy()-25);
		gfx.drawString("Level", 105, dim.iy()-25);
		gfx.setColor(YELLOW);
		gfx.setFont(TEXT18);
		gfx.drawString(game.getFPS()+"", 5, dim.iy()-5);
		if (SOUNDON) {
			gfx.setColor(GREEN);
			gfx.drawString("ON", 55, dim.iy()-5);
		} else {
			gfx.setColor(RED);
			gfx.drawString("OFF", 55, dim.iy()-5);
		}
		gfx.setColor(YELLOW);
		gfx.drawString(game.level.getTitle(), 105, dim.iy()-5);
	}
}