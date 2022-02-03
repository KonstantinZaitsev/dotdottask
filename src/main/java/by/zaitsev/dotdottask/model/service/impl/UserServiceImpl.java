package by.zaitsev.dotdottask.model.service.impl;

import by.zaitsev.dotdottask.exception.DaoException;
import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.dao.UserDao;
import by.zaitsev.dotdottask.model.dao.impl.UserDaoImpl;
import by.zaitsev.dotdottask.model.entity.User;
import by.zaitsev.dotdottask.model.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * UserServiceImpl is the main implementation of the {@link UserService} interface.
 *
 * @author Konstantin Zaitsev
 */
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static UserServiceImpl instance;
    private final UserDao userDao = UserDaoImpl.getInstance();

    private UserServiceImpl() {
    }

    /**
     * @return a single instance of the UserServiceImpl class.
     */
    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<User> findEntityById(long id) throws ServiceException {
        Optional<User> optionalUser;
        try {
            optionalUser = userDao.findEntityById(id);
            logger.log(Level.DEBUG, "findEntityById(long id) method was completed successfully. " +
                    "User with id {} " + (optionalUser.isPresent() ? "was found" : "don't exist"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find user by id. Dao access error: {}", e.getMessage());
            throw new ServiceException("Unable to find user by id. Dao access error: ", e);
        }
        return optionalUser;
    }

    @Override
    public List<User> findAllEntities() throws ServiceException {
        List<User> userList;
        try {
            userList = userDao.findAllEntities();
            logger.log(Level.DEBUG, "findAllEntities() method was completed successfully");
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find all users. Dao access error: {}", e.getMessage());
            throw new ServiceException("Unable to find all users. Dao access error: ", e);
        }
        return userList;
    }

    @Override
    public long insertNewEntity(User entity) {
        logger.log(Level.ERROR, "insertNewEntity(User entity) method isn't supported");
        throw new UnsupportedOperationException("insertNewEntity(User entity) method isn't supported");
    }

    @Override
    public boolean deleteEntityById(long id) throws ServiceException {
        boolean isDeleted;
        try {
            isDeleted = userDao.deleteEntityById(id);
            logger.log(Level.DEBUG, "deleteEntityById(long id) method was completed successfully. " +
                    (isDeleted ? "User was deleted" : "User wasn't deleted"));
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to delete user from database. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to delete user from database. Dao access error: ", e);
        }
        return isDeleted;
    }
}
