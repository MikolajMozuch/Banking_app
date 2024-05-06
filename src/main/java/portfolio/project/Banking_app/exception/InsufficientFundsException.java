package portfolio.project.Banking_app.exception;

/**
 * Exception thrown to indicate that a transaction cannot be completed due to insufficient funds in the sender's account.
 */
public class InsufficientFundsException extends RuntimeException {

    /**
     * Constructs a new InsufficientFundsException with the specified detail message.
     *
     * @param message the detail message.
     */
    public InsufficientFundsException(String message) {
        super(message);
    }
}
