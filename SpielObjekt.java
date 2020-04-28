import java.awt.Graphics;


// Prototyp für alle Objekte im Spiel - Spieler, Gegner, usw

public abstract class SpielObjekt {
	protected Vek2 dim;   // Dimension des Objekts
	protected Vek2 pos, v, a;   // Position, Geschwindigkeit, Beschleunigung
	protected Typ typ;

	public SpielObjekt(Typ typ, Vek2 dim, Vek2 pos) {
		this.typ = typ;
		this.dim = dim;
		this.pos = pos;
		v = new Vek2();
		a = new Vek2();
	}
	public SpielObjekt(Typ typ, Vek2 dim, Vek2 pos, Vek2 v) {
		this.typ = typ;
		this.dim = dim;
		this.pos = pos;
		this.v = pos;
		a = new Vek2();
	}
	public SpielObjekt(Typ typ, Vek2 dim, Vek2 pos, Vek2 v, Vek2 a) {
		this.typ = typ;
		this.dim = dim;
		this.pos = pos;
		this.v = v;
		this.a = a;
	}

	public abstract void tick();
	public abstract void render(Graphics gfx);

	public Typ  gibTyp() { return typ; }
	public Vek2 gibDim() { return dim; }
	public void setzPos(Vek2 pos) { this.pos = pos; }
	public Vek2 gibPos() { return pos; }
	public void setzV(Vek2 v) { this.v = v; }
	public Vek2 gibV() { return v; }
	public void setzA(Vek2 a) { this.a = a; }
	public Vek2 gibA() { return a; }

	public enum Typ {
		Spieler,
		Gegner;
	}
}