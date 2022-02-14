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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The ConnectionPool is a thread-safe singleton class for accessing {@link Connection}s. Uses two
 * {@link BlockingQueue}s<{@link ProxyConnection}> to store free and busy {@link Connection}s. Configured via
 * ../property/connection-pool.properties file.
 *
 * @author Konstantin Zaitsev
 */
public class ConnectionPool {
    public static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static final AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static final Lock lock = new ReentrantLock(true);
    private static ConnectionPool instance;
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final BlockingQueue<ProxyConnection> busyConnections;

    private ConnectionPool() {
        var properties = new Properties();
        int poolSize;
        try (InputStream stream = ConnectionPool.class.getResourceAsStream(Property.ConnectionPool.FILE_PATH)) {
            properties.load(stream);
            poolSize = Integer.parseInt(properties.getProperty(Property.ConnectionPool.POOL_SIZE));
        } catch (IOException e) {
            logger.log(Level.FATAL, "IOException while loading properties: {}", e.getMessage());
            throw new RuntimeException("IOException while loading properties: ", e);
        }
        freeConnections = new LinkedBlockingDeque<>(poolSize);
        busyConnections = new LinkedBlockingDeque<>(poolSize);
        var connectionFactory = ConnectionFactory.getInstance();
        for (int i = 0; i < poolSize; ++i) {
            try {
                var connection = connectionFactory.getConnection();
                var proxyConnection = new ProxyConnection(connection);
                freeConnections.add(proxyConnection);
            } catch (SQLException e) {
                logger.log(Level.WARN, "Can't create connection to data base: {}", e.getMessage());
            }
        }
        if (freeConnections.isEmpty()) {
            logger.log(Level.FATAL, "Connection pool wasn't created");
            throw new RuntimeException("Connection pool wasn't created");
        }
        logger.log(Level.INFO, "Connection pool was created successfully");
    }

    /**
     * Method for implementing the thread-safe singleton pattern. For these purposes, double check is used.
     *
     * @return instance of {@link ConnectionPool} class.
     */
    public static ConnectionPool getInstance() {
        if (!isInitialized.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    isInitialized.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * Method to get a free {@link Connection}.
     *
     * @return {@link Connection} wrapped in {@link ProxyConnection} class.
     */
    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            busyConnections.put(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "InterruptedException in method getConnection: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     * Method to release a {@link Connection} that is no longer in use.
     *
     * @param connection must be obtained using the {@link #getConnection()} method, otherwise the {@link Connection}
     *                   will not be returned to the {@link ConnectionPool}.
     */
    public void releaseConnection(Connection connection) {
        if ((connection instanceof ProxyConnection) && (busyConnections.remove(connection))) {
            try {
                freeConnections.put((ProxyConnection) connection);
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, "InterruptedException in method getConnection: {}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Method for closing the {@link ConnectionPool}, first closes all {@link Connection}s, then deregister the
     * {@link java.sql.Driver}s.
     */
    public void destroyPool() {
        freeConnections.forEach(connection -> {
            try {
                connection.trueClose();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "SQLException in method destroyPool: {}", e.getMessage());
            }
        });
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "SQLException in method deregisterDrivers: {}", e.getMessage());
            }
        });
        logger.log(Level.INFO, "Connection pool was destroyed successfully");
    }
}
