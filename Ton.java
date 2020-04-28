import java.io.File;
import java.nio.file.Paths;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


// Spielt Audiodateien ab, optional mit Endloschleife

public class Ton {
	File datei;
	Clip clip;

	public Ton(String dateiPfad) {
		try {
			datei = new File(Paths.get(System.getProperty("user.dir")) + dateiPfad);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void spielen(boolean schleife) {
		try {
			if (datei.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(datei);
				clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
				if (schleife) clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			else {
				System.out.println("Audiodatei konnte nicht gefunden werden. Pfad: " + datei.getPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stoppen() {
		clip.close();
	}
}