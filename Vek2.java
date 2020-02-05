import java.awt.Point;
import java.awt.Dimension;


// Definiert zweidimensionale Punkte auf der Zeichenfläche und oder andere Daten wie Bewegungsvektoren
// Für Physikberechnungen usw einfacher als Dimensions oder Points
// Kompatibilität mit Dimensions und Points ist implementiert um die Vernetzung mit Java-Klassen zu vereinfachen

public class Vek2 {
	public double x, y;

	public Vek2()                   { x = y = 0.0; }
	public Vek2(double x, double y) { this.x = x; this.y = y; }
	public Vek2(Point p)            { this.x = p.getX(); this.y = p.getY(); }
	public Vek2(Dimension d)        { this.x = d.getWidth(); this.y = d.getHeight(); }

	public int ix()                 { return (int) x; }
	public int iy()                 { return (int) y; }
	public Point point()            { return new Point(ix(), iy()); }
	public Dimension dimension()    { return new Dimension(ix(), iy()); }

	public Vek2 add(Vek2 v)         { return new Vek2(x+v.x, y+v.y); }
	public Vek2 sub(Vek2 v)         { return new Vek2(x-v.x, y-v.y); }
	public Vek2 mult(Vek2 v)        { return new Vek2(x*v.x, y*v.y); }
	public Vek2 mult(double n)      { return new Vek2(x*n, y*n); }

	public double gibLaenge()       { return Math.sqrt(gibLaenge2()); }
	public double gibLaenge2()      { return x*x + y*y; }

	public Vek2 gibNorm() {
		double l = gibLaenge();
		if (l > 0.0) {
			return this.mult(1.0/l);
		}
		return this;
	}
}