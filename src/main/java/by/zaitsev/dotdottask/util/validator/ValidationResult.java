package by.zaitsev.dotdottask.util.validator;

import by.zaitsev.dotdottask.model.service.BaseService;

/**
 * ValidationResult is a class containing validation results as strings. Used in Service classes.
 *
 * @author Konstantin Zaitsev
 * @see BaseService
 */
public final class ValidationResult {
    public static final String VALID = "true";
    public static final String INVALID = "invalid";
    public static final String NOT_UNIQUE = "not_unique";

    private ValidationResult() {
    }
}
