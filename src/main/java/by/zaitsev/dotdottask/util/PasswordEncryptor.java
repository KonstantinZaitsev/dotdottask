package by.zaitsev.dotdottask.util;

import jakarta.xml.bind.DatatypeConverter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import by.zaitsev.dotdottask.model.service.BaseService;

/**
 * PasswordEncryptor is a class used to encrypt a password for later storage in a database. Used in the Service classes.
 *
 * @author Konstantin Zaitsev
 * @see BaseService
 */
public class PasswordEncryptor {
    private static final Logger logger = LogManager.getLogger(PasswordEncryptor.class);
    private static final String ENCRYPT_ALGORITHM = "sha-256";
    private static final String SALT_KEY = "5hr8Uh32Hr";
    private static PasswordEncryptor instance;

    private PasswordEncryptor() {
    }

    /**
     * @return a single instance of the DefaultImageProvider class.
     */
    public static PasswordEncryptor getInstance() {
        if (instance == null) {
            instance = new PasswordEncryptor();
        }
        return instance;
    }

    /**
     * @param password encryption password.
     * @return sha-256 encrypted password.
     */
    public String encrypt(String password) {
        String encryptedPassword;
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        byte[] saltBytes = SALT_KEY.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest digest = MessageDigest.getInstance(ENCRYPT_ALGORITHM);
            digest.update(saltBytes);
            encryptedPassword = DatatypeConverter.printHexBinary(digest.digest(passwordBytes));
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.ERROR, "Password cannot be encrypted: {}", e.getMessage());
            throw new RuntimeException("Password cannot be encrypted: ", e);
        }
        return encryptedPassword;
    }
}
