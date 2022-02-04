package by.zaitsev.dotdottask.model.service;

import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.entity.User;
import by.zaitsev.dotdottask.model.dao.UserDao;

import java.util.Map;

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
}
