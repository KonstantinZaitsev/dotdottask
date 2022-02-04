package by.zaitsev.dotdottask.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import by.zaitsev.dotdottask.model.dao.BaseDao;

/**
 * DefaultImageProvider is a class used to get default entity images for the database. Used in Dao classes.
 *
 * @author Konstantin Zaitsev
 * @see BaseDao
 */
public class DefaultImageProvider {
    private static final Logger logger = LogManager.getLogger(DefaultImageProvider.class);
    private static final String DEFAULT_USER_IMAGE_PATH = "/image/default-user.jpg";
    private final byte[] defaultUserImage;
    private static DefaultImageProvider instance;

    private DefaultImageProvider() {
        InputStream resourceStream = getClass().getResourceAsStream(DEFAULT_USER_IMAGE_PATH);
        assert resourceStream != null;
        try {
            defaultUserImage = resourceStream.readAllBytes();
        } catch (IOException e) {
            logger.log(Level.FATAL, "IOException while reading bytes from resource stream: {}", e.getMessage());
            throw new RuntimeException("IOException while reading bytes from resource stream: ", e);
        }
    }

    /**
     * @return a single instance of the DefaultImageProvider class.
     */
    public static DefaultImageProvider getInstance() {
        if (instance == null) {
            instance = new DefaultImageProvider();
        }
        return instance;
    }

    public byte[] getDefaultUserImage() {
        return Arrays.copyOf(defaultUserImage, defaultUserImage.length);
    }
}
