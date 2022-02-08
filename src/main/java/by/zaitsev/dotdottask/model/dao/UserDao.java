package by.zaitsev.dotdottask.model.dao;

import by.zaitsev.dotdottask.exception.DaoException;
import by.zaitsev.dotdottask.model.entity.User;

import java.util.Optional;

/**
 * UserDao is an interface implemented by classes that will access the users table.
 *
 * @author Kosntantin Zaitsev
 */
public interface UserDao extends BaseDao<User> {
    /**
     * @param user              user to be added to the database.
     * @param encryptedPassword sha-256 encrypted user password.
     * @return entity id if it was added, otherwise 0.
     * @throws DaoException if the request to database could not be handled.
     */
    long insertNewUser(User user, String encryptedPassword) throws DaoException;

    /**
     * @param email user email to search.
     * @return a user wrapped in {@link Optional} if it exists in the database or empty {@link Optional} if it does
     * not exist in the database.
     * @throws DaoException if the request to database could not be handled.
     */
    Optional<User> findUserByEmail(String email) throws DaoException;

    /**
     * @param email             user email to search.
     * @param encryptedPassword sha-256 encrypted user password.
     * @return a user wrapped in {@link Optional} if it exists in the database or empty {@link Optional} if it does
     * not exist in the database.
     * @throws DaoException if the request to database could not be handled.
     */
    Optional<User> findUserByEmailAndPassword(String email, String encryptedPassword) throws DaoException;

    /**
     * @param id   user id to update.
     * @param name new user's name.
     * @return true if the user has been updated, otherwise false.
     * @throws DaoException if the request to database could not be handled.
     */
    boolean updateUserNameById(long id, String name) throws DaoException;

    /**
     * @param id      user id to update.
     * @param surname new user's surname.
     * @return true if the user has been updated, otherwise false.
     * @throws DaoException if the request to database could not be handled.
     */
    boolean updateUserSurnameById(long id, String surname) throws DaoException;

    /**
     * @param id                user id to update.
     * @param encryptedPassword new user's password.
     * @return true if the user has been updated, otherwise false.
     * @throws DaoException if the request to database could not be handled.
     */
    boolean updateUserPasswordById(long id, String encryptedPassword) throws DaoException;

    /**
     * @param id    user id to update.
     * @param image new user's image.
     * @return true if the user has been updated, otherwise false.
     * @throws DaoException if the request to database could not be handled.
     */
    boolean updateUserImageById(long id, byte[] image) throws DaoException;

    /**
     * @param id user id to update.
     * @param email ew user's email.
     * @return true if the user has been updated, otherwise false.
     * @throws DaoException if the request to database could not be handled.
     */
    boolean updateUserEmailById(long id, String email) throws DaoException;
}
