import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


// Diese Klasse lädt Bilder und gibt sie als nutzbare Objekte zurück

public class BildLader {
    private BufferedImage image;

    public BufferedImage laden(String pfad) {
        try {
            image = ImageIO.read(getClass().getResource(pfad));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}