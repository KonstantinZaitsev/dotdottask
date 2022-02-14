package by.zaitsev.dotdottask.util.validator;

/**
 * LocaleValidator is the class used to validate the user information.
 *
 * @author Konstantin Zaitsev
 */
public class UserValidator {
    private static final String EMAIL_REGEX = "^([\\p{Alpha}_-]+\\.)*[\\p{Alpha}_-]+@" +
            "[\\p{Lower}\\p{Digit}_-]+(\\.[\\p{Lower}\\p{Digit}_-]+)*\\.\\p{Lower}{2,6}$";
    private static final int MAX_EMAIL_SIZE = 50;
    private static final String PASSWORD_REGEX = "^\\p{Graph}{6,30}$";
    private static final String NAME_REGEX = "^\\p{Alpha}{2,20}$";
    private static UserValidator instance;

    private UserValidator() {
    }

    /**
     * @return a single instance of the UserValidator class.
     */
    public static UserValidator getInstance() {
        if (instance == null) {
            instance = new UserValidator();
        }
        return instance;
    }

    /**
     * @param email email to validate.
     * @return true if the email is valid, false if not.
     */
    public boolean isEmailValid(String email) {
        return email != null && email.length() <= MAX_EMAIL_SIZE && email.matches(EMAIL_REGEX);
    }

    /**
     * @param password password to validate.
     * @return true if the password is valid, false if not.
     */
    public boolean isPasswordValid(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    /**
     * @param name name to validate.
     * @return true if the name is valid, false if not.
     */
    public boolean isNameValid(String name) {
        return name != null && name.matches(NAME_REGEX);
    }

    /**
     * @param surname surname to validate.
     * @return true if the surname is valid, false if not.
     */
    public boolean isSurnameValid(String surname) {
        return isNameValid(surname);
    }
}
