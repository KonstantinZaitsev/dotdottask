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
}
