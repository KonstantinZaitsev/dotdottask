package by.zaitsev.dotdottask.model.dao.impl;

import by.zaitsev.dotdottask.exception.DaoException;
import by.zaitsev.dotdottask.model.dao.TaskDao;
import by.zaitsev.dotdottask.model.entity.Task;
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
 * TaskDaoImpl the main implementation of the {@link TaskDao} interface.
 *
 * @author Konstantin Zaitsev
 */
public class TaskDaoImpl implements TaskDao {
    private static final Logger logger = LogManager.getLogger(TaskDaoImpl.class);
    private static TaskDaoImpl instance;
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private TaskDaoImpl() {
    }

    /**
     * @return a single instance of the TaskDaoImpl class.
     */
    public static TaskDaoImpl getInstance() {
        if (instance == null) {
            instance = new TaskDaoImpl();
        }
        return instance;
    }

    @Override
    public Optional<Task> findEntityById(long id) throws DaoException {
        Optional<Task> optionalTask = Optional.empty();
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(SqlQuery.Tasks.FIND_ENTITY_BY_ID)) {
            preparedStatement.setLong(ParameterIndex.FIRST, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                var task = (Task) EntityFactory.TASK.build(resultSet);
                optionalTask = Optional.of(task);
            }
            logger.log(Level.DEBUG, "findEntityById(long id) method was completed successfully. " +
                    "Task with id {} " + (optionalTask.isPresent() ? "was found" : "don't exist"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find task by id. Database access error: {}", e.getMessage());
            throw new DaoException("Unable to find task by id. Database access error: ", e);
        }
        return optionalTask;
    }

    @Override
    public List<Task> findAllEntities() throws DaoException {
        List<Task> taskList = new ArrayList<>();
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(SqlQuery.Tasks.FIND_ALL_ENTITIES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var task = (Task) EntityFactory.TASK.build(resultSet);
                taskList.add(task);
            }
            logger.log(Level.DEBUG, "findAllEntities() method was completed successfully");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find all tasks. Database access error: {}", e.getMessage());
            throw new DaoException("Unable to find all tasks. Database access error: ", e);
        }
        return taskList;
    }

    @Override
    public long insertNewEntity(Task entity) throws DaoException {
        long id = 0;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(SqlQuery.Tasks.INSERT_NEW_ENTITY,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(ParameterIndex.FIRST, entity.getProjectId());
            preparedStatement.setString(ParameterIndex.SECOND, entity.getTitle());
            preparedStatement.setString(ParameterIndex.THIRD, entity.getDescription());
            preparedStatement.setTimestamp(ParameterIndex.FOURTH, entity.getDeadline());
            preparedStatement.setLong(ParameterIndex.FIFTH, entity.getAssignedUserId());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(TableColumn.Tasks.TASK_ID);
            }
            logger.log(Level.DEBUG, "insertNewEntity(Task entity) method was completed successfully. " +
                    "Task " + (id != 0 ? "with id {} was added" : "wasn't added"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to insert new task. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to insert new task. Database access error: ", e);
        }
        return id;
    }

    @Override
    public boolean deleteEntityById(long id) throws DaoException {
        boolean isDeleted;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(SqlQuery.Tasks.DELETE_ENTITY_BY_ID)) {
            preparedStatement.setLong(ParameterIndex.FIRST, id);
            isDeleted = preparedStatement.execute();
            logger.log(Level.DEBUG, "deleteEntityById(long id) method was completed successfully. " +
                    (isDeleted ? "Task was deleted" : "Task wasn't deleted"));
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to delete task from database. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to delete task from database. Database access error: ", e);
        }
        return isDeleted;
    }

    @Override
    public List<Task> findAllTasksByProjectId(long id) throws DaoException {
        List<Task> taskList = new ArrayList<>();
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(
                     SqlQuery.Tasks.FIND_ALL_TASKS_BY_PROJECT_ID)) {
            preparedStatement.setLong(ParameterIndex.FIRST, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var task = (Task) EntityFactory.TASK.build(resultSet);
                taskList.add(task);
            }
            logger.log(Level.DEBUG, "findAllTasksByProjectId(long id) method was completed successfully");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find tasks by project id. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to find tasks by project id. Database access error: ", e);
        }
        return taskList;
    }
}
