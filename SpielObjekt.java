import java.awt.Graphics;


// Prototyp für alle Objekte im Spiel - Spieler, Gegner, usw

public abstract class SpielObjekt {
	protected Vek2 pos, v;   // Position, Geschwindigkeit
	protected Typ typ;

	public SpielObjekt(Typ typ, Vek2 pos) {
		this.typ = typ;
		this.pos = pos;
		v = new Vek2();
	}
	public SpielObjekt(Typ typ, Vek2 pos, Vek2 v) {
		this.typ = typ;
		this.pos = pos;
		this.v = pos;
	}

	public abstract void tick();
	public abstract void render(Graphics gfx);

	public Typ  gibTyp() { return typ; }
	public void setzPos(Vek2 pos) { this.pos = pos; }
	public Vek2 gibPos() { return pos; }
	public void setzV(Vek2 v) { this.v = v; }
	public Vek2 gibV() { return v; }

	public enum Typ {
		Spieler,
		Gegner;
	}
}