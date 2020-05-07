public class LevelDatenElement extends kompositum.DatenElement {
	String daten;
	public LevelDatenElement(String s) {
		this.daten = s;
	}

	public void ausgeben() {
		System.out.println(daten);
	}

	public String string() {
		return daten;
	}
}