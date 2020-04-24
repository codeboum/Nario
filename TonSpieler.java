import java.io.File;
import java.nio.file.Paths;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


// Spielt Audiodateien ab, optional mit Endloschleife

public class TonSpieler {
	public void spielen(String dateiPfad, boolean schleife) {
		try {
			File datei = new File(Paths.get(System.getProperty("user.dir")) + dateiPfad);

			if (datei.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(datei);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
				if (schleife) clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			else {
				System.out.println("Audiodatei konnte nicht gefunden werden. Pfad: " + dateiPfad);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}