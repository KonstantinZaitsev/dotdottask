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

import java.util.List;
import java.util.Optional;

public class EditProjectTitleCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditProjectTitleCommand.class);

    @Override
    @SuppressWarnings(value = "unchecked")
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        var ownProjects = (List<Project>) session.getAttribute(AttributeName.OWN_PROJECTS);
        String title = request.getParameter(ParameterName.TITLE);
        var projectId = Long.parseLong(request.getParameter(ParameterName.ID));
        Optional<Project> optionalProject = ownProjects
                .stream()
                .filter(value -> value.getId() == projectId)
                .findFirst();
        Project project;
        if (optionalProject.isPresent()) {
            project = optionalProject.get();
        } else {
            logger.log(Level.ERROR, "Cannot update project title, there are no project with id {}", projectId);
            throw new CommandException("Cannot update project title, there are no project with id " + projectId);
        }
        var projectValidator = ProjectValidator.getInstance();
        try {
            if (projectValidator.isTitleValid(title)) {
                ProjectServiceImpl.getInstance().updateProjectTitleById(projectId, title);
                project.setTitle(title);
                logger.log(Level.DEBUG, "execute(HttpServletRequest request) method was completed " +
                        "successfully. Forwarded to catalog page");
            } else {
                request.setAttribute(AttributeName.UPDATE_TITLE_RESULT, ValidationResult.INVALID);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Project cannot be updated: {}", e.getMessage());
            throw new CommandException("Project cannot be updated: ", e);
        }
        var router = new Router();
        router.setPagePath(PagePath.CATALOG_PAGE);
        return router;
    }
}
