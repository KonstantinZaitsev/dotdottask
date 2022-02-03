package by.zaitsev.dotdottask.model.dao.impl;

import by.zaitsev.dotdottask.exception.DaoException;
import by.zaitsev.dotdottask.model.dao.TagDao;
import by.zaitsev.dotdottask.model.entity.Tag;
import by.zaitsev.dotdottask.model.pool.ConnectionPool;
import by.zaitsev.dotdottask.util.ParameterIndex;
import by.zaitsev.dotdottask.util.SqlQuery;
import by.zaitsev.dotdottask.util.TableColumn;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * TagDaoImpl the main implementation of the {@link TagDao} interface.
 *
 * @author Konstantin Zaitsev
 */
public class TagDaoImpl implements TagDao {
    private static final Logger logger = LogManager.getLogger(TagDaoImpl.class);
    private static TagDaoImpl instance;
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private TagDaoImpl() {
    }

    /**
     * @return a single instance of the TagDaoImpl class.
     */
    public static TagDaoImpl getInstance() {
        if (instance == null) {
            instance = new TagDaoImpl();
        }
        return instance;
    }

    @Override
    public Optional<Tag> findEntityById(long id) throws DaoException {
        Optional<Tag> optionalTag = Optional.empty();
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(SqlQuery.Tags.FIND_ENTITY_BY_ID)) {
            preparedStatement.setLong(ParameterIndex.FIRST, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                var tag = (Tag) EntityFactory.TAG.build(resultSet);
                optionalTag = Optional.of(tag);
            }
            logger.log(Level.DEBUG, "findEntityById(long id) method was completed successfully. " +
                    "Tag with id {} " + (optionalTag.isPresent() ? "was found" : "don't exist"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find tag by id. Database access error: {}", e.getMessage());
            throw new DaoException("Unable to find tag by id. Database access error: ", e);
        }
        return optionalTag;
    }

    @Override
    public List<Tag> findAllEntities() throws DaoException {
        List<Tag> tagList = new ArrayList<>();
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(SqlQuery.Tags.FIND_ALL_ENTITIES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var tag = (Tag) EntityFactory.TAG.build(resultSet);
                tagList.add(tag);
            }
            logger.log(Level.DEBUG, "findAllEntities() method was completed successfully");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find all tags. Database access error: {}", e.getMessage());
            throw new DaoException("Unable to find all tags. Database access error: ", e);
        }
        return tagList;
    }

    @Override
    public long insertNewEntity(Tag entity) throws DaoException {
        long id = 0;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(SqlQuery.Tags.INSERT_NEW_ENTITY,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(ParameterIndex.FIRST, entity.getUserId());
            preparedStatement.setString(ParameterIndex.SECOND, entity.getName());
            preparedStatement.setString(ParameterIndex.THIRD, entity.getColor());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(TableColumn.Tags.TAG_ID);
            }
            logger.log(Level.DEBUG, "insertNewEntity(Task entity) method was completed successfully. " +
                    "Tag " + (id != 0 ? "with id {} was added" : "wasn't added"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to insert new tag. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to insert new tag. Database access error: ", e);
        }
        return id;
    }

    @Override
    public boolean deleteEntityById(long id) throws DaoException {
        boolean isDeleted;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(SqlQuery.Tags.DELETE_ENTITY_BY_ID)) {
            preparedStatement.setLong(ParameterIndex.FIRST, id);
            isDeleted = preparedStatement.execute();
            logger.log(Level.DEBUG, "deleteEntityById(long id) method was completed successfully. " +
                    (isDeleted ? "Tag was deleted" : "Tag wasn't deleted"));
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to delete tag from database. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to delete tag from database. Database access error: ", e);
        }
        return isDeleted;
    }
}
