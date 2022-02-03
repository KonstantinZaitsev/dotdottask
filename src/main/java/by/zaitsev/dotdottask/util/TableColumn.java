package by.zaitsev.dotdottask.util;

/**
 * TableColumn is a class containing the names of the columns of tables from the database. Each table is associated
 * with an inner class.
 *
 * @author Konstantin Zaitsev
 */
public final class TableColumn {
    /**
     * Users is an inner class containing the column names from the users table.
     *
     * @author Konstantin Zaitsev
     * @see TableColumn
     */
    public static final class Users {
        public static final String USER_ID = "user_id";
        public static final String EMAIL = "email";
        public static final String ENCRYPTED_PASSWORD = "encrypted_password";
        public static final String NAME = "name";
        public static final String SURNAME = "surname";
        public static final String IMAGE = "image";

        private Users() {
        }
    }

    /**
     * Projects is an inner class containing the column names from the projects table.
     *
     * @author Konstantin Zaitsev
     * @see TableColumn
     */
    public static final class Projects {
        public static final String PROJECT_ID = "project_id";
        public static final String OWNER_ID = "owner_id";
        public static final String TITLE = "title";
        public static final String COLOR = "color";
        public static final String DESCRIPTION = "description";

        private Projects() {
        }
    }

    /**
     * Tasks is an inner class containing the column names from the tasks table.
     *
     * @author Konstantin Zaitsev
     * @see TableColumn
     */
    public static final class Tasks {
        public static final String TASK_ID = "task_id";
        public static final String PROJECT_ID = "project_id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String CREATION_DATE = "creation_date";
        public static final String DEADLINE = "deadline";
        public static final String IS_DONE = "is_done";
        public static final String ASSIGNED_USER_ID = "assigned_user_id";

        private Tasks() {
        }
    }

    /**
     * Tags is an inner class containing the column names from the tags table.
     *
     * @author Konstantin Zaitsev
     * @see TableColumn
     */
    public static final class Tags {
        public static final String TAG_ID = "tag_id";
        public static final String USER_ID = "user_id";
        public static final String NAME = "name";
        public static final String COLOR = "color";
    }

    private TableColumn() {
    }
}
