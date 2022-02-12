package by.zaitsev.dotdottask.util;

import by.zaitsev.dotdottask.model.dao.BaseDao;

/**
 * SqlQuery is a class containing sql queries for Dao classes. Each table is associated with the queries needed to
 * access it.
 *
 * @author Konstantin Zaitsev
 * @see BaseDao
 */
public final class SqlQuery {
    /**
     * Users is an inner class containing queries to access the users table.
     *
     * @author Konstantin Zaitsev
     * @see SqlQuery
     */
    public static final class Users {
        public static final String FIND_ENTITY_BY_ID = """
                SELECT user_id, email, encrypted_password, name, surname, image
                FROM users
                WHERE user_id = ?""";
        public static final String FIND_ALL_ENTITIES = """
                SELECT user_id, email, encrypted_password, name, surname, image
                FROM users""";
        public static final String DELETE_ENTITY_BY_ID = """
                DELETE
                FROM users
                WHERE user_id = ?""";
        public static final String INSERT_NEW_USER = """
                INSERT INTO users(email, encrypted_password, name, surname, image)
                VALUES (?, ?, ?, ?, ?)""";
        public static final String FIND_USER_BY_EMAIL = """
                SELECT user_id, email, encrypted_password, name, surname, image
                FROM users
                WHERE email = ?""";
        public static final String FIND_USER_BY_EMAIL_AND_PASSWORD = """
                SELECT user_id, email, encrypted_password, name, surname, image
                FROM users
                WHERE email = ?
                  AND encrypted_password = ?""";
        public static final String UPDATE_USER_NAME_BY_ID = """
                UPDATE users
                SET name = ?
                WHERE user_id = ?""";
        public static final String UPDATE_USER_SURNAME_BY_ID = """
                UPDATE users
                SET surname = ?
                WHERE user_id = ?""";
        public static final String UPDATE_USER_PASSWORD_BY_ID = """
                UPDATE users
                SET encrypted_password = ?
                WHERE user_id = ?""";
        public static final String UPDATE_USER_IMAGE_BY_ID = """
                UPDATE users
                SET image = ?
                WHERE user_id = ?""";
        public static final String UPDATE_USER_EMAIL_BY_ID = """
                UPDATE users
                SET email = ?
                WHERE user_id = ?""";

        private Users() {
        }
    }

    /**
     * Projects is an inner class containing queries to access the projects table.
     *
     * @author Konstantin Zaitsev
     * @see SqlQuery
     */
    public static final class Projects {
        public static final String FIND_ENTITY_BY_ID = """
                SELECT project_id, owner_id, title, description
                FROM projects
                WHERE project_id = ?""";
        public static final String FIND_ALL_ENTITIES = """
                SELECT project_id, owner_id, title, description
                FROM projects""";
        public static final String INSERT_NEW_ENTITY = """
                INSERT INTO projects(owner_id, title, description)
                VALUES (?, ?, ?, ?)""";
        public static final String DELETE_ENTITY_BY_ID = """
                DELETE
                FROM projects
                WHERE project_id = ?""";
        public static final String FIND_ALL_USER_OWN_PROJECTS_BY_ID = """
                SELECT project_id, owner_id, title, description
                FROM projects
                         LEFT JOIN users on owner_id = users.user_id
                WHERE user_id = ?""";
        public static final String FIND_ALL_USER_INVITED_PROJECTS_BY_ID = """
                SELECT projects.project_id, owner_id, title, description
                FROM projects
                         LEFT JOIN projects_users on projects.project_id = projects_users.project_id
                         LEFT JOIN users on projects_users.user_id = users.user_id
                WHERE users.user_id = ?""";
        public static final String UPDATE_PROJECT_TITLE_BY_ID = """
                UPDATE projects
                SET title = ?
                WHERE project_id = ?""";
        public static final String UPDATE_PROJECT_DESCRIPTION_BY_ID = """
                UPDATE projects
                SET description = ?
                WHERE project_id = ?""";

        private Projects() {
        }
    }

    /**
     * Tasks is an inner class containing queries to access the tasks table.
     *
     * @author Konstantin Zaitsev
     * @see SqlQuery
     */
    public static final class Tasks {
        public static final String FIND_ENTITY_BY_ID = """
                SELECT task_id, project_id, title, description, creation_date, deadline, is_done, assigned_user_id
                FROM tasks
                WHERE task_id = ?""";
        public static final String FIND_ALL_ENTITIES = """
                SELECT task_id, project_id, title, description, creation_date, deadline, is_done, assigned_user_id
                FROM tasks""";
        public static final String INSERT_NEW_ENTITY = """
                INSERT INTO tasks(project_id, title, description, deadline, assigned_user_id)
                VALUES (?, ?, ?, ?, ?)""";
        public static final String DELETE_ENTITY_BY_ID = """
                DELETE
                FROM tasks
                WHERE task_id = ?""";
        public static final String FIND_ALL_TASKS_BY_PROJECT_ID = """
                SELECT task_id, tasks.project_id, tasks.title, tasks.description, creation_date, deadline, is_done, assigned_user_id
                FROM tasks
                         LEFT JOIN projects ON tasks.project_id = projects.project_id
                WHERE projects.project_id = ?""";

        private Tasks() {
        }
    }

    /**
     * Tags is an inner class containing queries to access the tags table.
     *
     * @author Konstantin Zaitsev
     * @see SqlQuery
     */
    public static final class Tags {
        public static final String FIND_ENTITY_BY_ID = """
                SELECT tag_id, task_id, name
                FROM tags
                WHERE tag_id = ?""";
        public static final String FIND_ALL_ENTITIES = """
                SELECT tag_id, task_id, name
                FROM tags""";
        public static final String INSERT_NEW_ENTITY = """
                INSERT INTO tags(task_id, name)
                VALUES (?, ?, ?)""";
        public static final String DELETE_ENTITY_BY_ID = """
                DELETE
                FROM tags
                WHERE tag_id = ?""";
        public static final String FIND_ALL_TAGS_BY_TASK_ID = """
                SELECT tags.tag_id, tags.task_id, name
                FROM tags
                         LEFT JOIN tasks ON tags.task_id = tasks.task_id
                WHERE tasks.task_id = ?""";

        private Tags() {
        }
    }

    private SqlQuery() {
    }
}
