import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Spieler extends SpielObjekt {
    private Spiel spiel;
    private BufferedImage bild;

    public Spieler(Spiel spiel, Vek2 bildschirm, BufferedImage bild) {
        super(bildschirm.mult(0.5));
        this.spiel = spiel;
        this.bild = bild;
    }

    public void tick() {

    }

    public void render(Graphics gfx) {
        gfx.drawImage(bild, pos.ix(), pos.iy(), null);
    }
}