package Domain.Validators;

/**
 * Interface for the validators.
 * @param <T>
 *     the given Validator type.
 */
public interface Validator <T> {
    /**
     * Validates the given entity.
     * @param entity
     *      matching entity, must not be null.
     * @throws ValidatorException
     *      if the given entity is not valid.
     */
    void validate(T entity) throws ValidatorException;

    Class<T> getType();
}
