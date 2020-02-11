import java.awt.Graphics;


public class Spieler extends SpielObjekt {
    private Animation animation;

    public Spieler(Vek2 bildschirm, Animation animation) {
        super(bildschirm.mult(0.5));
        this.animation = animation;
    }

    public void tick() {

    }

    public void render(Graphics gfx) {
        gfx.drawImage(animation.animieren(), pos.ix(), pos.iy(), null);
    }
}