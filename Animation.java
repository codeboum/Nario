import java.util.LinkedList;
import java.awt.image.BufferedImage;


// Implements animation
// GameObjects can have Animation Objects and use them for rendering

public class Animation {
	LinkedList<BufferedImage> frames;
	int nOfFrames, index, fps, fpsIndex;   // fps is the fps of the animation
	Vec2 size;   // size of frames

	// *nOfFrames* Frames of the given size are cut out of the given image in horizontal direction
	//  and saved in a LinkedList
	public Animation(BufferedImage b, int nOfFrames, Vec2 size, int fps) {
		this.nOfFrames = nOfFrames;
		this.size = size;
		this.fps = fps;
		frames = new LinkedList<BufferedImage>();

		try {
			for (int i = 0; i < nOfFrames; i++) {
				frames.add(b.getSubimage(i*size.ix(), 0, size.ix(), size.iy()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		index = 0;
		fpsIndex = 0;
	}

	// This constructor directly takes a LinkedList of Frames
	public Animation(LinkedList<BufferedImage> frames, Vec2 size, int fps) {
		this.nOfFrames = frames.size();
		this.size = size;
		this.fps = fps;

		this.frames = frames;
		
		index = 0;
		fpsIndex = 0;
	}


	public BufferedImage getCurrentFrame() {
		return frames.get(index);
	}
	// Ticks the animation
	public void tick() {
		fpsIndex++;
		if (fpsIndex >= 60/fps) {
			fpsIndex = 0;
			index++;
			if (index >= nOfFrames) index = 0;
		}
	}

	public void reset() {
		index = 0;
		fpsIndex = 0;
	}
}