import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Spieler extends SpielObjekt {
    private Animation animation;
    private BufferedImage bildStand;
    private String name;

    public Spieler(Vek2 bildschirm, Animation animation, BufferedImage bildStand, String name) {
        super(Typ.Spieler, new Vek2(0, bildschirm.y*0.5));
        this.animation = animation;
        this.bildStand = bildStand;
        this.name = name;
    }

    public void tick() {
        animation.ticken();
    }

    public void render(Graphics gfx) {
        gfx.setFont(Design.TEXT36);
        gfx.setColor(Design.WEISS);
        gfx.drawString(name, pos.ix() + 30, pos.iy());
        gfx.drawImage(animation.bildGeben(), pos.ix(), pos.iy(), null);
    }

    public void setzName(String n) {
        this.name = n;
    }
}