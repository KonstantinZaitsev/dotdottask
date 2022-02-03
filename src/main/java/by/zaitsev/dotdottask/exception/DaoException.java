package by.zaitsev.dotdottask.exception;

import by.zaitsev.dotdottask.model.dao.BaseDao;

/**
 * DaoException is an exception class used in Dao classes.
 *
 * @author Konstantin Zaitsev
 * @see BaseDao
 */
public class DaoException extends Exception {
    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
