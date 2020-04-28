import java.nio.file.*;


// Verwaltet laden und speichern von Textdateien
// Ver- und entschlüsselt Dateien mit dem Blowfish-Algorhythmus
// Bevor Dateien gespeichert werden, werden sie mit Base64 kodiert
//  um wieder richtig geladen werden zu können
// Bisher nur mit .txt Format getestet

public class DateiModul {
	private String hauptPfad;   // Der Ordner, in dem alle Dateien gespeichert werden

	public DateiModul(Path hauptPfad) {
		this.hauptPfad = hauptPfad.toString() + "\\saves\\";
	}

	public boolean speichern(String pfad, String daten) {
		try {
			Files.writeString(Paths.get(hauptPfad + pfad), daten);
			return true;
		} catch (Exception e) { e.printStackTrace(); }
		return false;
	}

	public String laden(String pfad) {
		try {
			if (!Files.exists(Paths.get(hauptPfad + pfad))) {
				Files.writeString(Paths.get(hauptPfad + pfad), "");
				return "";
			}
			String daten = Files.readString(Paths.get(hauptPfad + pfad));
			return daten;
		} catch (Exception e) { e.printStackTrace(); }
		return "";
	}
}