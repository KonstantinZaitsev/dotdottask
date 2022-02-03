package by.zaitsev.dotdottask.model.service;

import by.zaitsev.dotdottask.model.entity.User;
import by.zaitsev.dotdottask.model.dao.UserDao;

/**
 * UserService is an interface implemented by a class that will access classes that implement the
 * {@link UserDao} interface.
 *
 * @author Konstantin Zaitsev
 */
public interface UserService extends BaseService<User> {
}
