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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * DeleteProjectCommand is used to delete project.
 *
 * @author Konstantin Zaitsev
 */
public class DeleteProjectCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteProjectCommand.class);

    @Override
    @SuppressWarnings(value = "unchecked")
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        var ownProjects = (List<Project>) session.getAttribute(AttributeName.OWN_PROJECTS);
        var projectId = Long.parseLong(request.getParameter(ParameterName.PROJECT_ID));
        Optional<Project> optionalProject = ownProjects
                .stream()
                .filter(value -> value.getId() == projectId)
                .findFirst();
        Project project;
        if (optionalProject.isPresent()) {
            project = optionalProject.get();
        } else {
            logger.log(Level.ERROR, "Cannot delete project, there are no project with id {}",
                    projectId);
            throw new CommandException("Cannot delete project, there are no project with id " + projectId);
        }
        try {
            ProjectServiceImpl.getInstance().deleteEntityById(project.getId());
            ownProjects.remove(project);
            logger.log(Level.DEBUG, "execute(HttpServletRequest request) method was completed " +
                    "successfully. Forwarded to catalog page");
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Project cannot be deleted: {}", e.getMessage());
            throw new CommandException("Project cannot be deleted: ", e);
        }
        var router = new Router();
        router.setPagePath(PagePath.CATALOG_PAGE);
        return router;
    }
}
