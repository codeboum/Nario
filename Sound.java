import java.io.File;
import java.nio.file.Paths;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


// Plays audio files, looping is optional

public class Sound {
	File file;
	Clip clip;

	public Sound(String filePath) {
		try {
			file = new File(Paths.get(System.getProperty("user.dir")) + filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play(boolean loop) {
		try {
			if (file.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
				clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
				if (loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			else {
				System.out.println("Audio file wasn't found. Path: " + file.getPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		clip.close();
	}
}