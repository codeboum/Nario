import java.util.LinkedList;
import java.awt.image.BufferedImage;


// Implementiert Animation
// Aus dem gegebenen Bild werden in horizontaler Richtung *anzahl* Bilder mit der gegebenen Groesse ausgeschnitten
//  und in einer LinkedList gespeichert
// In animation() wird das aktuelle Bild gegeben und die Animation schreitet um ein Bild fort. Das ganze läuft in Schleife

public class Animation {
	LinkedList<BufferedImage> bilder;
	int anzahl, index, fps, fpsIndex;
	Vek2 groesse;

	public Animation(BufferedImage b, int anzahl, Vek2 groesse, int fps) {
		this.anzahl = anzahl;
		this.groesse = groesse;
		this.fps = fps;
		bilder = new LinkedList<BufferedImage>();

		try {
			for (int i = 0; i < anzahl; i++) {
				bilder.add(b.getSubimage(i*groesse.ix(), 0, groesse.ix(), groesse.iy()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		index = 0;
		fpsIndex = 0;
	}
	public Animation(LinkedList<BufferedImage> bilder, Vek2 groesse, int fps) {
		this.anzahl = bilder.size();
		this.groesse = groesse;
		this.fps = fps;

		this.bilder = bilder;
		
		index = 0;
		fpsIndex = 0;
	}


	public BufferedImage bildGeben() {
		return bilder.get(index);
	}
	public void ticken() {
		fpsIndex++;
		if (fpsIndex >= 60/fps) {
			fpsIndex = 0;
			index++;
			if (index >= anzahl) index = 0;
		}
	}
}