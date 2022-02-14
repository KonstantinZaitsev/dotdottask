package by.zaitsev.dotdottask.util.validator;

/**
 * TagValidator is the class used to validate the tag entity classes.
 *
 * @author Konstantin Zaitsev
 */
public class TagValidator {
    private static final int MAX_NAME_LENGTH = 30;
    private static TagValidator instance;

    private TagValidator() {
    }

    /**
     * @return a single instance of the TagValidator class.
     */
    public static TagValidator getInstance() {
        if (instance == null) {
            instance = new TagValidator();
        }
        return instance;
    }

    /**
     * @param name name to validate.
     * @return true if the name is valid, false if not.
     */
    public boolean isNameValid(String name) {
        return name != null && name.length() <= MAX_NAME_LENGTH;
    }
}
