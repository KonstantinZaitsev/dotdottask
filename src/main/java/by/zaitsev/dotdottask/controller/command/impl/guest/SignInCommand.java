package by.zaitsev.dotdottask.controller.command.impl.guest;

import by.zaitsev.dotdottask.controller.Router;
import by.zaitsev.dotdottask.controller.command.Command;
import by.zaitsev.dotdottask.exception.CommandException;
import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.entity.Project;
import by.zaitsev.dotdottask.model.entity.User;
import by.zaitsev.dotdottask.model.service.impl.ProjectServiceImpl;
import by.zaitsev.dotdottask.model.service.impl.UserServiceImpl;
import by.zaitsev.dotdottask.util.AttributeName;
import by.zaitsev.dotdottask.util.PagePath;
import by.zaitsev.dotdottask.util.ParameterName;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * SignInCommand is the command used to sign in to an account in the application.
 *
 * @author Konstantin Zaitsev
 */
public class SignInCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SignInCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String email = request.getParameter(ParameterName.EMAIL);
        String password = request.getParameter(ParameterName.PASSWORD);
        var router = new Router();
        try {
            Optional<User> optionalUser = UserServiceImpl.getInstance().findUserByEmailAndPassword(email, password);
            if (optionalUser.isPresent()) {
                var user = optionalUser.get();
                session.setAttribute(AttributeName.USER, user);
                var projectService = ProjectServiceImpl.getInstance();
                List<Project> ownProjects = projectService.findAllUserOwnProjectsById(user.getId());
                session.setAttribute(AttributeName.OWN_PROJECTS, ownProjects);
                List<Project> invitedProjects = projectService.findAllUserInvitedProjectsById(user.getId());
                session.setAttribute(AttributeName.INVITED_PROJECTS, invitedProjects);
                request.setAttribute(AttributeName.SIGN_IN_RESULT, true);
                router.setPagePath(PagePath.CATALOG_PAGE);
            } else {
                request.setAttribute(AttributeName.SIGN_IN_RESULT, false);
                router.setPagePath(PagePath.SIGN_IN_PAGE);
            }
            logger.log(Level.DEBUG, "execute(HttpServletRequest request) method was completed successfully. " +
                    "Forwarded to catalog page");
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "User sign in could not be completed: {}", e.getMessage());
            throw new CommandException("User sign in could not be completed: ", e);
        }
        return router;
    }
}
