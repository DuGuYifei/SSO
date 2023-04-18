package lsea.middleware;

import lsea.errors.HttpBaseError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class acts as a global exception handler for HTTP errors and unknown
 * exceptions.
 * It catches exceptions thrown by the controllers and returns an appropriate
 * response to the client.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * This method handles HttpBaseError exceptions and returns an HTTP response
     * with the appropriate status code and message.
     * 
     * @param error The HttpBaseError object representing the exception
     * @return A ResponseEntity object with the status code and error message
     */
    @ExceptionHandler(HttpBaseError.class)
    public ResponseEntity<String> handleException(HttpBaseError error) {
        return ResponseEntity.status(error.statusCode).body(error.e.getMessage());
    }

    /**
     * This method handles unknown exceptions and returns an HTTP response with a
     * 422 status code and a generic error message.
     * 
     * @param error The unknown exception object
     * @return A ResponseEntity object with the 422 status code and a generic error
     *         message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnknownException(Error error) {
        error.printStackTrace();
        return ResponseEntity.status(422).body("Something went wrong");
    }
}
