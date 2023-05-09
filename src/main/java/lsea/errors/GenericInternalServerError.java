package lsea.errors;

/**
 * Represents a generic internal server error with a status code of 500.
 * This exception can be thrown to indicate that an unexpected error occurred
 * while processing a request,
 * and the server was unable to handle it properly.
 */
public class GenericInternalServerError extends HttpBaseError {

  /* Requirement 2.7 */
  /**
   * Constructs a new instance of the {@code GenericInternalServerError} class
   * with the specified error message.
   *
   * @param message a description of the error
   */
  public GenericInternalServerError(String message) {
    super(500, message);
  }

  /* Requirement 2.7 */
  /**
   * Constructs a new instance of the {@code GenericInternalServerError} class
   * with the specified exception.
   *
   * @param e the exception that caused the error
   */
  public GenericInternalServerError(Exception e) {
    super(500, e);
  }
}
