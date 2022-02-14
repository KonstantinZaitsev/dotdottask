package by.zaitsev.dotdottask.util;

/**
 * Property is a class containing paths to .properties files and the names of their elements.
 * Each .properties file is associated with an inner class.
 *
 * @author Konstantin Zaitsev
 */
public final class Property {
    /**
     * ConnectionPool is an inner class that stores the path and element names for the connection-pool.properties file.
     *
     * @author Konstantin Zaitsev
     * @see Property
     */
    public static final class ConnectionPool {
        public static final String FILE_PATH = "/config/connection-pool.properties";
        public static final String POOL_SIZE = "cp.pool-size";

        private ConnectionPool() {
        }
    }

    /**
     * Database is an inner class that stores the path and element names for the database.properties file.
     *
     * @author Konstantin Zaitsev
     * @see Property
     */
    public static final class Database {
        public static final String FILE_PATH = "/config/database.properties";
        public static final String URL = "db.url";
        public static final String DRIVER = "db.driver";

        private Database() {
        }
    }

    private Property() {
    }
}
