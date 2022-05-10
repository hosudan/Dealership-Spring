package IdGenerators;

/**
 *Employee-specific implementation of the IdGenerator interface.
 */
public class EmployeeIdGenerator implements IdGenerator<Long>{
    private static Long id;

    /**
     *Base constructor for the EmployeeIdGenerator class.
     */
    public EmployeeIdGenerator(){
        id = Integer.toUnsignedLong(0);
    }

    /**
     * Constructor for the EmployeeIdGenerator class.
     * @param lastId
     *      the last id found in the repository.
     */
    public EmployeeIdGenerator(Long lastId){
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
