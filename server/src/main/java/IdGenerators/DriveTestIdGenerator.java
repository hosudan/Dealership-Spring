package IdGenerators;

/**
 * DriveTest-specific implementation of the IdGenerator interface.
 */
public class DriveTestIdGenerator implements IdGenerator<Long>{
    private static Long id;

    /**
     * Base constructor of the DriveTestIdGenerator class.
     */
    public DriveTestIdGenerator(){
        id = Integer.toUnsignedLong(0);
    }

    /**
     * Constructor of the DriveTestIdGenerator class.
     * @param lastId
     *      the last id found in the repository.
     */
    public DriveTestIdGenerator(Long lastId){
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
