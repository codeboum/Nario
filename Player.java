import java.awt.Graphics;
import java.awt.image.BufferedImage;


// GameObject
// Already features Running animation, unused atm

public class Player extends GameObject {
    private Status status;
    private Animation runAnim;
    private BufferedImage spriteStanding, spriteStandingJump, spriteMovingJump;
    private String name;
    private int health;


    public Player(Vec2 screenDim, Animation runAnim, BufferedImage spriteStanding, BufferedImage spriteStandingJump, BufferedImage spriteMovingJump, String name) {
        super(Type.Player, new Vec2(160.0, 224.0), new Vec2(0.0, screenDim.y*0.55), new Vec2(), new Vec2(0.0, 1.0));
        status = Status.Standing;
        this.runAnim = runAnim;
        this.spriteStanding = spriteStanding;
        this.spriteStandingJump = spriteStandingJump;
        this.spriteMovingJump = spriteMovingJump;
        this.name = name;
        health = Config.MAXHEALTH;
    }

    public enum Status {
        Standing,
        StandingJump,
        Moving,
        MovingJump;
    }
    public void standing() {
        status = Status.Standing;
    }
    public void moving() {
        if (status != Status.Standing) return;
        runAnim.reset();
        status = Status.Moving;
    }
    public void jump() {
        if (status == Status.StandingJump || status == Status.MovingJump) return;
        if (status == Status.Standing) status = Status.StandingJump;
        else if (status == Status.Moving) status = Status.MovingJump;
        v = new Vec2(v.x, v.y -= 25.0);
    }

    public void setHealth(int h) {
        if (h < 0 || h > Config.MAXHEALTH) return;
        health = h;
    }
    public void addHealth(int i) {
        if (health+i < 0 || health+i > Config.MAXHEALTH) return;
        health += i;
    }

    public void tick() {
        v = v.add(a);
        pos = pos.add(v);

        if (status == Status.Moving) {
            runAnim.tick();
        }
    }

    public void render(Graphics gfx) {
        gfx.setFont(Config.TEXT36);
        gfx.setColor(Config.BLACK);
        gfx.drawString(name, pos.ix() + 30, pos.iy());
        switch (status) {
            case Standing:
                gfx.drawImage(spriteStanding, pos.ix(), pos.iy(), null);
                break;
            case StandingJump:
                gfx.drawImage(spriteStandingJump, pos.ix(), pos.iy(), null);
                break;
            case Moving:
                gfx.drawImage(runAnim.getCurrentFrame(), pos.ix(), pos.iy(), null);
                break;
            case MovingJump:
                gfx.drawImage(spriteMovingJump, pos.ix(), pos.iy(), null);
        }
    }
    
    public String getName() { return name; }
    public Status getStatus() { return status; }
    public int getHealth() { return health; }
}