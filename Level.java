import java.awt.Graphics;
import java.util.LinkedList;


// Verwaltet alle SpielObjekte
// Beinhaltet LinkedList welche alle Objekte enthält
// Die Hauptaufgabe der Klasse ist, auf allen SpielObjekten tick() und render() aufzurufen

public class Level {
	String titel;
	LinkedList<SpielObjekt> objekte;   // Beinhaltet SpielObjekte

	// temporär
	double boden = 790.0;

	public Level(String daten) {
		titel = "PLACEHOLDER";
		objekte = new LinkedList<SpielObjekt>();
	}

	// Objekte einfügen
	public void adden(SpielObjekt obj) {
		objekte.add(obj);
	}
	// Objekte entfernen
	public void entfernen(SpielObjekt obj) { objekte.remove(obj); }
	// Entfernt alle Objekte, Hilfreich um das Spiel zurückzusetzen, zB zwischen Level
	public void leeren() {
		objekte.clear();
	}
	// Entfernt alle Objekte ausser eins, hilfreich um zB alle ausser den Spiel zu entfernen
	public void leerenAusser(SpielObjekt o) {
		for (SpielObjekt obj : objekte) {
			if (obj != o) entfernen(obj);
		}
	}

	// Ruft tick() auf allen Objekten auf
	public void tick(Spieler spieler) {
		for (SpielObjekt obj : objekte) {
			obj.tick();
		}

		Vek2 spos = spieler.gibPos();
		Vek2 sdim = spieler.gibDim();
		if (spos.y + sdim.y > boden) {
			Spieler.Status s = spieler.gibStatus();
			if (s == Spieler.Status.StandSprung) spieler.stand();
			else if (s == Spieler.Status.LaufSprung) spieler.stand(); spieler.lauf();
			spieler.setzPos(new Vek2(sdim.x, boden-sdim.y));
			spieler.setzV(new Vek2(spieler.gibV().x, 0));
		}
	}
	// Ruft render() auf allen Objekten auf
	public void render(Graphics gfx) {
		for (SpielObjekt obj : objekte) {
			obj.render(gfx);
		}
	}

	public String gibTitel() { return titel; }
}