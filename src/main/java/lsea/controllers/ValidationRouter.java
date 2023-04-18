package lsea.controllers;

import lsea.errors.ValidationError;

import javax.validation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* Requirement 2.10 */
/**
 * This class provides utility methods for validating objects using the Bean
 * Validation API.
 */
public abstract class ValidationRouter {
    /* Requirement 2.6 */
    /** The default validator factory. */
    static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    /** The validator instance to use for validating objects. */
    static final private Validator validator = factory.getValidator();

    /* Requirement 2.6 */
    /**
     * Validates the given DTO.
     *
     * @param <T> the type of the DTO to validate
     * @param object the object to validate
     * @throws ValidationError if the object fails validation
     */
    static protected <T> void validate(T object) throws ValidationError {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            List<String> errors = new ArrayList<>();
            /* Requirement 2.3 */
            violations.forEach(violation -> errors.add(
                    violation.getPropertyPath() + " " + violation.getMessage()));
            String joined = String.join(", ", errors);
            throw new ValidationError(joined);
        }
    }
}
