package by.zaitsev.dotdottask.model.dao;

import by.zaitsev.dotdottask.exception.DaoException;
import by.zaitsev.dotdottask.model.entity.Tag;

import java.util.List;

/**
 * TagDao is an interface implemented by classes that will access the tags table.
 *
 * @author Kosntantin Zaitsev
 */
public interface TagDao extends BaseDao<Tag> {
    /**
     * @param id task id to search tags.
     * @return all tags by task id in the database.
     * @throws DaoException if the request to database could not be handled.
     */
    List<Tag> findAllTagsByTaskId(long id) throws DaoException;

    /**
     * @param id tag id to update.
     * @param name new project name.
     * @return true if the project has been updated, otherwise false.
     * @throws DaoException if the request to database could not be handled.
     */
    boolean updateTagNameById(long id, String name) throws DaoException;
}
