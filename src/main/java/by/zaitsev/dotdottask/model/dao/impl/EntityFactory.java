package by.zaitsev.dotdottask.model.dao.impl;

import by.zaitsev.dotdottask.model.entity.AbstractEntity;
import by.zaitsev.dotdottask.model.entity.Project;
import by.zaitsev.dotdottask.model.entity.Tag;
import by.zaitsev.dotdottask.model.entity.Task;
import by.zaitsev.dotdottask.model.entity.User;
import by.zaitsev.dotdottask.util.ImageEncoder;
import by.zaitsev.dotdottask.util.TableColumn;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * EntityFactory is an enum for creating entity classes extending {@link AbstractEntity}.
 *
 * @author Konstantin Zaitsev
 */
enum EntityFactory {
    USER,
    PROJECT,
    TASK,
    TAG;

    /**
     * @param resultSet is the {@link ResultSet} received after the sql query.
     * @return an entity created from the {@link ResultSet}.
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is
     * called on a closed result set.
     */
    AbstractEntity build(ResultSet resultSet) throws SQLException {
        return switch (this) {
            case USER -> {
                var user = new User();
                var imageEncoder = ImageEncoder.getInstance();
                user.setId(resultSet.getLong(TableColumn.Users.USER_ID));
                user.setEmail(resultSet.getString(TableColumn.Users.EMAIL));
                user.setName(resultSet.getString(TableColumn.Users.NAME));
                user.setSurname(resultSet.getString(TableColumn.Users.SURNAME));
                user.setImage(imageEncoder.encodeImage(resultSet.getBytes(TableColumn.Users.IMAGE)));
                user.setUserRole(User.UserRole.USER);
                yield user;
            }
            case PROJECT -> {
                var project = new Project();
                project.setId(resultSet.getLong(TableColumn.Projects.PROJECT_ID));
                project.setOwnerId(resultSet.getLong(TableColumn.Projects.OWNER_ID));
                project.setTitle(resultSet.getString(TableColumn.Projects.TITLE));
                project.setColor(resultSet.getString(TableColumn.Projects.COLOR));
                project.setDescription(resultSet.getString(TableColumn.Projects.DESCRIPTION));
                yield project;
            }
            case TASK -> {
                var task = new Task();
                task.setId(resultSet.getLong(TableColumn.Tasks.TASK_ID));
                task.setProjectId(resultSet.getLong(TableColumn.Tasks.PROJECT_ID));
                task.setTitle(resultSet.getString(TableColumn.Tasks.TITLE));
                task.setDescription(resultSet.getString(TableColumn.Tasks.DESCRIPTION));
                task.setCreationDate(resultSet.getTimestamp(TableColumn.Tasks.CREATION_DATE));
                task.setDeadline(resultSet.getTimestamp(TableColumn.Tasks.DEADLINE));
                task.setDone(resultSet.getBoolean(TableColumn.Tasks.IS_DONE));
                task.setAssignedUserId(resultSet.getLong(TableColumn.Tasks.ASSIGNED_USER_ID));
                yield task;
            }
            case TAG -> {
                var tag = new Tag();
                tag.setId(resultSet.getLong(TableColumn.Tags.TAG_ID));
                tag.setTaskId(resultSet.getLong(TableColumn.Tags.USER_ID));
                tag.setName(resultSet.getString(TableColumn.Tags.NAME));
                tag.setColor(resultSet.getString(TableColumn.Tags.COLOR));
                yield tag;
            }
        };
    }
}
