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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * DeleteTagCommand is used to delete tag.
 *
 * @author Konstantin Zaitsev
 */
public class DeleteTagCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteTagCommand.class);

    @Override
    @SuppressWarnings(value = "unchecked")
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        var ownProjects = (List<Project>) session.getAttribute(AttributeName.OWN_PROJECTS);
        var projectId = Long.parseLong(request.getParameter(ParameterName.PROJECT_ID));
        var taskId = Long.parseLong(request.getParameter(ParameterName.TASK_ID));
        var tagId = Long.parseLong(request.getParameter(ParameterName.TAG_ID));
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
            logger.log(Level.ERROR, "Cannot delete tag, there are no project with id {}", projectId);
            throw new CommandException("Cannot delete tag, there are no project with id " + projectId);
        }
        Optional<Tag> optionalTag;
        Task task;
        if (optionalTask.isPresent()) {
            optionalTag = optionalTask.get().getTagList()
                    .stream()
                    .filter(value -> value.getId() == tagId)
                    .findFirst();
            task = optionalTask.get();
        } else {
            logger.log(Level.ERROR, "Cannot delete tag, there are no task with id {}", taskId);
            throw new CommandException("Cannot delete tag, there are no task with id " + taskId);
        }
        Tag tag;
        if (optionalTag.isPresent()) {
            tag = optionalTag.get();
        } else {
            logger.log(Level.ERROR, "Cannot delete tag, there are no tag with id {}", tagId);
            throw new CommandException("Cannot delete tag, there are no tag with id " + tagId);
        }
        try {
            TagServiceImpl.getInstance().deleteEntityById(tag.getId());
            task.getTagList().remove(tag);
            logger.log(Level.DEBUG, "execute(HttpServletRequest request) method was completed " +
                    "successfully. Forwarded to catalog page");
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Tag cannot be deleted: {}", e.getMessage());
            throw new CommandException("Tag cannot be deleted: ", e);
        }
        var router = new Router();
        router.setPagePath(PagePath.CATALOG_PAGE);
        return router;
    }
}
