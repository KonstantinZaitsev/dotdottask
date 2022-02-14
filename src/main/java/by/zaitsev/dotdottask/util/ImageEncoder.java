package by.zaitsev.dotdottask.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * ImageEncoder is a class used to convert a byte[] image to a String Base64 image.
 *
 * @author Konstantin Zaitsev
 */
public class ImageEncoder {
    private static final String IMAGE_TYPE = "data:image/jpg;base64,";
    private static ImageEncoder instance;

    private ImageEncoder() {
    }

    /**
     * @return a single instance of the ConnectionFactory class.
     */
    public static ImageEncoder getInstance() {
        if (instance == null) {
            instance = new ImageEncoder();
        }
        return instance;
    }

    /**
     * @param image image in byte[] format.
     * @return a String Base64 image.
     */
    public String encodeImage(byte[] image) {
        byte[] encodeBase64 = Base64.getEncoder().encode(image);
        String base64DataString = new String(encodeBase64, StandardCharsets.UTF_8);
        return IMAGE_TYPE + base64DataString;
    }
}
