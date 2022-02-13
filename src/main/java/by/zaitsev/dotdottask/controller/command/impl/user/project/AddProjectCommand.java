package by.zaitsev.dotdottask.controller.command.impl.user.project;

import by.zaitsev.dotdottask.controller.Router;
import by.zaitsev.dotdottask.controller.command.Command;
import by.zaitsev.dotdottask.exception.CommandException;
import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.entity.Project;
import by.zaitsev.dotdottask.model.service.impl.ProjectServiceImpl;
import by.zaitsev.dotdottask.util.AttributeName;
import by.zaitsev.dotdottask.util.PagePath;
import by.zaitsev.dotdottask.util.ParameterName;
import by.zaitsev.dotdottask.util.validator.ProjectValidator;
import by.zaitsev.dotdottask.util.validator.ValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * AddProjectCommand is used to add project.
 *
 * @author Konstantin Zaitsev
 */
public class AddProjectCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddProjectCommand.class);

    @Override
    @SuppressWarnings(value = "unchecked")
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String title = request.getParameter(ParameterName.TITLE);
        String description = request.getParameter(ParameterName.DESCRIPTION);
        var ownerId = Long.parseLong(request.getParameter(ParameterName.OWNER_ID));
        var ownProjects = (List<Project>) session.getAttribute(AttributeName.OWN_PROJECTS);
        var projectValidator = ProjectValidator.getInstance();
        try {
            if (projectValidator.isTitleValid(title) && projectValidator.isDescriptionValid(description)) {
                var project = new Project();
                project.setTitle(title);
                project.setDescription(description);
                project.setOwnerId(ownerId);
                project.setTaskList(new ArrayList<>());
                long id = ProjectServiceImpl.getInstance().insertNewEntity(project);
                project.setId(id);
                ownProjects.add(project);
                logger.log(Level.DEBUG, "execute(HttpServletRequest request) method was completed " +
                        "successfully. Forwarded to catalog page");
            } else {
                request.setAttribute(AttributeName.ADD_PROJECT_RESULT, ValidationResult.INVALID);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Project cannot be added: {}", e.getMessage());
            throw new CommandException("Project cannot be added: ", e);
        }
        var router = new Router();
        router.setPagePath(PagePath.CATALOG_PAGE);
        return router;
    }
}
