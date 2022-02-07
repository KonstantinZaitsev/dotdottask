package by.zaitsev.dotdottask.model.service;

import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.dao.TaskDao;
import by.zaitsev.dotdottask.model.entity.Task;

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
}
