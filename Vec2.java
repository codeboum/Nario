import java.awt.Point;
import java.awt.Dimension;


// Defines 2dimensional vectors
// Easier for physics than dimensions or points
// Compatibility with dimension or points is implemented to make things easier

public class Vec2 {
	public double x, y;

	public Vec2()                   { x = y = 0.0; }
	public Vec2(double x, double y) { this.x = x; this.y = y; }
	public Vec2(Point p)            { this.x = p.getX(); this.y = p.getY(); }
	public Vec2(Dimension d)        { this.x = d.getWidth(); this.y = d.getHeight(); }

	public int ix()                 { return (int) x; }
	public int iy()                 { return (int) y; }
	public Point point()            { return new Point(ix(), iy()); }
	public Dimension dimension()    { return new Dimension(ix(), iy()); }

	public Vec2 add(Vec2 v)         { return new Vec2(x+v.x, y+v.y); }
	public Vec2 sub(Vec2 v)         { return new Vec2(x-v.x, y-v.y); }
	public Vec2 mult(Vec2 v)        { return new Vec2(x*v.x, y*v.y); }
	public Vec2 mult(double n)      { return new Vec2(x*n, y*n); }

	public double gibLaenge()       { return Math.sqrt(gibLaenge2()); }
	public double gibLaenge2()      { return x*x + y*y; }

	public Vec2 gibNorm() {
		double l = gibLaenge();
		if (l > 0.0) {
			return this.mult(1.0/l);
		}
		return this;
	}

	// Tests if a point defined by Vec2 intersects a rectangle, useful e.g. for the mouse
	// pos - Position of the rectangle (upper left corner)
	// dim - Dimension of the rectangle
	public boolean intersects(Vec2 pos, Vec2 dim) {
		return x >= pos.x && x < pos.x + dim.x && y >= pos.y && y < pos.y + dim.y;
	}
}