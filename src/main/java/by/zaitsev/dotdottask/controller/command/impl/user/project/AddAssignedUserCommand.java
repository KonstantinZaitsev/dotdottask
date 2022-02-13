package by.zaitsev.dotdottask.controller.command.impl.user.project;

import by.zaitsev.dotdottask.controller.Router;
import by.zaitsev.dotdottask.controller.command.Command;
import by.zaitsev.dotdottask.exception.CommandException;
import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.entity.Project;
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

import java.util.List;
import java.util.Optional;

/**
 * AddAssignedUserCommand is used to add assigned user.
 *
 * @author Konstantin Zaitsev
 */
public class AddAssignedUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddAssignedUserCommand.class);

    @Override
    @SuppressWarnings(value = "unchecked")
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String email = request.getParameter(ParameterName.EMAIL);
        long projectId = Long.parseLong(request.getParameter(ParameterName.PROJECT_ID));
        var ownProjects = (List<Project>) session.getAttribute(AttributeName.OWN_PROJECTS);
        Optional<Project> optionalProject = ownProjects
                .stream()
                .filter(value -> value.getId() == projectId)
                .findFirst();
        Project project;
        if (optionalProject.isPresent()) {
            project = optionalProject.get();
        } else {
            logger.log(Level.ERROR, "Cannot add user, there are no project with id {}", projectId);
            throw new CommandException("Cannot add user, there are no project with id " + projectId);
        }
        var userValidator = UserValidator.getInstance();
        try {
            Optional<User> optionalUser = UserServiceImpl.getInstance().findUserByEmail(email);
            if (userValidator.isEmailValid(email) && optionalUser.isPresent()) {
                UserServiceImpl.getInstance().
                        insertAssignedUserByProjectId(project.getId(), optionalUser.get().getId());
                project.getAssignedUserList().add(optionalUser.get());
                logger.log(Level.DEBUG, "execute(HttpServletRequest request) method was completed " +
                        "successfully. Forwarded to catalog page");
            } else {
                request.setAttribute(AttributeName.ADD_ASSIGNED_USER_RESULT, ValidationResult.INVALID);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "User cannot be added: {}", e.getMessage());
            throw new CommandException("User cannot be added: ", e);
        }
        var router = new Router();
        router.setPagePath(PagePath.CATALOG_PAGE);
        return router;
    }
}
