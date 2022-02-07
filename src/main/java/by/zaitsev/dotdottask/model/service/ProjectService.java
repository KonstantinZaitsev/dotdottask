package by.zaitsev.dotdottask.model.service;

import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.dao.ProjectDao;
import by.zaitsev.dotdottask.model.entity.Project;

import java.util.List;

/**
 * ProjectService is an interface implemented by a class that will access classes that implement the
 * {@link ProjectDao} interface.
 *
 * @author Konstantin Zaitsev
 */
public interface ProjectService extends BaseService<Project> {
    /**
     * @param id user id to search projects.
     * @return all user's own projects in the database.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    List<Project> findAllUserOwnProjectsById(long id) throws ServiceException;

    /**
     * @param id user id to search projects.
     * @return all user's invited projects in the database.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    List<Project> findAllUserInvitedProjectsById(long id) throws ServiceException;
}
