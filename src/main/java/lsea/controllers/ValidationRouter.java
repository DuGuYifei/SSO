package lsea.controllers;

import lsea.errors.ValidationError;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* Requirement 2.10 */
/**
 * This class provides utility methods for validating objects using the Bean Validation API.
 */
public abstract class ValidationRouter {

    /** The default validator factory. */
    static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    /** The validator instance to use for validating objects. */
    static final private Validator validator = factory.getValidator();

    /**
     * Validates the given object.
     *
     * @param object the object to validate
     * @throws ValidationError if the object fails validation
     */
    static public <T> void validate(T object) throws ValidationError {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            List<String> errors = new ArrayList<>();
            violations.forEach(violation -> errors.add(
                    violation.getPropertyPath() + " " + violation.getMessage()
            ));
            String joined = String.join(", ", errors);
            throw new ValidationError(joined);
        }
    }
}
