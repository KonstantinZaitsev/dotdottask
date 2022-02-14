package by.zaitsev.dotdottask.controller.command.impl.user.task;

import by.zaitsev.dotdottask.controller.Router;
import by.zaitsev.dotdottask.controller.command.Command;
import by.zaitsev.dotdottask.exception.CommandException;
import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.entity.Project;
import by.zaitsev.dotdottask.model.entity.Task;
import by.zaitsev.dotdottask.model.service.impl.TaskServiceImpl;
import by.zaitsev.dotdottask.util.AttributeName;
import by.zaitsev.dotdottask.util.PagePath;
import by.zaitsev.dotdottask.util.ParameterName;
import by.zaitsev.dotdottask.util.validator.TaskValidator;
import by.zaitsev.dotdottask.util.validator.ValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * EditTaskDescriptionCommand is used to edit task description.
 *
 * @author Konstantin Zaitsev
 */
public class EditTaskDescriptionCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditTaskDescriptionCommand.class);

    @Override
    @SuppressWarnings(value = "unchecked")
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        var ownProjects = (List<Project>) session.getAttribute(AttributeName.OWN_PROJECTS);
        String description = request.getParameter(ParameterName.DESCRIPTION);
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
            logger.log(Level.ERROR, "Cannot update task description, there are no project with id {}",
                    projectId);
            throw new CommandException("Cannot update task description, there are no project with id " + projectId);
        }
        Task task;
        if (optionalTask.isPresent()) {
            task = optionalTask.get();
        } else {
            logger.log(Level.ERROR, "Cannot update task description, there are no task with id {}", taskId);
            throw new CommandException("Cannot update task description, there are no task with id " + taskId);
        }
        var taskValidator = TaskValidator.getInstance();
        try {
            if (taskValidator.isDescriptionValid(description)) {
                TaskServiceImpl.getInstance().updateTaskDescriptionById(taskId, description);
                task.setDescription(description);
                logger.log(Level.DEBUG, "execute(HttpServletRequest request) method was completed " +
                        "successfully. Forwarded to catalog page");
            } else {
                request.setAttribute(AttributeName.UPDATE_TASK_DESCRIPTION_RESULT, ValidationResult.INVALID);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Task cannot be updated: {}", e.getMessage());
            throw new CommandException("Task cannot be updated: ", e);
        }
        var router = new Router();
        router.setPagePath(PagePath.CATALOG_PAGE);
        return router;
    }
}
