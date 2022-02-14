package by.zaitsev.dotdottask.controller.command.impl.user.task;

import by.zaitsev.dotdottask.controller.Router;
import by.zaitsev.dotdottask.controller.command.Command;
import by.zaitsev.dotdottask.exception.CommandException;
import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.entity.Project;
import by.zaitsev.dotdottask.model.entity.Task;
import by.zaitsev.dotdottask.model.entity.User;
import by.zaitsev.dotdottask.model.service.impl.TaskServiceImpl;
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
 * EditTaskAssignedUserIdCommand is used to edit task assigned user id.
 *
 * @author Konstantin Zaitsev
 */
public class EditTaskAssignedUserIdCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditTaskAssignedUserIdCommand.class);

    @Override
    @SuppressWarnings(value = "unchecked")
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        var ownProjects = (List<Project>) session.getAttribute(AttributeName.OWN_PROJECTS);
        String email = request.getParameter(ParameterName.ASSIGNED_USER_EMAIL);
        var projectId = Long.parseLong(request.getParameter(ParameterName.PROJECT_ID));
        var taskId = Long.parseLong(request.getParameter(ParameterName.TASK_ID));
        Optional<Project> optionalProject = ownProjects
                .stream()
                .filter(value -> value.getId() == projectId)
                .findFirst();
        Optional<Task> optionalTask;
        Project project;
        if (optionalProject.isPresent()) {
            optionalTask = optionalProject.get().getTaskList()
                    .stream()
                    .filter(value -> value.getId() == taskId)
                    .findFirst();
            project = optionalProject.get();
        } else {
            logger.log(Level.ERROR, "Cannot update task assigned user id, there are no project with id {}",
                    projectId);
            throw new CommandException("Cannot update task assigned user id, there are no project with id " + projectId);
        }
        Task task;
        if (optionalTask.isPresent()) {
            task = optionalTask.get();
        } else {
            logger.log(Level.ERROR, "Cannot update task assigned user id, there are no task with id {}",
                    taskId);
            throw new CommandException("Cannot update task assigned user id, there are no task with id " + taskId);
        }
        var userValidator = UserValidator.getInstance();
        try {
            Optional<User> user = UserServiceImpl.getInstance().findUserByEmail(email);
            if (userValidator.isEmailValid(email) &&
                    user.isPresent() &&
                    project.getAssignedUserList().contains(user.get())) {
                TaskServiceImpl.getInstance().updateTaskAssignedUserIdById(taskId, user.get().getId());
                task.setAssignedUserId(user.get().getId());
                logger.log(Level.DEBUG, "execute(HttpServletRequest request) method was completed " +
                        "successfully. Forwarded to catalog page");
            } else {
                request.setAttribute(AttributeName.UPDATE_TASK_ASSIGNED_USER_ID_RESULT, ValidationResult.INVALID);
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
