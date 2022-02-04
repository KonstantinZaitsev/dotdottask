package by.zaitsev.dotdottask.controller.command.impl.guest;

import by.zaitsev.dotdottask.controller.Router;
import by.zaitsev.dotdottask.controller.command.Command;
import by.zaitsev.dotdottask.exception.CommandException;
import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.service.impl.UserServiceImpl;
import by.zaitsev.dotdottask.util.AttributeName;
import by.zaitsev.dotdottask.util.PagePath;
import by.zaitsev.dotdottask.util.ParameterName;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * SignUpCommand is the command used to register new users.
 *
 * @author Konstantin Zaitsev
 */
public class SignUpCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SignUpCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        var router = new Router();
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put(ParameterName.EMAIL, request.getParameter(ParameterName.EMAIL));
        parameterMap.put(ParameterName.PASSWORD, request.getParameter(ParameterName.PASSWORD));
        parameterMap.put(ParameterName.CONFIRMED_PASSWORD, request.getParameter(ParameterName.CONFIRMED_PASSWORD));
        parameterMap.put(ParameterName.NAME, request.getParameter(ParameterName.NAME));
        parameterMap.put(ParameterName.SURNAME, request.getParameter(ParameterName.SURNAME));
        try {
            long id = UserServiceImpl.getInstance().insertNewUser(parameterMap);
            boolean isRegistered = id != 0;
            if (!isRegistered) {
                for (String key : parameterMap.keySet()) {
                    String validationResult = parameterMap.get(key);
                    if (Boolean.parseBoolean(validationResult)) {
                        switch (key) {
                            case ParameterName.EMAIL -> request.setAttribute(AttributeName.VALID_EMAIL,
                                    request.getParameter(ParameterName.EMAIL));
                            case ParameterName.NAME -> request.setAttribute(AttributeName.VALID_NAME,
                                    request.getParameter(ParameterName.NAME));
                            case ParameterName.SURNAME -> request.setAttribute(AttributeName.VALID_SURNAME,
                                    request.getParameter(ParameterName.SURNAME));
                        }
                    } else {
                        switch (key) {
                            case ParameterName.EMAIL -> request.setAttribute(AttributeName.INVALID_EMAIL,
                                    validationResult);
                            case ParameterName.PASSWORD -> request.setAttribute(AttributeName.INVALID_PASSWORD,
                                    validationResult);
                            case ParameterName.CONFIRMED_PASSWORD -> request.setAttribute(
                                    AttributeName.INVALID_CONFIRMED_PASSWORD, validationResult);
                            case ParameterName.NAME -> request.setAttribute(AttributeName.INVALID_NAME,
                                    validationResult);
                            case ParameterName.SURNAME -> request.setAttribute(AttributeName.INVALID_SURNAME,
                                    validationResult);
                        }
                    }
                }
            }
            request.setAttribute(AttributeName.REGISTRATION_RESULT, isRegistered);
            router.setPagePath(PagePath.AUTHORIZATION_PAGE);
            logger.log(Level.DEBUG, "execute(HttpServletRequest request) method was completed successfully. " +
                    "Forwarded to authorization page");
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "User cannot be registered: {}", e.getMessage());
            throw new CommandException("Student cannot be registered: ", e);
        }
        return router;
    }
}
