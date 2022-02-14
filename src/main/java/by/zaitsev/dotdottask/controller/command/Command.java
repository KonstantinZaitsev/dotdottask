package by.zaitsev.dotdottask.controller.command;

import by.zaitsev.dotdottask.controller.Router;
import by.zaitsev.dotdottask.exception.CommandException;
import by.zaitsev.dotdottask.model.service.BaseService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Command is an interface implemented by all classes that help the controller handle requests.
 *
 * @author Konstantin Zaitsev
 */
public interface Command {
    /**
     * @param request contains information about how the command should be processed.
     * @return {@link Router} which helps the controller figure out which page to redirect the user to.
     * @throws CommandException if the service class cannot process the request.
     * @see BaseService
     */
    Router execute(HttpServletRequest request) throws CommandException;
}
