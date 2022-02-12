package by.zaitsev.dotdottask.model.dao.impl;

import by.zaitsev.dotdottask.exception.DaoException;
import by.zaitsev.dotdottask.model.dao.ProjectDao;
import by.zaitsev.dotdottask.model.entity.Project;
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
 * ProjectDaoImpl the main implementation of the {@link ProjectDao} interface.
 *
 * @author Konstantin Zaitsev
 */
public class ProjectDaoImpl implements ProjectDao {
    private static final Logger logger = LogManager.getLogger(ProjectDaoImpl.class);
    private static ProjectDaoImpl instance;
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private ProjectDaoImpl() {
    }

    /**
     * @return a single instance of the ProjectDaoImpl class.
     */
    public static ProjectDaoImpl getInstance() {
        if (instance == null) {
            instance = new ProjectDaoImpl();
        }
        return instance;
    }

    @Override
    public Optional<Project> findEntityById(long id) throws DaoException {
        Optional<Project> optionalProject = Optional.empty();
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(SqlQuery.Projects.FIND_ENTITY_BY_ID)) {
            preparedStatement.setLong(ParameterIndex.FIRST, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                var project = (Project) EntityFactory.PROJECT.build(resultSet);
                optionalProject = Optional.of(project);
            }
            logger.log(Level.DEBUG, "findEntityById(long id) method was completed successfully. " +
                    "Project with id {} " + (optionalProject.isPresent() ? "was found" : "don't exist"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find project by id. Database access error: {}", e.getMessage());
            throw new DaoException("Unable to find project by id. Database access error: ", e);
        }
        return optionalProject;
    }

    @Override
    public List<Project> findAllEntities() throws DaoException {
        List<Project> projectList = new ArrayList<>();
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(SqlQuery.Projects.FIND_ALL_ENTITIES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var project = (Project) EntityFactory.PROJECT.build(resultSet);
                projectList.add(project);
            }
            logger.log(Level.DEBUG, "findAllEntities() method was completed successfully");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find all projects. Database access error: {}", e.getMessage());
            throw new DaoException("Unable to find all projects. Database access error: ", e);
        }
        return projectList;
    }

    @Override
    public long insertNewEntity(Project entity) throws DaoException {
        long id = 0;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(SqlQuery.Projects.INSERT_NEW_ENTITY,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(ParameterIndex.FIRST, entity.getOwnerId());
            preparedStatement.setString(ParameterIndex.SECOND, entity.getTitle());
            preparedStatement.setString(ParameterIndex.THIRD, entity.getDescription());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(TableColumn.Projects.PROJECT_ID);
            }
            logger.log(Level.DEBUG, "insertNewEntity(Project entity) method was completed successfully. " +
                    "Task " + (id != 0 ? "with id {} was added" : "wasn't added"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to insert new project. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to insert new project. Database access error: ", e);
        }
        return id;
    }

    @Override
    public boolean deleteEntityById(long id) throws DaoException {
        boolean isDeleted;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(
                     SqlQuery.Projects.DELETE_ENTITY_BY_ID)) {
            preparedStatement.setLong(ParameterIndex.FIRST, id);
            isDeleted = preparedStatement.execute();
            logger.log(Level.DEBUG, "deleteEntityById(long id) method was completed successfully. " +
                    (isDeleted ? "Project was deleted" : "Project wasn't deleted"));
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to delete project from database. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to delete project from database. Database access error: ", e);
        }
        return isDeleted;
    }

    @Override
    public List<Project> findAllUserOwnProjectsById(long id) throws DaoException {
        List<Project> projectList = new ArrayList<>();
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(
                     SqlQuery.Projects.FIND_ALL_USER_OWN_PROJECTS_BY_ID)) {
            preparedStatement.setLong(ParameterIndex.FIRST, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var project = (Project) EntityFactory.PROJECT.build(resultSet);
                projectList.add(project);
            }
            logger.log(Level.DEBUG, "findAllUserOwnProjectsById(long id) method was completed successfully");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find user's own projects. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to find user's own projects. Database access error: ", e);
        }
        return projectList;
    }

    @Override
    public List<Project> findAllUserInvitedProjectsById(long id) throws DaoException {
        List<Project> projectList = new ArrayList<>();
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(
                     SqlQuery.Projects.FIND_ALL_USER_INVITED_PROJECTS_BY_ID)) {
            preparedStatement.setLong(ParameterIndex.FIRST, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var project = (Project) EntityFactory.PROJECT.build(resultSet);
                projectList.add(project);
            }
            logger.log(Level.DEBUG, "findAllUserInvitedProjectsById(long id) method was completed " +
                    "successfully");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find user's invited projects. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to find user's invited projects. Database access error: ", e);
        }
        return projectList;
    }

    @Override
    public boolean updateProjectTitleById(long id, String title) throws DaoException {
        boolean isUpdated;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(
                     SqlQuery.Projects.UPDATE_PROJECT_TITLE_BY_ID)) {
            preparedStatement.setString(ParameterIndex.FIRST, title);
            preparedStatement.setLong(ParameterIndex.SECOND, id);
            isUpdated = preparedStatement.execute();
            logger.log(Level.DEBUG, "updateProjectTitleById(long id, String title) method was completed " +
                    "successfully. Project with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update project title by id. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to update project title by id. Database access error: ", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateProjectDescriptionById(long id, String description) throws DaoException {
        boolean isUpdated;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement(
                     SqlQuery.Projects.UPDATE_PROJECT_DESCRIPTION_BY_ID)) {
            preparedStatement.setString(ParameterIndex.FIRST, description);
            preparedStatement.setLong(ParameterIndex.SECOND, id);
            isUpdated = preparedStatement.execute();
            logger.log(Level.DEBUG, "updateProjectDescriptionById(long id, String description) method was " +
                    "completed successfully. Project with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update project description by id. Database access error: {}",
                    e.getMessage());
            throw new DaoException("Unable to update project description by id. Database access error: ", e);
        }
        return isUpdated;
    }
}
