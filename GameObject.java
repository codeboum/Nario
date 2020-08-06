import java.awt.Graphics;


// Prototype for all Objects in the Game

public abstract class GameObject {
	protected Vec2 dim;   // Dimension of object
	protected Vec2 pos, v, a;   // Position, Velocity, Acceleration
	protected Type type;

	public GameObject(Type type, Vec2 dim, Vec2 pos) {
		this.type = type;
		this.dim = dim;
		this.pos = pos;
		v = new Vec2();
		a = new Vec2();
	}
	public GameObject(Type type, Vec2 dim, Vec2 pos, Vec2 v) {
		this.type = type;
		this.dim = dim;
		this.pos = pos;
		this.v = pos;
		a = new Vec2();
	}
	public GameObject(Type type, Vec2 dim, Vec2 pos, Vec2 v, Vec2 a) {
		this.type = type;
		this.dim = dim;
		this.pos = pos;
		this.v = v;
		this.a = a;
	}

	public abstract void tick();
	public abstract void render(Graphics gfx);

	public Type getType() { return type; }
	public Vec2 getDim() { return dim; }
	public void setPos(Vec2 pos) { this.pos = pos; }
	public Vec2 getPos() { return pos; }
	public void setV(Vec2 v) { this.v = v; }
	public Vec2 getV() { return v; }
	public void setA(Vec2 a) { this.a = a; }
	public Vec2 getA() { return a; }

	public enum Type {
		Player,
		Enemy;
	}
}