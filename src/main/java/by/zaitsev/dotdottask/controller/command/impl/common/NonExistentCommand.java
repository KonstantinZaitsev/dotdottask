package by.zaitsev.dotdottask.controller.command.impl.common;

import by.zaitsev.dotdottask.controller.Router;
import by.zaitsev.dotdottask.controller.command.Command;
import by.zaitsev.dotdottask.util.AttributeName;
import by.zaitsev.dotdottask.util.PagePath;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * NonExistent Command is the command used when an unknown command is received in request.
 *
 * @author Konstantin Zaitsev
 */
public class NonExistentCommand implements Command {
    private static final Logger logger = LogManager.getLogger(NonExistentCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        request.setAttribute(AttributeName.WRONG_COMMAND, true);
        var router = new Router();
        router.setPagePath(PagePath.ERROR_400_PAGE);
        logger.log(Level.DEBUG, "execute(HttpServletRequest request) method was completed successfully. " +
                "Forwarded to error400 page");
        return router;
    }
}
