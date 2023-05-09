package lsea.errors;

/* Requirement 2.2 */
/**
 * This class represents a generic conflict error, which occurs when a resource
 * request conflicts with another resource.
 * It extends the HttpBaseError class.
 */
public class GenericConflictError extends HttpBaseError {

  /* Requirement 2.7 */
  /* Requirement 2.9 */
  /**
   * Constructs a new GenericConflictError object with the specified message.
   *
   * @param message the error message to display
   */
  public GenericConflictError(String message) {
    super(409, message);
  }

  /* Requirement 2.7 */
  /* Requirement 2.9 */
  /**
   * Constructs a new GenericConflictError object with the specified exception.
   *
   * @param e the exception that caused the error
   */
  public GenericConflictError(Exception e) {
    super(409, e);
  }
}
