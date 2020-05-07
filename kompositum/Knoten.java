package kompositum;

public class Knoten extends ListenElement {
	DatenElement inhalt;
	ListenElement nachfolger;

	public Knoten(DatenElement d) {
		inhalt = d;
		nachfolger = new Abschluss();
	}

	public Knoten adden(Knoten kNeu) {
		nachfolger = nachfolger.adden(kNeu);
		return this;
	}

	public DatenElement gib(int index) {
		if (index == 0) {
			return inhalt;
		}
		else {
			return nachfolger.gib(index-1);
		}
	}

	public int gibRestLaenge() {
		return nachfolger.gibRestLaenge() + 1;
	}

	public void ausgeben() {
		inhalt.ausgeben();
		nachfolger.ausgeben();
	}

	public void nachfolgerSetzen(ListenElement le) {
		nachfolger = le;
	}
}