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

        private Users() {
        }
    }

    private SqlQuery() {
    }
}
