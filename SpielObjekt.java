import java.awt.Graphics;


// Prototyp für alle Objekte im Spiel - Spieler, Gegner, usw

public abstract class SpielObjekt {
	protected Vek2 pos, v;   // Position, Geschwindigkeit

	public SpielObjekt(Vek2 pos) {
		this.pos = pos;
		v = new Vek2();
	}
	public SpielObjekt(Vek2 pos, Vek2 v) {
		this.pos = pos;
		this.v = pos;
	}

	public abstract void tick();
	public abstract void render(Graphics gfx);

	public void setzPos(Vek2 pos) { this.pos = pos; }
	public Vek2 gibPos() { return pos; }
	public void setzV(Vek2 v) { this.v = v; }
	public Vek2 gibV() { return v; }
}