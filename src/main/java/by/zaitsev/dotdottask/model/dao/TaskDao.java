package by.zaitsev.dotdottask.model.dao;

import by.zaitsev.dotdottask.exception.DaoException;
import by.zaitsev.dotdottask.model.entity.Task;

import java.sql.Timestamp;
import java.util.List;

/**
 * TaskDao is an interface implemented by classes that will access the tasks table.
 *
 * @author Kosntantin Zaitsev
 */
public interface TaskDao extends BaseDao<Task> {
    /**
     * @param id project id to search tasks.
     * @return all entities by project id in the database.
     * @throws DaoException if the request to database could not be handled.
     */
    List<Task> findAllTasksByProjectId(long id) throws DaoException;

    /**
     * @param id    task id to update.
     * @param title new task title.
     * @return true if the task has been updated, otherwise false.
     * @throws DaoException if the request to database could not be handled.
     */
    boolean updateTaskTitleById(long id, String title) throws DaoException;

    /**
     * @param id          task id to update.
     * @param description new task description.
     * @return true if the task has been updated, otherwise false.
     * @throws DaoException if the request to database could not be handled.
     */
    boolean updateTaskDescriptionById(long id, String description) throws DaoException;

    /**
     * @param id       task id to update.
     * @param deadline new task deadline.
     * @return true if the task has been updated, otherwise false.
     * @throws DaoException if the request to database could not be handled.
     */
    boolean updateTaskDeadlineById(long id, Timestamp deadline) throws DaoException;

    /**
     * @param id     task id to update.
     * @param isDone new task isDone status.
     * @return true if the task has been updated, otherwise false.
     * @throws DaoException if the request to database could not be handled.
     */
    boolean updateTaskIsDoneById(long id, boolean isDone) throws DaoException;

    /**
     * @param id             task id to update.
     * @param assignedUserId new task assigned user id.
     * @return true if the task has been updated, otherwise false.
     * @throws DaoException if the request to database could not be handled.
     */
    boolean updateTaskAssignedUserIdById(long id, long assignedUserId) throws DaoException;
}
