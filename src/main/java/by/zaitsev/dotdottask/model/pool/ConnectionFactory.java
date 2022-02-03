package by.zaitsev.dotdottask.model.pool;

import by.zaitsev.dotdottask.util.Property;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The ConnectionFactory is a helper class for creating database {@link Connection}. Configured via
 * ../property/database.properties file.
 *
 * @author Konstantin Zaitsev
 */
class ConnectionFactory {
    private static final Logger logger = LogManager.getLogger(ConnectionFactory.class);
    private static ConnectionFactory instance;
    private final Properties properties;
    private final String databaseUrl;

    private ConnectionFactory() {
        properties = new Properties();
        try (InputStream stream = ConnectionFactory.class.getResourceAsStream(Property.Database.FILE_PATH)) {
            properties.load(stream);
            databaseUrl = properties.getProperty(Property.Database.URL);
            String databaseDriver = properties.getProperty(Property.Database.DRIVER);
            Class.forName(databaseDriver);
        } catch (IOException e) {
            logger.log(Level.FATAL, "IOException while loading properties: {}", e.getMessage());
            throw new RuntimeException("IOException while loading properties: ", e);
        } catch (ClassNotFoundException e) {
            String databaseDriver = properties.getProperty(Property.Database.DRIVER);
            logger.log(Level.FATAL, "Driver {} not found: {}",
                    databaseDriver,
                    e.getMessage());
            throw new RuntimeException(String.format("Driver %s not found: ",
                    databaseDriver),
                    e);
        }
    }

    /**
     * @return a single instance of the ConnectionFactory class
     */
    static ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    /**
     * @return a new database {@link Connection}.
     * @throws SQLException if a database access error occurs.
     */
    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseUrl, properties);
    }
}
