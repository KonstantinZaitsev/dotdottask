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
import by.zaitsev.dotdottask.util.validator.TaskValidator;
import by.zaitsev.dotdottask.util.validator.ValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * AddTaskCommand is used to add task.
 *
 * @author Konstantin Zaitsev
 */
public class AddTaskCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddTaskCommand.class);

    @Override
    @SuppressWarnings(value = "unchecked")
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String title = request.getParameter(ParameterName.TITLE);
        String description = request.getParameter(ParameterName.DESCRIPTION);
        String deadline = request.getParameter(ParameterName.DEADLINE);
        String assignedUserEmail = request.getParameter(ParameterName.ASSIGNED_USER_EMAIL);
        var projectId = Long.parseLong(request.getParameter(ParameterName.PROJECT_ID));
        var ownProjects = (List<Project>) session.getAttribute(AttributeName.OWN_PROJECTS);
        Optional<Project> optionalProject = ownProjects
                .stream()
                .filter(value -> value.getId() == projectId)
                .findFirst();
        Project project;
        if (optionalProject.isPresent()) {
            project = optionalProject.get();
        } else {
            logger.log(Level.ERROR, "Cannot add task, there are no project with id {}", projectId);
            throw new CommandException("Cannot add task, there are no project with id " + projectId);
        }
        var taskValidator = TaskValidator.getInstance();
        try {
            long assignedUserId;
            Optional<User> optionalUser = UserServiceImpl.getInstance().findUserByEmail(assignedUserEmail);
            if (taskValidator.isTitleValid(title) &&
                    taskValidator.isDescriptionValid(description) &&
                    taskValidator.isDeadlineValid(deadline) &&
                    optionalUser.isPresent()) {
                assignedUserId = optionalUser.get().getId();
                var task = new Task();
                task.setProjectId(project.getId());
                task.setTitle(title);
                task.setDescription(description);
                task.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
                task.setDeadline(Timestamp.valueOf(deadline));
                task.setDone(false);
                task.setAssignedUserId(assignedUserId);
                task.setTagList(new ArrayList<>());
                long id = TaskServiceImpl.getInstance().insertNewEntity(task);
                task.setId(id);
                project.getTaskList().add(task);
                logger.log(Level.DEBUG, "execute(HttpServletRequest request) method was completed " +
                        "successfully. Forwarded to catalog page");
            } else {
                request.setAttribute(AttributeName.ADD_TASK_RESULT, ValidationResult.INVALID);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Task cannot be added: {}", e.getMessage());
            throw new CommandException("Task cannot be added: ", e);
        }
        var router = new Router();
        router.setPagePath(PagePath.CATALOG_PAGE);
        return router;
    }
}
