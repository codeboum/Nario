import java.awt.Graphics;


public class Spieler extends SpielObjekt {
    private Spiel spiel;

    public Spieler(Spiel spiel, Vek2 bildschirm) {
        super(bildschirm.mult(0.5));
        this.spiel = spiel;
    }

    public void tick() {

    }

    public void render(Graphics gfx) {
        gfx.setColor(Design.ROT);
        gfx.fillRect(pos.ix(), pos.iy(), 20, 20);
    }
}