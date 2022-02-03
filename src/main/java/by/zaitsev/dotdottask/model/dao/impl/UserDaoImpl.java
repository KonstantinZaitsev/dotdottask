package by.zaitsev.dotdottask.model.dao.impl;

import by.zaitsev.dotdottask.exception.DaoException;
import by.zaitsev.dotdottask.model.dao.UserDao;
import by.zaitsev.dotdottask.model.entity.User;
import by.zaitsev.dotdottask.model.pool.ConnectionPool;
import by.zaitsev.dotdottask.util.ParameterIndex;
import by.zaitsev.dotdottask.util.SqlQuery;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UserDaoImpl is the main implementation of the {@link UserDao} interface.
 *
 * @author Konstantin Zaitsev
 */
public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private static UserDaoImpl instance;
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private UserDaoImpl() {
    }

    /**
     * @return a single instance of the UserDaoImpl class.
     */
    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public Optional<User> findEntityById(long id) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(SqlQuery.Users.FIND_ENTITY_BY_ID)) {
            preparedStatement.setLong(ParameterIndex.FIRST, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                var user = (User) EntityFactory.USER.build(resultSet);
                optionalUser = Optional.of(user);
            }
            logger.log(Level.DEBUG, "findEntityById(long id) method was completed successfully. " +
                    "User with id {} " + (optionalUser.isPresent() ? "was found" : "don't exist"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find user by id. Database access error: {}", e.getMessage());
            throw new DaoException("Unable to find user by id. Database access error: ", e);
        }
        return optionalUser;
    }

    @Override
    public List<User> findAllEntities() throws DaoException {
        List<User> userList = new ArrayList<>();
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(SqlQuery.Users.FIND_ALL_ENTITIES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var user = (User) EntityFactory.USER.build(resultSet);
                userList.add(user);
            }
            logger.log(Level.DEBUG, "findAllEntities() method was completed successfully");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find all users. Database access error: {}", e.getMessage());
            throw new DaoException("Unable to find all users. Database access error: ", e);
        }
        return userList;
    }

    @Override
    public long insertNewEntity(User entity) {
        logger.log(Level.ERROR, "insertNewEntity(User entity) method isn't supported");
        throw new UnsupportedOperationException("insertNewEntity(User entity) method isn't supported");
    }

    @Override
    public boolean deleteEntityById(long id) throws DaoException {
        boolean isDeleted;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(SqlQuery.Users.DELETE_ENTITY_BY_ID)) {
            preparedStatement.setLong(ParameterIndex.FIRST, id);
            isDeleted = preparedStatement.execute();
            logger.log(Level.DEBUG, "deleteEntityById(long id) method was completed successfully. " +
                    (isDeleted ? "User was deleted" : "User wasn't deleted"));
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to delete user from database. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to delete user from database. Database access error: ", e);
        }
        return isDeleted;
    }
}
