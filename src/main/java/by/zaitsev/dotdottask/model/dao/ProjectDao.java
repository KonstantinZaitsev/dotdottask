package by.zaitsev.dotdottask.model.dao;

import by.zaitsev.dotdottask.exception.DaoException;
import by.zaitsev.dotdottask.model.entity.Project;

import java.util.List;

/**
 * ProjectDao is an interface implemented by classes that will access the projects table.
 *
 * @author Kosntantin Zaitsev
 */
public interface ProjectDao extends BaseDao<Project> {
    /**
     * @param id user id to search projects.
     * @return all user's own projects in the database.
     * @throws DaoException if the request to database could not be handled.
     */
    List<Project> findAllUserOwnProjectsById(long id) throws DaoException;

    /**
     * @param id user id to search projects.
     * @return all user's invited projects in the database.
     * @throws DaoException if the request to database could not be handled.
     */
    List<Project> findAllUserInvitedProjectsById(long id) throws DaoException;

    /**
     * @param id    project id to update.
     * @param title new project title.
     * @return true if the project has been updated, otherwise false.
     * @throws DaoException if the request to database could not be handled.
     */
    boolean updateProjectTitleById(long id, String title) throws DaoException;

    /**
     * @param id          project id to update.
     * @param description new project description.
     * @return true if the project has been updated, otherwise false.
     * @throws DaoException if the request to database could not be handled.
     */
    boolean updateProjectDescriptionById(long id, String description) throws DaoException;
}
