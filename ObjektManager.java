import java.awt.Graphics;
import java.util.LinkedList;


// Verwaltet alle SpielObjekte
// Beinhaltet LinkedList welche alle Objekte enthält
// Die Hauptaufgabe der Klasse ist, auf allen SpielObjekten tick() und render() aufzurufen

public class ObjektManager {
	LinkedList<SpielObjekt> objekte;   // Beinhaltet SpielObjekte
	boolean habenSpieler;

	public ObjektManager() {
		objekte = new LinkedList<SpielObjekt>();
		habenSpieler = false;
	}

	// Objekte einfügen
	public void adden(SpielObjekt obj) {
		if (obj.gibTyp() == SpielObjekt.Typ.Spieler) {
			if (!habenSpieler) {
				objekte.add(obj);
				habenSpieler = true;
			}
		}
		objekte.add(obj);
	}
	// Objekte entfernen
	public void entfernen(SpielObjekt obj) { objekte.remove(obj); }
	// Entfernt alle Objekte, Hilfreich um das Spiel zurückzusetzen, zB zwischen Level
	public void leeren() {
		objekte.clear();
		habenSpieler = false;
	}
	// Entfernt alle Objekte ausser eins, hilfreich um zB alle ausser den Spiel zu entfernen
	public void leerenAusser(SpielObjekt o) {
		for (SpielObjekt obj : objekte) {
			if (obj != o) entfernen(obj);
		}
	}

	// Ruft tick() auf allen Objekten auf
	public void tick() {
		for (SpielObjekt obj : objekte) {
			obj.tick();
		}
	}
	// Ruft render() auf allen Objekten auf
	public void render(Graphics gfx) {
		for (SpielObjekt obj : objekte) {
			obj.render(gfx);
		}
	}
	// Sucht ein bestimmtes Objekt in der Liste
	public SpielObjekt suchen(SpielObjekt.Typ typ) {
		for (SpielObjekt obj : objekte) {
			if (obj.gibTyp() == typ) {
				return obj;
			}
		}
		return null;
	}
}