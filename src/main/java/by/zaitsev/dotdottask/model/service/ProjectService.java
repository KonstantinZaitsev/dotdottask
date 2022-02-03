package by.zaitsev.dotdottask.model.service;

import by.zaitsev.dotdottask.model.dao.ProjectDao;
import by.zaitsev.dotdottask.model.entity.Project;

/**
 * ProjectService is an interface implemented by a class that will access classes that implement the
 * {@link ProjectDao} interface.
 *
 * @author Konstantin Zaitsev
 */
public interface ProjectService extends BaseService<Project> {
}
