package Domain.Validators;

/**
 * Exception used in signaling problems in the Domain.
 */
public class DomainException extends RuntimeException{

    /**
     * Constructor for the DomainException.
     * @param message
     *      exception message.
     */
    public DomainException(String message){
        super(message);
    }

    /**
     * Constructor for the DomainException.
     * @param message
     *      exception message.
     * @param cause
     *      exception cause.
     */
    public DomainException(String message, Throwable cause){
        super(message, cause);
    }

    /**
     * Constructor for the DomainException.
     * @param cause
     *      exception cause.
     */
    public DomainException(Throwable cause){
        super(cause);
    }
}
