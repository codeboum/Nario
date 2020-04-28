import java.util.LinkedList;
import java.awt.image.BufferedImage;


// Implementiert Animation
// SpielObjekte können Animationsobjekte haben, und diese zum Zeichnen benutzen

public class Animation {
	LinkedList<BufferedImage> bilder;
	int anzahl, index, fps, fpsIndex;   // fps gibt an, wie schnell die Animation läuft
	Vek2 groesse;   // Groesse der Bilder

	// Aus dem gegebenen Bild werden in horizontaler Richtung *anzahl* Bilder mit der gegebenen Groesse ausgeschnitten
	//  und in einer LinkedList gespeichert
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
	// Dieser Konstruktor nimmt direkt eine LinkedList mit Bildern und übernimmt diese in die Animation
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
	// Lässt die Animation voranschreiten
	public void ticken() {
		fpsIndex++;
		if (fpsIndex >= 60/fps) {
			fpsIndex = 0;
			index++;
			if (index >= anzahl) index = 0;
		}
	}

	public void reset() {
		index = 0;
		fpsIndex = 0;
	}
}