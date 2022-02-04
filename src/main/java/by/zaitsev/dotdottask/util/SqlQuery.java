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
                SELECT project_id, owner_id, title, color, description
                FROM projects
                WHERE project_id = ?""";
        public static final String FIND_ALL_ENTITIES = """
                SELECT project_id, owner_id, title, color, description
                FROM projects""";
        public static final String INSERT_NEW_ENTITY = """
                INSERT INTO projects(owner_id, title, color, description)
                VALUES (?, ?, ?, ?)""";
        public static final String DELETE_ENTITY_BY_ID = """
                DELETE
                FROM projects
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
                SELECT tag_id, user_id, name, color
                FROM tags
                WHERE tag_id = ?""";
        public static final String FIND_ALL_ENTITIES = """
                SELECT tag_id, user_id, name, color
                FROM tags""";
        public static final String INSERT_NEW_ENTITY = """
                INSERT INTO tags(user_id, name, color)
                VALUES (?, ?, ?)""";
        public static final String DELETE_ENTITY_BY_ID = """
                DELETE
                FROM tags
                WHERE tag_id = ?""";

        private Tags() {
        }
    }

    private SqlQuery() {
    }
}
