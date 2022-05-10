package IdGenerators;

/**
 * Interface for managing IDs
 */
public interface IdGenerator<T> {
    /**
     * Get the next available ID
     * @return
     *      the next available ID
     */
    T getId();
}
