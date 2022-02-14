package by.zaitsev.dotdottask.util.validator;

/**
 * LocaleValidator is the class used to validate the locale.
 *
 * @author Konstantin Zaitsev
 */
public class LocaleValidator {
    private static final String ENGLISH_LOCALE = "en_US";
    private static final String RUSSIAN_LOCALE = "ru_RU";
    private static LocaleValidator instance;

    private LocaleValidator() {
    }

    /**
     * @return a single instance of the LocaleValidator class.
     */
    public static LocaleValidator getInstance() {
        if (instance == null) {
            instance = new LocaleValidator();
        }
        return instance;
    }

    /**
     * @param locale locale name to validate.
     * @return true if the locale is valid, false if not.
     */
    public boolean isLocaleValid(String locale) {
        return locale != null && (locale.matches(ENGLISH_LOCALE) || locale.matches(RUSSIAN_LOCALE));
    }
}
