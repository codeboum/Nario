package kompositum;

public class Liste {
	private ListenElement anfang;

	public Liste() {
		anfang = new Abschluss();
	}

	public void adden(Knoten kNeu) {
		anfang = anfang.adden(kNeu);
	}
	public void adden(DatenElement dNeu) {
		adden(new Knoten(dNeu));
	}

	public DatenElement gib(int index) {
		return anfang.gib(index);
	}

	public int laenge() {
		return anfang.gibRestLaenge();
	}
	public void ausgeben() {
		anfang.ausgeben();
	}
}