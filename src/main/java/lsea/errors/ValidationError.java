package lsea.errors;

/**
 * An error class representing a validation error, which occurs when a request fails a validation check.
 */
public class ValidationError extends HttpBaseError {

    /**
     * Creates a new instance of the ValidationError class with a message.
     *
     * @param message The message describing the validation error.
     */
    public ValidationError(String message) {
        super(403, "A validation error occured: " + message);
    }
}
