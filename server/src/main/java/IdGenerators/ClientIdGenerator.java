package IdGenerators;

/**
 *Client-specific implementation of the IdGenerator interface.
 */
public class ClientIdGenerator implements IdGenerator<Long>{
    private static Long id;

    /**
     *Base constructor for the ClientIdGenerator class.
     */
    public ClientIdGenerator(){
        id = Integer.toUnsignedLong(0);
    }

    /**
     * Constructor for the ClientIdGenerator class.
     * @param lastId
     *      the last id found in the repository.
     */
    public ClientIdGenerator(Long lastId){
        id = lastId;
    }

    /**
     * Gets the next available ID.
     * @return
     *      the next available ID.
     */
    @Override
    public Long getId() {
        return ++id;
    }
}
