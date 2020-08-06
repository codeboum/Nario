import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.nio.file.Paths;
import javax.imageio.ImageIO;



// Loads Images and returns usable BufferedImage Objects

public class ImageLoader {
    public BufferedImage load(String path) {
        try {
            return ImageIO.read(new FileInputStream(Paths.get(System.getProperty("user.dir")) + path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}