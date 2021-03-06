package by.zaitsev.dotdottask.util.validator;

/**
 * TaskValidator is the class used to validate the task entity classes.
 *
 * @author Konstantin Zaitsev
 */
public class TaskValidator {
    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MAX_DESCRIPTION_LENGTH = 500;
    private static final String IS_DONE_REGEX = "true|false";
    private static TaskValidator instance;

    private TaskValidator() {
    }

    /**
     * @return a single instance of the TaskValidator class.
     */
    public static TaskValidator getInstance() {
        if (instance == null) {
            instance = new TaskValidator();
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

    /**
     * @param timestamp timestamp to validate.
     * @return true if the description is valid, false if not.
     */
    public boolean isDeadlineValid(String timestamp) {
        return timestamp != null;
    }

    /**
     * @param isDone is done status to validate.
     * @return true if the is done status is valid, false if not.
     */
    public boolean isIsDoneValid(String isDone) {
        return isDone != null && isDone.matches(IS_DONE_REGEX);
    }
}
