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
import java.sql.Timestamp;
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

    @Override
    public boolean updateTaskTitleById(long id, String title) throws DaoException {
        boolean isUpdated;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(
                     SqlQuery.Tasks.UPDATE_TASK_TITLE_BY_ID)) {
            preparedStatement.setString(ParameterIndex.FIRST, title);
            preparedStatement.setLong(ParameterIndex.SECOND, id);
            isUpdated = preparedStatement.execute();
            logger.log(Level.DEBUG, "updateTaskTitleById(long id, String title) method was completed " +
                    "successfully. Task with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update task title by id. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to update task title by id. Database access error: ", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateTaskDescriptionById(long id, String description) throws DaoException {
        boolean isUpdated;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(
                     SqlQuery.Tasks.UPDATE_TASk_DESCRIPTION_BY_ID)) {
            preparedStatement.setString(ParameterIndex.FIRST, description);
            preparedStatement.setLong(ParameterIndex.SECOND, id);
            isUpdated = preparedStatement.execute();
            logger.log(Level.DEBUG, "updateTaskDescriptionById(long id, String description) method was " +
                    "completed successfully. Task with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update task description by id. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to update task description by id. Database access error: ", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateTaskDeadlineById(long id, Timestamp deadline) throws DaoException {
        boolean isUpdated;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(
                     SqlQuery.Tasks.UPDATE_TASK_DEADLINE_BY_ID)) {
            preparedStatement.setTimestamp(ParameterIndex.FIRST, deadline);
            preparedStatement.setLong(ParameterIndex.SECOND, id);
            isUpdated = preparedStatement.execute();
            logger.log(Level.DEBUG, "updateTaskDeadlineById(long id, Timestamp deadline) method was " +
                    "completed successfully. Task with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update task deadline by id. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to update task deadline by id. Database access error: ", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateTaskIsDoneById(long id, boolean isDone) throws DaoException {
        boolean isUpdated;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(
                     SqlQuery.Tasks.UPDATE_TASK_IS_DONE_BY_ID)) {
            preparedStatement.setBoolean(ParameterIndex.FIRST, isDone);
            preparedStatement.setLong(ParameterIndex.SECOND, id);
            isUpdated = preparedStatement.execute();
            logger.log(Level.DEBUG, "updateTaskIsDoneById(long id, boolean isDone) method was completed " +
                    "successfully. Task with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update task is done status by id. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to update task is done status by id. Database access error: ", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateTaskAssignedUserIdById(long id, long assignedUserId) throws DaoException {
        boolean isUpdated;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(
                     SqlQuery.Tasks.UPDATE_TASK_ASSIGNED_USER_ID_BY_ID)) {
            preparedStatement.setLong(ParameterIndex.FIRST, assignedUserId);
            preparedStatement.setLong(ParameterIndex.SECOND, id);
            isUpdated = preparedStatement.execute();
            logger.log(Level.DEBUG, "updateTaskAssignedUserIdById(long id, long assignedUserId) method was " +
                    "completed successfully. Task with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update task assigned user id by id. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to update task assigned user id by id. Database access error: ", e);
        }
        return isUpdated;
    }
}
