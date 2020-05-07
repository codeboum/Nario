package kompositum;

public class Abschluss extends ListenElement {
	public Knoten adden(Knoten kNeu) {
		kNeu.nachfolgerSetzen(this);
		return kNeu;
	}

	public DatenElement gib(int index) {
		return null;
	}

	public int gibRestLaenge() {
		return 0;
	}

	public void ausgeben() {}
}