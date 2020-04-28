import java.awt.Graphics;
import java.awt.image.BufferedImage;


// Spieler-Objekt, erbt von SpielObjekt
// Beinhaltet bereits Laufanimation (wird noch nicht benutzt)

public class Spieler extends SpielObjekt {
    private Animation animation;
    private BufferedImage bildStand;
    private String name;

    public Spieler(Vek2 bildschirm, Animation animation, BufferedImage bildStand, String name) {
        super(Typ.Spieler, new Vek2(0, bildschirm.y*0.55));
        this.animation = animation;
        this.bildStand = bildStand;
        this.name = name;
    }

    public void tick() {
        animation.ticken();
    }

    public void render(Graphics gfx) {
        gfx.setFont(Konfig.TEXT36);
        gfx.setColor(Konfig.SCHWARZ);
        gfx.drawString(name, pos.ix() + 30, pos.iy());
        gfx.drawImage(bildStand, pos.ix(), pos.iy(), null);
    }

    public void setzName(String n) {
        this.name = n;
    }
    public String gibName() { return name; }
}