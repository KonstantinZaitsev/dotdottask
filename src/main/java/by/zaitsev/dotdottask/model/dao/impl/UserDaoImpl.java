package by.zaitsev.dotdottask.model.dao.impl;

import by.zaitsev.dotdottask.exception.DaoException;
import by.zaitsev.dotdottask.model.dao.UserDao;
import by.zaitsev.dotdottask.model.entity.User;
import by.zaitsev.dotdottask.model.pool.ConnectionPool;
import by.zaitsev.dotdottask.util.DefaultImageProvider;
import by.zaitsev.dotdottask.util.ParameterIndex;
import by.zaitsev.dotdottask.util.SqlQuery;
import by.zaitsev.dotdottask.util.TableColumn;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    @Override
    public long insertNewUser(User user, String encryptedPassword) throws DaoException {
        long id = 0;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(SqlQuery.Users.INSERT_NEW_USER,
                     Statement.RETURN_GENERATED_KEYS)) {
            var defaultImageProvider = DefaultImageProvider.getInstance();
            preparedStatement.setString(ParameterIndex.FIRST, user.getEmail());
            preparedStatement.setString(ParameterIndex.SECOND, encryptedPassword);
            preparedStatement.setString(ParameterIndex.THIRD, user.getName());
            preparedStatement.setString(ParameterIndex.FOURTH, user.getSurname());
            preparedStatement.setBytes(ParameterIndex.FIFTH, defaultImageProvider.getDefaultUserImage());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(TableColumn.Users.USER_ID);
            }
            logger.log(Level.DEBUG, "insertNewUser(User user, String encryptedPassword) method was completed " +
                    "successfully. User " + (id != 0 ? "with id {} was added" : "wasn't added"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to insert new user. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to insert new user. Database access error: ", e);
        }
        return id;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(SqlQuery.Users.FIND_USER_BY_EMAIL)) {
            preparedStatement.setString(ParameterIndex.FIRST, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                var user = (User) EntityFactory.USER.build(resultSet);
                optionalUser = Optional.of(user);
            }
            logger.log(Level.DEBUG, "findUserByEmail(String email) method was completed successfully. " +
                    "User with email {} " + (optionalUser.isPresent() ? "was found" : "don't exist"), email);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find user by email. Database access error: {}", e.getMessage());
            throw new DaoException("Unable to find user by email. Database access error: ", e);
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String encryptedPassword) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(
                     SqlQuery.Users.FIND_USER_BY_EMAIL_AND_PASSWORD)) {
            preparedStatement.setString(ParameterIndex.FIRST, email);
            preparedStatement.setString(ParameterIndex.SECOND, encryptedPassword);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                var user = (User) EntityFactory.USER.build(resultSet);
                optionalUser = Optional.of(user);
            }
            logger.log(Level.DEBUG, "findUserByEmailAndPassword(String email, String encryptedPassword) " +
                    "method was completed successfully. User with email {} " +
                    (optionalUser.isPresent() ? "was found" : "don't exist"), email);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find user by email and password. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to find user by email and password. Database access error: ", e);
        }
        return optionalUser;
    }

    @Override
    public boolean updateUserNameById(long id, String name) throws DaoException {
        boolean isUpdated;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(
                     SqlQuery.Users.UPDATE_USER_NAME_BY_ID)) {
            preparedStatement.setString(ParameterIndex.FIRST, name);
            preparedStatement.setLong(ParameterIndex.SECOND, id);
            isUpdated = preparedStatement.execute();
            logger.log(Level.DEBUG, "updateUserNameById(long id, String name) method was completed " +
                    "successfully. User with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update user's name by id. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to update user's name by id. Database access error: ", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateUserSurnameById(long id, String surname) throws DaoException {
        boolean isUpdated;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(
                     SqlQuery.Users.UPDATE_USER_SURNAME_BY_ID)) {
            preparedStatement.setString(ParameterIndex.FIRST, surname);
            preparedStatement.setLong(ParameterIndex.SECOND, id);
            isUpdated = preparedStatement.execute();
            logger.log(Level.DEBUG, "updateUserSurnameById(long id, String surname) method was completed " +
                    "successfully. User with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update user's surname by id. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to update user's surname by id. Database access error: ", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateUserPasswordById(long id, String encryptedPassword) throws DaoException {
        boolean isUpdated;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(
                     SqlQuery.Users.UPDATE_USER_PASSWORD_BY_ID)) {
            preparedStatement.setString(ParameterIndex.FIRST, encryptedPassword);
            preparedStatement.setLong(ParameterIndex.SECOND, id);
            isUpdated = preparedStatement.execute();
            logger.log(Level.DEBUG, "updateUserPasswordById(long id, String encryptedPassword) method was " +
                    "completed successfully. User with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update user's password by id. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to update user's password by id. Database access error: ", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateUserImageById(long id, byte[] image) throws DaoException {
        boolean isUpdated;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(
                     SqlQuery.Users.UPDATE_USER_IMAGE_BY_ID)) {
            preparedStatement.setBytes(ParameterIndex.FIRST, image);
            preparedStatement.setLong(ParameterIndex.SECOND, id);
            isUpdated = preparedStatement.execute();
            logger.log(Level.DEBUG, "updateUserImageById(long id, byte[] image) method was completed " +
                    "successfully. User with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update user's image by id. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to update user's image by id. Database access error: ", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateUserEmailById(long id, String email) throws DaoException {
        boolean isUpdated;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(
                     SqlQuery.Users.UPDATE_USER_EMAIL_BY_ID)) {
            preparedStatement.setString(ParameterIndex.FIRST, email);
            preparedStatement.setLong(ParameterIndex.SECOND, id);
            isUpdated = preparedStatement.execute();
            logger.log(Level.DEBUG, "updateUserEmailById(long id, String email) method was completed " +
                    "successfully. User with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update user's email by id. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to update user's email by id. Database access error: ", e);
        }
        return isUpdated;
    }

    @Override
    public List<User> findAllAssignedUsersByProjectId(long id) throws DaoException {
        List<User> userList = new ArrayList<>();
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(
                     SqlQuery.Users.FIND_ALL_ASSIGNED_USERS_BY_PROJECT_ID)) {
            preparedStatement.setLong(ParameterIndex.FIRST, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var user = (User) EntityFactory.USER.build(resultSet);
                userList.add(user);
            }
            logger.log(Level.DEBUG, "findAllAssignedUsersByProjectId(long id) method was completed " +
                    "successfully");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find all assigned users. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to find all assigned users. Database access error: ", e);
        }
        return userList;
    }

    @Override
    public boolean deleteAssignedUserByProjectId(long projectId, long userId) throws DaoException {
        boolean isDeleted;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(
                     SqlQuery.Users.DELETE_ASSIGNED_USER_BY_PROJECT_ID)) {
            preparedStatement.setLong(ParameterIndex.FIRST, projectId);
            preparedStatement.setLong(ParameterIndex.SECOND, userId);
            isDeleted = preparedStatement.execute();
            logger.log(Level.DEBUG, "deleteAssignedUserByProjectId(long projectId, long userId) method was " +
                    "completed successfully. " + (isDeleted ? "User was deleted" : "User wasn't deleted"));
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to delete user from database. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to delete user from database. Database access error: ", e);
        }
        return isDeleted;
    }
}
