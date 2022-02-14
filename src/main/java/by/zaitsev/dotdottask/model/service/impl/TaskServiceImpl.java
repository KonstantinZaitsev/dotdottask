package by.zaitsev.dotdottask.model.service.impl;

import by.zaitsev.dotdottask.exception.DaoException;
import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.dao.TaskDao;
import by.zaitsev.dotdottask.model.dao.impl.TaskDaoImpl;
import by.zaitsev.dotdottask.model.entity.Tag;
import by.zaitsev.dotdottask.model.entity.Task;
import by.zaitsev.dotdottask.model.service.TagService;
import by.zaitsev.dotdottask.model.service.TaskService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * TaskServiceImpl is the main implementation of the {@link TaskService} interface.
 *
 * @author Konstantin Zaitsev
 */
public class TaskServiceImpl implements TaskService {
    private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);
    private static TaskServiceImpl instance;
    private final TaskDao taskDao = TaskDaoImpl.getInstance();
    private final TagService tagService = TagServiceImpl.getInstance();

    private TaskServiceImpl() {
    }

    /**
     * @return a single instance of the TaskServiceImpl class.
     */
    public static TaskServiceImpl getInstance() {
        if (instance == null) {
            instance = new TaskServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<Task> findEntityById(long id) throws ServiceException {
        Optional<Task> optionalTask;
        try {
            optionalTask = taskDao.findEntityById(id);
            if (optionalTask.isPresent()) {
                List<Tag> tagList = tagService.findAllTagsByTaskId(id);
                Task task = optionalTask.get();
                task.setTagList(tagList);
                optionalTask = Optional.of(task);
            }
            logger.log(Level.DEBUG, "findEntityById(long id) method was completed successfully. " +
                    "Task with id {} " + (optionalTask.isPresent() ? "was found" : "don't exist"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find task by id. Dao access error: {}", e.getMessage());
            throw new ServiceException("Unable to find task by id. Dao access error: ", e);
        }
        return optionalTask;
    }

    @Override
    public List<Task> findAllEntities() throws ServiceException {
        List<Task> taskList;
        try {
            taskList = taskDao.findAllEntities();
            for (Task task : taskList) {
                List<Tag> tagList = tagService.findAllTagsByTaskId(task.getId());
                task.setTagList(tagList);
            }
            logger.log(Level.DEBUG, "findAllEntities() method was completed successfully");
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find all tasks. Dao access error: {}", e.getMessage());
            throw new ServiceException("Unable to find all tasks. Dao access error: ", e);
        }
        return taskList;
    }

    @Override
    public long insertNewEntity(Task entity) throws ServiceException {
        long id;
        try {
            id = taskDao.insertNewEntity(entity);
            logger.log(Level.DEBUG, "insertNewEntity(Task entity) method was completed successfully. " +
                    "Task " + (id != 0 ? "with id {} was added" : "wasn't added"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to insert new task. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to insert new task. Dao access error: ", e);
        }
        return id;
    }

    @Override
    public boolean deleteEntityById(long id) throws ServiceException {
        boolean isDeleted;
        try {
            isDeleted = taskDao.deleteEntityById(id);
            logger.log(Level.DEBUG, "deleteEntityById(long id) method was completed successfully. " +
                    (isDeleted ? "Task was deleted" : "Task wasn't deleted"));
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to delete task from database. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to delete task from database. Dao access error: ", e);
        }
        return isDeleted;
    }

    @Override
    public List<Task> findAllTasksByProjectId(long id) throws ServiceException {
        List<Task> taskList;
        try {
            taskList = taskDao.findAllTasksByProjectId(id);
            for (Task task : taskList) {
                List<Tag> tagList = tagService.findAllTagsByTaskId(task.getId());
                task.setTagList(tagList);
            }
            logger.log(Level.DEBUG, "findAllTasksByProjectId(long id) method was completed successfully");
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find tasks by project id. Dao access error: {}", e.getMessage());
            throw new ServiceException("Unable to find tasks by project id. Dao access error: ", e);
        }
        return taskList;
    }

    @Override
    public boolean updateTaskTitleById(long id, String title) throws ServiceException {
        boolean isUpdated;
        try {
            isUpdated = taskDao.updateTaskTitleById(id, title);
            logger.log(Level.DEBUG, "updateTaskTitleById(long id, String title) method was completed " +
                    "successfully. Task with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update task title by id. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to update task title by id. Dao access error: ", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateTaskDescriptionById(long id, String description) throws ServiceException {
        boolean isUpdated;
        try {
            isUpdated = taskDao.updateTaskDescriptionById(id, description);
            logger.log(Level.DEBUG, "updateTaskDescriptionById(long id, String description) method was " +
                    "completed successfully. Task with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update task description by id. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to update task description by id. Dao access error: ", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateTaskDeadlineById(long id, Timestamp deadline) throws ServiceException {
        boolean isUpdated;
        try {
            isUpdated = taskDao.updateTaskDeadlineById(id, deadline);
            logger.log(Level.DEBUG, "updateTaskDeadlineById(long id, Timestamp deadline) method was " +
                    "completed successfully. Task with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update task deadline by id. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to update task deadline by id. Dao access error: ", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateTaskIsDoneById(long id, boolean isDone) throws ServiceException {
        boolean isUpdated;
        try {
            isUpdated = taskDao.updateTaskIsDoneById(id, isDone);
            logger.log(Level.DEBUG, "updateTaskIsDoneById(long id, boolean isDone) method was completed " +
                    "successfully. Task with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update task is done status by id. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to update task is done status by id. Dao access error: ", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateTaskAssignedUserIdById(long id, long assignedUserId) throws ServiceException {
        boolean isUpdated;
        try {
            isUpdated = taskDao.updateTaskAssignedUserIdById(id, assignedUserId);
            logger.log(Level.DEBUG, "updateTaskAssignedUserIdById(long id, long assignedUserId) method was " +
                    "completed successfully. Task with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update task assigned user id by id. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to update task assigned user id by id. Dao access error: ", e);
        }
        return isUpdated;
    }
}
