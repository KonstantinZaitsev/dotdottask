package by.zaitsev.dotdottask.controller.command.impl.common;

import by.zaitsev.dotdottask.controller.Router;
import by.zaitsev.dotdottask.controller.command.Command;
import by.zaitsev.dotdottask.util.AttributeName;
import by.zaitsev.dotdottask.util.ParameterName;
import by.zaitsev.dotdottask.util.validator.LocaleValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ChangeLocaleCommand is used to change the application's locale.
 *
 * @author Konstantin Zaitsev
 */
public class ChangeLocaleCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangeLocaleCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String newLocale = request.getParameter(ParameterName.LOCALE);
        if (LocaleValidator.getInstance().isLocaleValid(newLocale)) {
            session.setAttribute(AttributeName.LOCALE, newLocale);
        } else {
            logger.log(Level.WARN, "Wrong locale parameter: {}", newLocale);
        }
        var currentPage = (String) session.getAttribute(AttributeName.CURRENT_PAGE);
        var router = new Router();
        router.setPagePath(currentPage);
        logger.log(Level.DEBUG, "execute(HttpServletRequest request) method was completed successfully. " +
                "Forwarded to current page");
        return router;
    }
}
