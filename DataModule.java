import java.nio.file.*;


// Handles loading and saving of text files
// Encrypts and decrypts data using the Blowfish algorythm
// Before data is saved, it is coded using Base64
//  to allow it to be loaded again correctly
// Only tested with .txt format so far

public class DataModule {
	private String mainPath;   // Der Ordner, in dem alle Dateien gespeichert werden

	public DataModule(Path mainPath) {
		this.mainPath = mainPath.toString();
	}

	public boolean save(String path, String data) {
		try {
			Files.writeString(Paths.get(mainPath + path), data);
			return true;
		} catch (Exception e) { e.printStackTrace(); }
		return false;
	}

	public String load(String path) {
		try {
			if (!Files.exists(Paths.get(mainPath + path))) {
				Files.writeString(Paths.get(mainPath + path), "");
				return "";
			}
			String data = Files.readString(Paths.get(mainPath + path));
			return data;
		} catch (Exception e) { e.printStackTrace(); }
		return "";
	}
}