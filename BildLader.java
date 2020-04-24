import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.nio.file.Paths;
import javax.imageio.ImageIO;


// Diese Klasse lädt Bilder und gibt sie als nutzbare BufferedImage Objekte zurück

public class BildLader {
    public BufferedImage laden(String pfad) {
        try {
            return ImageIO.read(new FileInputStream(Paths.get(System.getProperty("user.dir")) + pfad));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}