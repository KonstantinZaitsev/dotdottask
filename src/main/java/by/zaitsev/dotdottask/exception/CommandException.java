package by.zaitsev.dotdottask.exception;

/**
 * CommandException is an exception class used in Command classes.
 *
 * @author Konstantin Zaitsev
 */
public class CommandException extends Exception {
    public CommandException() {
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
