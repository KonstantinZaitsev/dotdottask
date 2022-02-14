package by.zaitsev.dotdottask.exception;

import by.zaitsev.dotdottask.model.service.BaseService;

/**
 * ServiceException is an exception class used in Service classes.
 *
 * @author Konstantin Zaitsev
 * @see BaseService
 */
public class ServiceException extends Exception {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
