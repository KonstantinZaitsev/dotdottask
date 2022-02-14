package by.zaitsev.dotdottask.controller.command.impl.navigation;

import by.zaitsev.dotdottask.controller.Router;
import by.zaitsev.dotdottask.controller.command.Command;
import by.zaitsev.dotdottask.exception.CommandException;
import by.zaitsev.dotdottask.util.PagePath;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * GoToSignUpPageCommand is the class used to navigate to the sign-up page.
 *
 * @author Konstantin Zaitsev
 */
public class GoToSignUpPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToSignUpPageCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        var router = new Router();
        router.setPagePath(PagePath.SIGN_UP_PAGE);
        logger.log(Level.DEBUG, "execute(HttpServletRequest request) method was completed successfully. " +
                "Forwarded to sign up page");
        return router;
    }
}
