package by.zaitsev.dotdottask.controller.command.impl.user.tag;

import by.zaitsev.dotdottask.controller.Router;
import by.zaitsev.dotdottask.controller.command.Command;
import by.zaitsev.dotdottask.exception.CommandException;
import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.entity.Project;
import by.zaitsev.dotdottask.model.entity.Tag;
import by.zaitsev.dotdottask.model.entity.Task;
import by.zaitsev.dotdottask.model.service.impl.TagServiceImpl;
import by.zaitsev.dotdottask.util.AttributeName;
import by.zaitsev.dotdottask.util.PagePath;
import by.zaitsev.dotdottask.util.ParameterName;
import by.zaitsev.dotdottask.util.validator.TagValidator;
import by.zaitsev.dotdottask.util.validator.ValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * AddTagCommand is used to add tag.
 *
 * @author Konstantin Zaitsev
 */
public class AddTagCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddTagCommand.class);

    @Override
    @SuppressWarnings(value = "unchecked")
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String name = request.getParameter(ParameterName.NAME);
        var ownerId = Long.parseLong(request.getParameter(ParameterName.OWNER_ID));
        var ownProjects = (List<Project>) session.getAttribute(AttributeName.OWN_PROJECTS);
        var projectId = Long.parseLong(request.getParameter(ParameterName.PROJECT_ID));
        var taskId = Long.parseLong(request.getParameter(ParameterName.TASK_ID));
        Optional<Project> optionalProject = ownProjects
                .stream()
                .filter(value -> value.getId() == projectId)
                .findFirst();
        Optional<Task> optionalTask;
        if (optionalProject.isPresent()) {
            optionalTask = optionalProject.get().getTaskList()
                    .stream()
                    .filter(value -> value.getId() == taskId)
                    .findFirst();
        } else {
            logger.log(Level.ERROR, "Cannot add tag, there are no project with id {}", projectId);
            throw new CommandException("Cannot add tag, there are no project with id " + projectId);
        }
        Task task;
        if (optionalTask.isPresent()) {
            task = optionalTask.get();
        } else {
            logger.log(Level.ERROR, "Cannot add tag, there are no task with id {}", taskId);
            throw new CommandException("Cannot add tag, there are no task with id " + taskId);
        }
        var tagValidator = TagValidator.getInstance();
        try {
            if (tagValidator.isNameValid(name)) {
                var tag = new Tag();
                tag.setName(name);
                tag.setTaskId(task.getId());
                long id = TagServiceImpl.getInstance().insertNewEntity(tag);
                tag.setId(id);
                task.getTagList().add(tag);
                logger.log(Level.DEBUG, "execute(HttpServletRequest request) method was completed " +
                        "successfully. Forwarded to catalog page");
            } else {
                request.setAttribute(AttributeName.ADD_TAG_RESULT, ValidationResult.INVALID);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Tag cannot be added: {}", e.getMessage());
            throw new CommandException("Tag cannot be added: ", e);
        }
        var router = new Router();
        router.setPagePath(PagePath.CATALOG_PAGE);
        return router;
    }
}
