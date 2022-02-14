package by.zaitsev.dotdottask.util.validator;

/**
 * ProjectValidator is the class used to validate the project entity classes.
 *
 * @author Konstantin Zaitsev
 */
public class ProjectValidator {
    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MAX_DESCRIPTION_LENGTH = 500;
    public static ProjectValidator instance;

    private ProjectValidator() {
    }

    /**
     * @return a single instance of the ProjectValidator class.
     */
    public static ProjectValidator getInstance() {
        if (instance == null) {
            instance = new ProjectValidator();
        }
        return instance;
    }

    /**
     * @param title title to validate.
     * @return true if the title is valid, false if not.
     */
    public boolean isTitleValid(String title) {
        return title != null && title.length() <= MAX_TITLE_LENGTH;
    }

    /**
     * @param description description to validate.
     * @return true if the description is valid, false if not.
     */
    public boolean isDescriptionValid(String description) {
        return description != null && description.length() <= MAX_DESCRIPTION_LENGTH;
    }
}
