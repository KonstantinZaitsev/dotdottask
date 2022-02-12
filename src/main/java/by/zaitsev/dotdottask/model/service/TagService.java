package by.zaitsev.dotdottask.model.service;

import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.dao.TagDao;
import by.zaitsev.dotdottask.model.entity.Tag;

import java.util.List;

/**
 * TagService is an interface implemented by a class that will access classes that implement the
 * {@link TagDao} interface.
 *
 * @author Konstantin Zaitsev
 */
public interface TagService extends BaseService<Tag> {
    /**
     * @param id task id to search tags.
     * @return all tags by task id in the database.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    List<Tag> findAllTagsByTaskId(long id) throws ServiceException;

    /**
     * @param id   tag id to update.
     * @param name new tag name.
     * @return true if the user has been updated, otherwise false.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    boolean updateTagNameById(long id, String name) throws ServiceException;
}
