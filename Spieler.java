import java.awt.Graphics;
import java.awt.image.BufferedImage;


// Spieler-Objekt, erbt von SpielObjekt
// Beinhaltet bereits Laufanimation (wird noch nicht benutzt)

public class Spieler extends SpielObjekt {
    private Status status;
    private Animation animation;
    private BufferedImage bildStand, bildStandSprung, bildLaufSprung;
    private String name;


    public Spieler(Vek2 bildschirm, Animation animation, BufferedImage bildStand, BufferedImage bildStandSprung, BufferedImage bildLaufSprung, String name) {
        super(Typ.Spieler, new Vek2(160.0, 224.0), new Vek2(0.0, bildschirm.y*0.55), new Vek2(), new Vek2(0.0, 1.0));
        status = Status.Stand;
        this.animation = animation;
        this.bildStand = bildStand;
        this.bildStandSprung = bildStandSprung;
        this.bildLaufSprung = bildLaufSprung;
        this.name = name;
    }

    public enum Status {
        Stand,
        StandSprung,
        Lauf,
        LaufSprung;
    }
    public void stand() {
        status = Status.Stand;
    }
    public void lauf() {
        if (status != Status.Stand) return;
        animation.reset();
        status = Status.Lauf;
    }
    public void sprung() {
        if (status == Status.StandSprung || status == Status.LaufSprung) return;
        if (status == Status.Stand) status = Status.StandSprung;
        else if (status == Status.Lauf) status = Status.LaufSprung;
        v = new Vek2(v.x, v.y -= 25.0);

    }

    public void tick() {
        v = v.add(a);
        pos = pos.add(v);

        if (status == Status.Lauf) {
            animation.ticken();
        }
    }

    public void render(Graphics gfx) {
        gfx.setFont(Konfig.TEXT36);
        gfx.setColor(Konfig.SCHWARZ);
        gfx.drawString(name, pos.ix() + 30, pos.iy());
        switch (status) {
            case Stand:
                gfx.drawImage(bildStand, pos.ix(), pos.iy(), null);
                break;
            case StandSprung:
                gfx.drawImage(bildStandSprung, pos.ix(), pos.iy(), null);
                break;
            case Lauf:
                gfx.drawImage(animation.bildGeben(), pos.ix(), pos.iy(), null);
                break;
            case LaufSprung:
                gfx.drawImage(bildLaufSprung, pos.ix(), pos.iy(), null);
        }
    }

    public void setzName(String n) { this.name = n; }
    public String gibName() { return name; }
    public Status gibStatus() { return status; }
}