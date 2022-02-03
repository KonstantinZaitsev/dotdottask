package by.zaitsev.dotdottask.model.service.impl;

import by.zaitsev.dotdottask.exception.DaoException;
import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.dao.ProjectDao;
import by.zaitsev.dotdottask.model.dao.impl.ProjectDaoImpl;
import by.zaitsev.dotdottask.model.entity.Project;
import by.zaitsev.dotdottask.model.service.ProjectService;
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
}
