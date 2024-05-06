package portfolio.project.Banking_app.exception;

/**
 * Exception thrown when an incorrect PIN is provided.
 */
public class IncorrectPinException extends RuntimeException {

    /**
     * Constructs a new IncorrectPinException with the specified detail message.
     *
     * @param message the detail message.
     */
    public IncorrectPinException(String message) {
        super(message);
    }
}
