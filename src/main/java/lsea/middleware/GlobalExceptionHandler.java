package lsea.middleware;

import lsea.errors.HttpBaseError;
import lsea.utils.ErrorResult;

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
    public ResponseEntity<ErrorResult> handleException(HttpBaseError error) {
        ErrorResult result = ErrorResult.builder()
                .message(error.e.getMessage())
                .status(error.statusCode)
                .build();

        return ResponseEntity.status(error.statusCode).body(result);
    }

    /**
     * This method handles unknown exceptions and returns an HTTP response with a
     * 422 status code and a generic error message.
     * 
     * @param e The exception
     * @return A ResponseEntity object with the 422 status code and a generic error
     *         message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResult> handleUnknownException(Exception e) {
        System.out.println("And error occured to a user:");
        System.out.println(e.getMessage());
        System.out.println("Here is the stack trace:");
        e.printStackTrace();
        ErrorResult result = ErrorResult.builder()
                .message("Something went wrong")
                .status(422)
                .build();

        return ResponseEntity.status(422).body(result);
    }
}
