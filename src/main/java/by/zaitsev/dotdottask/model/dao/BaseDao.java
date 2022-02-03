package by.zaitsev.dotdottask.model.dao;

import by.zaitsev.dotdottask.exception.DaoException;
import by.zaitsev.dotdottask.model.entity.AbstractEntity;

import java.util.List;
import java.util.Optional;

/**
 * BaseDao is the base interface for all Dao interfaces that provide database access.
 *
 * @param <T> determines which objects Dao will work with. The object must extend {@link AbstractEntity}.
 * @author Konstantin Zaitsev
 */
public interface BaseDao<T extends AbstractEntity> {
    /**
     * @param id entity id to search.
     * @return an entity wrapped in {@link Optional} if it exists in the database or empty {@link Optional} if it
     * does not exist in the database.
     * @throws DaoException if the request to database could not be handled.
     */
    Optional<T> findEntityById(long id) throws DaoException;

    /**
     * @return all entities in the database.
     * @throws DaoException if the request to database could not be handled.
     */
    List<T> findAllEntities() throws DaoException;

    /**
     * @param entity entity to be added to the database.
     * @return entity id if it was added, otherwise 0.
     * @throws DaoException if the request to database could not be handled.
     */
    long insertNewEntity(T entity) throws DaoException;

    /**
     * @param id entity to be deleted from the database.
     * @return true if the entity has been deleted, otherwise false.
     * @throws DaoException if the request to database could not be handled.
     */
    boolean deleteEntityById(long id) throws DaoException;
}
