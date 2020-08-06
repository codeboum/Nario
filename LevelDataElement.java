public class LevelDataElement extends kompositum.DatenElement {
	String data;
	public LevelDataElement(String s) {
		this.data = s;
	}

	public void ausgeben() {
		System.out.println(data);
	}

	public String string() {
		return data;
	}
}