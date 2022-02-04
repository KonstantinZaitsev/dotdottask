package by.zaitsev.dotdottask.model.dao;

import by.zaitsev.dotdottask.exception.DaoException;
import by.zaitsev.dotdottask.model.entity.User;

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
}
