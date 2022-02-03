package by.zaitsev.dotdottask.model.service.impl;

import by.zaitsev.dotdottask.exception.DaoException;
import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.dao.TagDao;
import by.zaitsev.dotdottask.model.dao.impl.TagDaoImpl;
import by.zaitsev.dotdottask.model.entity.Tag;
import by.zaitsev.dotdottask.model.service.TagService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * TagServiceImpl is the main implementation of the {@link TagServiceImpl} interface.
 *
 * @author Konstantin Zaitsev
 */
public class TagServiceImpl implements TagService {
    private static final Logger logger = LogManager.getLogger(TagServiceImpl.class);
    private static TagServiceImpl instance;
    private final TagDao tagDao = TagDaoImpl.getInstance();

    private TagServiceImpl() {
    }

    /**
     * @return a single instance of the TagServiceImpl class.
     */
    public static TagServiceImpl getInstance() {
        if (instance == null) {
            instance = new TagServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<Tag> findEntityById(long id) throws ServiceException {
        Optional<Tag> optionalTag;
        try {
            optionalTag = tagDao.findEntityById(id);
            logger.log(Level.DEBUG, "findEntityById(long id) method was completed successfully. " +
                    "Tag with id {} " + (optionalTag.isPresent() ? "was found" : "don't exist"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find tag by id. Dao access error: {}", e.getMessage());
            throw new ServiceException("Unable to find tag by id. Dao access error: ", e);
        }
        return optionalTag;
    }

    @Override
    public List<Tag> findAllEntities() throws ServiceException {
        List<Tag> tagList;
        try {
            tagList = tagDao.findAllEntities();
            logger.log(Level.DEBUG, "findAllEntities() method was completed successfully");
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find all tags. Dao access error: {}", e.getMessage());
            throw new ServiceException("Unable to find all tags. Dao access error: ", e);
        }
        return tagList;
    }

    @Override
    public long insertNewEntity(Tag entity) throws ServiceException {
        long id;
        try {
            id = tagDao.insertNewEntity(entity);
            logger.log(Level.DEBUG, "insertNewEntity(Project entity) method was completed successfully. " +
                    "Tag " + (id != 0 ? "with id {} was added" : "wasn't added"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to insert new tag. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to insert new tag. Dao access error: ", e);
        }
        return id;
    }

    @Override
    public boolean deleteEntityById(long id) throws ServiceException {
        boolean isDeleted;
        try {
            isDeleted = tagDao.deleteEntityById(id);
            logger.log(Level.DEBUG, "deleteEntityById(long id) method was completed successfully. " +
                    (isDeleted ? "Tag was deleted" : "Tag wasn't deleted"));
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to delete tag from database. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to delete tag from database. Dao access error: ", e);
        }
        return isDeleted;
    }
}
