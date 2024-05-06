package portfolio.project.Banking_app.exception;

/**
 * Exception to indicate that a transaction operation cannot be completed.
 */
public class TransactionOperationException extends RuntimeException {

    /**
     * Constructs a new TransactionOperationException with the specified detail message.
     *
     * @param message the detail message.
     */
    public TransactionOperationException(String message) {
        super(message);
    }
}

