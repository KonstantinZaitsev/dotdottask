package by.zaitsev.dotdottask.model.service.impl;

import by.zaitsev.dotdottask.exception.DaoException;
import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.dao.ProjectDao;
import by.zaitsev.dotdottask.model.dao.impl.ProjectDaoImpl;
import by.zaitsev.dotdottask.model.entity.Project;
import by.zaitsev.dotdottask.model.entity.Task;
import by.zaitsev.dotdottask.model.entity.User;
import by.zaitsev.dotdottask.model.service.ProjectService;
import by.zaitsev.dotdottask.model.service.TaskService;
import by.zaitsev.dotdottask.model.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * ProjectServiceImpl is the main implementation of the {@link ProjectService} interface.
 *
 * @author Konstantin Zaitsev
 */
public class ProjectServiceImpl implements ProjectService {
    private static final Logger logger = LogManager.getLogger(ProjectServiceImpl.class);
    private static ProjectServiceImpl instance;
    private final ProjectDao projectDao = ProjectDaoImpl.getInstance();
    private final TaskService taskService = TaskServiceImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    private ProjectServiceImpl() {
    }

    /**
     * @return a single instance of the ProjectServiceImpl class.
     */
    public static ProjectServiceImpl getInstance() {
        if (instance == null) {
            instance = new ProjectServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<Project> findEntityById(long id) throws ServiceException {
        Optional<Project> optionalProject;
        try {
            optionalProject = projectDao.findEntityById(id);
            if (optionalProject.isPresent()) {
                List<Task> taskList = taskService.findAllTasksByProjectId(id);
                Project project = optionalProject.get();
                List<User> userList = userService.findAllAssignedUsersByProjectId(project.getId());
                project.setAssignedUserList(userList);
                project.setTaskList(taskList);
                optionalProject = Optional.of(project);
            }
            logger.log(Level.DEBUG, "findEntityById(long id) method was completed successfully. " +
                    "Project with id {} " + (optionalProject.isPresent() ? "was found" : "don't exist"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find project by id. Dao access error: {}", e.getMessage());
            throw new ServiceException("Unable to find project by id. Dao access error: ", e);
        }
        return optionalProject;
    }

    @Override
    public List<Project> findAllEntities() throws ServiceException {
        List<Project> projectList;
        try {
            projectList = projectDao.findAllEntities();
            for (Project project : projectList) {
                List<Task> taskList = taskService.findAllTasksByProjectId(project.getId());
                List<User> userList = userService.findAllAssignedUsersByProjectId(project.getId());
                project.setAssignedUserList(userList);
                project.setTaskList(taskList);
            }
            logger.log(Level.DEBUG, "findAllEntities() method was completed successfully");
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find all projects. Dao access error: {}", e.getMessage());
            throw new ServiceException("Unable to find all projects. Dao access error: ", e);
        }
        return projectList;
    }

    @Override
    public long insertNewEntity(Project entity) throws ServiceException {
        long id;
        try {
            id = projectDao.insertNewEntity(entity);
            logger.log(Level.DEBUG, "insertNewEntity(Project entity) method was completed successfully. " +
                    "Project " + (id != 0 ? "with id {} was added" : "wasn't added"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to insert new project. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to insert new project. Dao access error: ", e);
        }
        return id;
    }

    @Override
    public boolean deleteEntityById(long id) throws ServiceException {
        boolean isDeleted;
        try {
            isDeleted = projectDao.deleteEntityById(id);
            logger.log(Level.DEBUG, "deleteEntityById(long id) method was completed successfully. " +
                    (isDeleted ? "Project was deleted" : "Project wasn't deleted"));
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to delete project from database. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to delete project from database. Dao access error: ", e);
        }
        return isDeleted;
    }

    @Override
    public List<Project> findAllUserOwnProjectsById(long id) throws ServiceException {
        List<Project> projectList;
        try {
            projectList = projectDao.findAllUserOwnProjectsById(id);
            for (Project project : projectList) {
                List<Task> taskList = taskService.findAllTasksByProjectId(project.getId());
                List<User> userList = userService.findAllAssignedUsersByProjectId(project.getId());
                project.setAssignedUserList(userList);
                project.setTaskList(taskList);
            }
            logger.log(Level.DEBUG, "findAllUserOwnProjectsById(long id) method was completed successfully");
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find user's own projects. Dao access error: {}", e.getMessage());
            throw new ServiceException("Unable to find user's own projects. Dao access error: ", e);
        }
        return projectList;
    }

    @Override
    public List<Project> findAllUserInvitedProjectsById(long id) throws ServiceException {
        List<Project> projectList;
        try {
            projectList = projectDao.findAllUserInvitedProjectsById(id);
            for (Project project : projectList) {
                List<Task> taskList = taskService.findAllTasksByProjectId(project.getId());
                List<User> userList = userService.findAllAssignedUsersByProjectId(project.getId());
                project.setAssignedUserList(userList);
                project.setTaskList(taskList);
            }
            logger.log(Level.DEBUG, "findAllUserInvitedProjectsById(long id) method was completed " +
                    "successfully");
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find user's invited projects. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to find user's invited projects. Dao access error: ", e);
        }
        return projectList;
    }

    @Override
    public boolean updateProjectTitleById(long id, String title) throws ServiceException {
        boolean isUpdated;
        try {
            isUpdated = projectDao.updateProjectTitleById(id, title);
            logger.log(Level.DEBUG, "updateProjectTitleById(long id, String title) method was completed " +
                    "successfully. Project with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update project title by id. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to update project title by id. Dao access error: ", e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateProjectDescriptionById(long id, String description) throws ServiceException {
        boolean isUpdated;
        try {
            isUpdated = projectDao.updateProjectDescriptionById(id, description);
            logger.log(Level.DEBUG, "updateProjectDescriptionById(long id, String description) method was " +
                    "completed successfully. Project with id {} " + (isUpdated ? "was updated" : "wasn't updated"), id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update project description by id. Dao access error: {}",
                    e.getMessage());
            throw new ServiceException("Unable to update project description by id. Dao access error: ", e);
        }
        return isUpdated;
    }
}
