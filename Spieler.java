import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Spieler extends SpielObjekt {
    private Animation animation;
    private BufferedImage bildStand;
    private String name;

    public Spieler(Vek2 bildschirm, Animation animation, BufferedImage bildStand, String name) {
        super(Typ.Spieler, new Vek2(0, bildschirm.y*0.55));
        this.animation = animation;
        this.bildStand = bildStand;
        this.name = name;
        this.v = new Vek2(0.1, 0);
    }

    public void tick() {
        animation.ticken();
        this.pos = this.pos.add(this.v);
    }

    public void render(Graphics gfx) {
        gfx.setFont(Design.TEXT36);
        gfx.setColor(Design.SCHWARZ);
        gfx.drawString(name, pos.ix() + 30, pos.iy());
        gfx.drawImage(bildStand, pos.ix(), pos.iy(), null);
    }

    public void setzName(String n) {
        this.name = n;
    }
}