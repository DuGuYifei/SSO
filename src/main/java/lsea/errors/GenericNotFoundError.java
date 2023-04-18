package lsea.errors;

/**
 * This class represents a custom HttpBaseError that is thrown when a generic
 * not found error occurs.
 * It extends the HttpBaseError abstract class.
 */
public class GenericNotFoundError extends HttpBaseError {

    /* Requirement 2.7 */
    /**
     * Constructs a new instance of GenericNotFoundError with the specified message.
     * 
     * @param message the error message to be displayed
     */
    public GenericNotFoundError(String message) {
        super(404, message);
    }

    /* Requirement 2.7 */
    /**
     * Constructs a new instance of GenericNotFoundError with the specified
     * exception.
     * 
     * @param e the exception that caused the error to occur
     */
    public GenericNotFoundError(Exception e) {
        super(404, e);
    }
}
