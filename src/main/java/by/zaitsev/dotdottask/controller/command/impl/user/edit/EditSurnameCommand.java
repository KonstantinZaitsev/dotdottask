package by.zaitsev.dotdottask.controller.command.impl.user.edit;

import by.zaitsev.dotdottask.controller.Router;
import by.zaitsev.dotdottask.controller.command.Command;
import by.zaitsev.dotdottask.exception.CommandException;
import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.entity.User;
import by.zaitsev.dotdottask.model.service.impl.UserServiceImpl;
import by.zaitsev.dotdottask.util.AttributeName;
import by.zaitsev.dotdottask.util.PagePath;
import by.zaitsev.dotdottask.util.ParameterName;
import by.zaitsev.dotdottask.util.validator.UserValidator;
import by.zaitsev.dotdottask.util.validator.ValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * EditSurnameCommand is used to edit user surname.
 *
 * @author Konstantin Zaitsev
 */
public class EditSurnameCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditSurnameCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        var user = (User) session.getAttribute(AttributeName.USER);
        String surname = request.getParameter(ParameterName.SURNAME);
        var userValidator = UserValidator.getInstance();
        try {
            if (userValidator.isSurnameValid(surname)) {
                UserServiceImpl.getInstance().updateUserSurnameById(user.getId(), surname);
                user.setSurname(surname);
                session.setAttribute(AttributeName.USER, user);
                logger.log(Level.DEBUG, "execute(HttpServletRequest request) method was completed " +
                        "successfully. Forwarded to catalog page");
            } else {
                request.setAttribute(AttributeName.UPDATE_SURNAME_RESULT, ValidationResult.INVALID);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "User cannot be updated: {}", e.getMessage());
            throw new CommandException("User cannot be updated: ", e);
        }
        var router = new Router();
        router.setPagePath(PagePath.CATALOG_PAGE);
        return router;
    }
}
