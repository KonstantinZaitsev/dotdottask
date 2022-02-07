package by.zaitsev.dotdottask.model.dao;

import by.zaitsev.dotdottask.exception.DaoException;
import by.zaitsev.dotdottask.model.entity.Task;

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
}
