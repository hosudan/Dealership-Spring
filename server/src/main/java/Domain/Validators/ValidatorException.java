package Domain.Validators;

/**
 * Exception used to signal problems during validation
 */
public class ValidatorException extends DomainException {
    /**
     * Exception constructor.
     * @param message
     *      exception message.
     */
    public ValidatorException(String message){
        super(message);
    }

    /**
     * Exception constructor.
     * @param message
     *      exception message.
     * @param cause
     *      exception cause.
     */
    public ValidatorException(String message, Throwable cause){
        super(message, cause);
    }

    /**
     * Exception constructor.
     * @param cause
     *      exception cause.
     */
    public ValidatorException(Throwable cause){
        super(cause);
    }
}
