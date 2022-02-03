package by.zaitsev.dotdottask.model.service;

import by.zaitsev.dotdottask.model.dao.TaskDao;
import by.zaitsev.dotdottask.model.entity.Task;

/**
 * TaskService is an interface implemented by a class that will access classes that implement the
 * {@link TaskDao} interface.
 *
 * @author Konstantin Zaitsev
 */
public interface TaskService extends BaseService<Task> {
}
