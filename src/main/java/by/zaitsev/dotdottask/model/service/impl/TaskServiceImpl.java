package by.zaitsev.dotdottask.model.service.impl;

import by.zaitsev.dotdottask.exception.DaoException;
import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.dao.TaskDao;
import by.zaitsev.dotdottask.model.dao.impl.TaskDaoImpl;
import by.zaitsev.dotdottask.model.entity.Task;
import by.zaitsev.dotdottask.model.service.TaskService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            logger.log(Level.DEBUG, "findAllTasksByProjectId(long id) method was completed successfully");
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find tasks by project id. Dao access error: {}", e.getMessage());
            throw new ServiceException("Unable to find tasks by project id. Dao access error: ", e);
        }
        return taskList;
    }
}
