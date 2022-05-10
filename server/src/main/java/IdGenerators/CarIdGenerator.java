package IdGenerators;

/**
 *Car-specific implementation of the IdGenerator interface.
 */
public class CarIdGenerator implements IdGenerator<Long>{
    private static Long id;

    /**
     * Constructor for the CarIdGenerator class.
     */
    public CarIdGenerator(){
        id = Integer.toUnsignedLong(0);
    }

    /**
     * Constructor for the CarIdGenerator class.
     * @param lastId
     *      the last id found in the repository.
     */
    public CarIdGenerator(Long lastId){
        id = lastId;
    }

    /**
     * Gets the next available ID
     * @return
     *      the next available ID
     */
    public Long getId(){
        return ++id;
    }

    public void setId(Long id) {
        CarIdGenerator.id = id;
    }
}
