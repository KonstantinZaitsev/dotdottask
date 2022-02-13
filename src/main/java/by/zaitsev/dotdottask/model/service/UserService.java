package by.zaitsev.dotdottask.model.service;

import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.entity.User;
import by.zaitsev.dotdottask.model.dao.UserDao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * UserService is an interface implemented by a class that will access classes that implement the
 * {@link UserDao} interface.
 *
 * @author Konstantin Zaitsev
 */
public interface UserService extends BaseService<User> {
    /**
     * @param parameterMap map with parameters passed from request.
     * @return entity id if it was added, otherwise 0.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    long insertNewUser(Map<String, String> parameterMap) throws ServiceException;

    /**
     * @param email    user email to search.
     * @param password user password to search.
     * @return a user wrapped in {@link Optional} if it exists in the database or empty {@link Optional} if it
     * does not exist in the database.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    Optional<User> findUserByEmailAndPassword(String email, String password) throws ServiceException;

    /**
     * @param id   user id to update.
     * @param name new user's name.
     * @return true if the user has been updated, otherwise false.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    boolean updateUserNameById(long id, String name) throws ServiceException;

    /**
     * @param id      user id to update.
     * @param surname new user's surname.
     * @return true if the user has been updated, otherwise false.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    boolean updateUserSurnameById(long id, String surname) throws ServiceException;

    /**
     * @param id       user id to update.
     * @param password new user's password.
     * @return true if the user has been updated, otherwise false.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    boolean updateUserPasswordById(long id, String password) throws ServiceException;

    /**
     * @param id    user id to update.
     * @param image new user's image.
     * @return true if the user has been updated, otherwise false.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    boolean updateUserImageById(long id, byte[] image) throws ServiceException;

    /**
     * @param id    user id to update.
     * @param email new user's email.
     * @return true if the user has been updated, otherwise false.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    boolean updateUserEmailById(long id, String email) throws ServiceException;

    /**
     * @param email user email to search.
     * @return a user wrapped in {@link Optional} if it exists in the database or empty {@link Optional} if it
     * does not exist in the database.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    Optional<User> findUserByEmail(String email) throws ServiceException;

    /**
     * @param id project id to find users.
     * @return list of found users.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    List<User> findAllAssignedUsersByProjectId(long id) throws ServiceException;
}
