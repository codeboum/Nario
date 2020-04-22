import java.awt.Graphics;


public class Spieler extends SpielObjekt {
    private Animation animation;

    public Spieler(Vek2 bildschirm, Animation animation) {
        super(new Vek2(0, bildschirm.y*0.5));
        this.animation = animation;
    }

    public void tick() {
        animation.ticken();
        pos = pos.add(new Vek2(5, 0));
    }

    public void render(Graphics gfx) {
        gfx.drawImage(animation.bildGeben(), pos.ix(), pos.iy(), null);
    }
}