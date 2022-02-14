package by.zaitsev.dotdottask.model.service;

import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.entity.AbstractEntity;

import java.util.List;
import java.util.Optional;

/**
 * ServiceDao is the base interface for all Service interfaces that provide database access.
 *
 * @param <T> determines which objects Service will work with. The object must extend {@link AbstractEntity}.
 * @author Konstantin Zaitsev
 */
public interface BaseService<T extends AbstractEntity> {
    /**
     * @param id entity id to search.
     * @return an entity wrapped in {@link Optional} if it exists in the database or empty {@link Optional} if it
     * does not exist in the database.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    Optional<T> findEntityById(long id) throws ServiceException;

    /**
     * @return all entities in the database.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    List<T> findAllEntities() throws ServiceException;

    /**
     * @param entity entity to be added to the database.
     * @return entity id if it was added, otherwise 0.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    long insertNewEntity(T entity) throws ServiceException;

    /**
     * @param id entity to be deleted from the database.
     * @return true if the entity has been deleted, otherwise false.
     * @throws ServiceException if the request to Dao class could not be handled.
     */
    boolean deleteEntityById(long id) throws ServiceException;
}
