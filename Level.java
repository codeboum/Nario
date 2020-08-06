import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import kompositum.*;


// Handles current Level with all GameObjects
// GameObjects are contained within a LinkedList

public class Level {
	String title;
	Vec2 screenDim;
	BufferedImage spriteStone;
	LinkedList<GameObject> objects;   // Handles GameObjects

	int groundData;
	double groundActual;

	public Level(String data, Vec2 screenDim, BufferedImage spriteStone) {
		Liste levelData = convertLevelData(data);
		this.screenDim = screenDim;
		this.spriteStone = spriteStone;
		System.out.println("Level Data loaded");
		
		title = levelData.gib(0).string().substring(8);
		String groundStr = levelData.gib(13).string().substring(7);
		try {
			groundData = Integer.parseInt(groundStr);
			groundActual = screenDim.iy() - (groundData * Config.TILE_SIZE);
		} catch (Exception e) { e.printStackTrace(); };
		objects = new LinkedList<GameObject>();
	}

	// add object
	public void add(GameObject obj) {
		objects.add(obj);
	}
	// remove object
	public void remove(GameObject obj) { objects.remove(obj); }
	// clears all objects
	public void clearObjects() {
		objects.clear();
	}
	// clears all objects except one
	public void clearObjectsExcept(GameObject o) {
		for (GameObject obj : objects) {
			if (obj != o) remove(obj);
		}
	}

	// Calls tick() on all objects
	public void tick(Player player) {
		for (GameObject obj : objects) {
			obj.tick();
		}

		Vec2 ppos = player.getPos();
		Vec2 pdim = player.getDim();
		if (ppos.y + pdim.y > groundActual) {
			Player.Status s = player.getStatus();
			if (s == Player.Status.StandingJump) {
				player.standing();
			}
			else if (s == Player.Status.MovingJump) {
				player.standing();
				player.moving();
			}
			player.setPos(new Vec2(ppos.x, groundActual-pdim.y));
			player.setV(new Vec2(player.getV().x, 0));
		}
	}

	// Calls render() on all objects
	public void render(Graphics gfx) {
		for (int j = 0; j < groundData; j++) {
			for (int i = 0; i < screenDim.ix(); i++) {
				gfx.drawImage(spriteStone, i*Config.TILE_SIZE, screenDim.iy()-(j+1)*Config.TILE_SIZE, null);
			}
		}
		for (GameObject obj : objects) {
			obj.render(gfx);
		}
	}

	public String getTitle() { return title; }

	private Liste convertLevelData(String data) {
		Liste list = new Liste();

		boolean done = false;
		do {
			int lineEnd = data.indexOf("\n");
			if (lineEnd == -1) {
				list.adden(new LevelDataElement(data));
				done = true;
			}
			else {
				list.adden(new LevelDataElement(data.substring(0, lineEnd)));
				data = data.substring(lineEnd+1);
			}
		} while (!done);

		return list;
	}
}