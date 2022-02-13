package by.zaitsev.dotdottask.model.service.impl;

import by.zaitsev.dotdottask.exception.DaoException;
import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.dao.UserDao;
import by.zaitsev.dotdottask.model.dao.impl.UserDaoImpl;
import by.zaitsev.dotdottask.model.entity.User;
import by.zaitsev.dotdottask.model.service.UserService;
import by.zaitsev.dotdottask.util.ParameterName;
import by.zaitsev.dotdottask.util.PasswordEncryptor;
import by.zaitsev.dotdottask.util.validator.UserValidator;
import by.zaitsev.dotdottask.util.validator.ValidationResult;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
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

    @Override
    public long insertNewUser(Map<String, String> parameterMap) throws ServiceException {
        String email = parameterMap.get(ParameterName.EMAIL);
        String password = parameterMap.get(ParameterName.PASSWORD);
        String confirmedPassword = parameterMap.get(ParameterName.CONFIRMED_PASSWORD);
        String name = parameterMap.get(ParameterName.NAME);
        String surname = parameterMap.get(ParameterName.SURNAME);
        var userValidator = UserValidator.getInstance();
        long id = 0;
        try {
            boolean isEmailExist = userDao.findUserByEmail(email).isPresent();
            String emailCheckResult;
            if (userValidator.isEmailValid(email)) {
                emailCheckResult = !isEmailExist ? ValidationResult.VALID : ValidationResult.NOT_UNIQUE;
            } else {
                emailCheckResult = ValidationResult.INVALID;
            }
            String passwordCheckResult = userValidator.isPasswordValid(password) ?
                    ValidationResult.VALID : ValidationResult.INVALID;
            String confirmedPasswordCheckResult = password.equals(confirmedPassword) ?
                    ValidationResult.VALID : ValidationResult.INVALID;
            String nameCheckResult = userValidator.isNameValid(name) ?
                    ValidationResult.VALID : ValidationResult.INVALID;
            String surnameCheckResult = userValidator.isSurnameValid(surname) ?
                    ValidationResult.VALID : ValidationResult.INVALID;
            boolean isEmailValid = Boolean.parseBoolean(emailCheckResult);
            boolean isPasswordValid = Boolean.parseBoolean(passwordCheckResult);
            boolean isConfirmedPasswordValid = Boolean.parseBoolean(confirmedPasswordCheckResult);
            boolean isNameValid = Boolean.parseBoolean(nameCheckResult);
            boolean isSurnameValid = Boolean.parseBoolean(surnameCheckResult);
            boolean result = isEmailValid &&
                    isPasswordValid &&
                    isConfirmedPasswordValid &&
                    isNameValid &&
                    isSurnameValid;
            if (result) {
                var user = new User();
                user.setEmail(email);
                user.setName(name);
                user.setSurname(surname);
                String encryptedPassword = PasswordEncryptor.getInstance().encrypt(password);
                id = userDao.insertNewUser(user, encryptedPassword);
            } else {
                parameterMap.replace(ParameterName.EMAIL, emailCheckResult);
                parameterMap.replace(ParameterName.PASSWORD, passwordCheckResult);
                parameterMap.replace(ParameterName.CONFIRMED_PASSWORD, confirmedPasswordCheckResult);
                parameterMap.replace(ParameterName.NAME, nameCheckResult);
                parameterMap.replace(ParameterName.SURNAME, surnameCheckResult);
            }
            logger.log(Level.DEBUG, "insertNewUser(Map<String, String> parameterMap) method was completed " +
                    "successfully. User " + (id != 0 ? "with id {} was added" : "wasn't added"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to insert new user. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to insert new user. Dao access error: ", e);
        }
        return id;
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) throws ServiceException {
        Optional<User> optionalUser;
        String encryptedPassword = PasswordEncryptor.getInstance().encrypt(password);
        try {
            optionalUser = userDao.findUserByEmailAndPassword(email, encryptedPassword);
            logger.log(Level.DEBUG, "findUserByEmailAndPassword(String email, String password) method was " +
                    "completed successfully. User with email {} " +
                    (optionalUser.isPresent() ? "was found" : "don't exist"), email);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find user by email and password. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to find user by email and password. Dao access error: ", e);
        }
        return optionalUser;
    }

    @Override
    public boolean updateUserNameById(long id, String name) throws ServiceException {
        boolean isUpdated;
        try {
            isUpdated = userDao.updateUserNameById(id, name);
            logger.log(Level.DEBUG, "updateUserNameById(long id, String name) method was completed " +
                    "successfully. User with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update user's name by id. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to update user's name by id. Dao access error: ", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateUserSurnameById(long id, String surname) throws ServiceException {
        boolean isUpdated;
        try {
            isUpdated = userDao.updateUserSurnameById(id, surname);
            logger.log(Level.DEBUG, "updateUserSurnameById(long id, String surname) method was completed " +
                    "successfully. User with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update user's surname by id. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to update user's surname by id. Dao access error:  ", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateUserPasswordById(long id, String password) throws ServiceException {
        boolean isUpdated;
        try {
            isUpdated = userDao.updateUserPasswordById(id, PasswordEncryptor.getInstance().encrypt(password));
            logger.log(Level.DEBUG, "updateUserPasswordById(long id, String encryptedPassword) method was " +
                    "completed successfully. User with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update user's password by id. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to update user's password by id. Dao access error: ", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateUserImageById(long id, byte[] image) throws ServiceException {
        boolean isUpdated;
        try {
            isUpdated = userDao.updateUserImageById(id, image);
            logger.log(Level.DEBUG, "updateUserImageById(long id, byte[] image) method was completed " +
                    "successfully. User with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update user's image by id. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to update user's image by id. Dao access error: ", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateUserEmailById(long id, String email) throws ServiceException {
        boolean isUpdated;
        try {
            isUpdated = userDao.updateUserEmailById(id, email);
            logger.log(Level.DEBUG, "updateUserEmailById(long id, String email) method was completed " +
                    "successfully. User with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update user's email by id. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to update user's email by id. Dao access error: ", e);
        }
        return isUpdated;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws ServiceException {
        Optional<User> optionalUser;
        try {
            optionalUser = userDao.findUserByEmail(email);
            logger.log(Level.DEBUG, "findUserByEmail(String email) method was completed successfully. " +
                    "User with email {} " + (optionalUser.isPresent() ? "was found" : "don't exist"), email);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find user by email. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to find user by email. Dao access error: ", e);
        }
        return optionalUser;
    }

    @Override
    public List<User> findAllAssignedUsersByProjectId(long id) throws ServiceException {
        List<User> userList;
        try {
            userList = userDao.findAllAssignedUsersByProjectId(id);
            logger.log(Level.DEBUG, "findAllAssignedUsersByProjectId(long id) method was completed " +
                    "successfully");
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find all assigned users. Dao access error: {}", e.getMessage());
            throw new ServiceException("Unable to find all assigned users. Dao access error: ", e);
        }
        return userList;
    }

    @Override
    public boolean deleteAssignedUserByProjectId(long projectId, long userId) throws ServiceException {
        boolean isDeleted;
        try {
            isDeleted = userDao.deleteAssignedUserByProjectId(projectId, userId);
            logger.log(Level.DEBUG, "deleteAssignedUserByProjectId(long projectId, long userId) method was " +
                    "completed successfully. " + (isDeleted ? "User was deleted" : "User wasn't deleted"));
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to delete user from database. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to delete user from database. Dao access error: ", e);
        }
        return isDeleted;
    }
}
