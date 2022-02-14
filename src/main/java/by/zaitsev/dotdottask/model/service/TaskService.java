package by.zaitsev.dotdottask.model.service;

import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.dao.TaskDao;
import by.zaitsev.dotdottask.model.entity.Task;

import java.sql.Timestamp;
import java.util.List;

/**
 * TaskService is an interface implemented by a class that will access classes that implement the
 * {@link TaskDao} interface.
 *
 * @author Konstantin Zaitsev
 */
public interface TaskService extends BaseService<Task> {
    /**
     * @param id project id to search tasks.
     * @return all entities by project id in the database.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    List<Task> findAllTasksByProjectId(long id) throws ServiceException;

    /**
     * @param id    task id to update.
     * @param title new task title.
     * @return true if the user has been updated, otherwise false.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    boolean updateTaskTitleById(long id, String title) throws ServiceException;

    /**
     * @param id          task id to update.
     * @param description new task description.
     * @return true if the user has been updated, otherwise false.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    boolean updateTaskDescriptionById(long id, String description) throws ServiceException;

    /**
     * @param id       task id to update.
     * @param deadline new task deadline.
     * @return true if the user has been updated, otherwise false.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    boolean updateTaskDeadlineById(long id, Timestamp deadline) throws ServiceException;

    /**
     * @param id     task id to update.
     * @param isDone new task is done status.
     * @return true if the user has been updated, otherwise false.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    boolean updateTaskIsDoneById(long id, boolean isDone) throws ServiceException;

    /**
     * @param id             task id to update.
     * @param assignedUserId new task assigned user id.
     * @return true if the user has been updated, otherwise false.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    boolean updateTaskAssignedUserIdById(long id, long assignedUserId) throws ServiceException;
}
