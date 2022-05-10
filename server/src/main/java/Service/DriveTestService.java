package Service;



import Domain.DriveTest;
import Domain.Validators.ValidatorException;
import IdGenerators.DriveTestIdGenerator;
import Repository.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Generic service for the DriveTest object that works on a matching repository
 */
public class DriveTestService implements IService<Long, DriveTest>{
    private Repository<Long, DriveTest> repository;

    /**
     * Constructor for the drive test service.
     * @param repository
     *      is a matching repository for the DriveTest object.
     */
    public DriveTestService(Repository<Long,DriveTest> repository){
        this.repository = repository;
    }

    /**
     * Parses the given DriveTest entity to the repository to be saved.
     * @param entity
     *      must not be null.
     * @throws ValidatorException
     *      if the given entity is not valid.
     */
    public void addEntity(DriveTest entity)throws ValidatorException {
        this.repository.save(entity);
    }

    /**
     * Updates the drive test in the repository with the matching ID.
     * @param entity
     *      must not be null.
     */
    public void updateEntity(DriveTest entity){
        this.repository.update(entity);
    }

    /**
     * Deletes the drive test in the repository with the matching ID.
     * @param id
     *      must not be null.
     */
    public void deleteEntity(Long id){
        this.repository.delete(id);
    }

    /**
     * Populates the repository with some model drive tests.
     */
    public void populateRepository(){
        DriveTestIdGenerator driveTestIdGenerator = new DriveTestIdGenerator();
        for (int i=1;i<=5;i++){
            Long clientId = Integer.toUnsignedLong(i);
            Long carId = Integer.toUnsignedLong(i);
            String cnp = "cnp000000000" + String.valueOf(i);
            String vin = "vin"+String.valueOf(i);
            int rating = i;
            DriveTest driveTest = new DriveTest(clientId,carId,cnp,vin,rating);
            driveTest.setId(driveTestIdGenerator.getId());
            repository.save(driveTest);
        }
    }

    /**
     * Gets all the saved drive tests.
     * @return
     *      a {@code Set} containing all the drive tests in the repository.
     */
    public Set<DriveTest> getAllEntities(){
        Iterable<DriveTest> driveTests = this.repository.findAll();
        return StreamSupport.stream(driveTests.spliterator(),false).collect(Collectors.toSet());
    }

    /**
     * Gets the last ID in the repository.
     * @return
     *      the last assigned ID.
     */
    @Override
    public Long getLastId() {
        Long[] lastId = new Long[1];
        lastId[0] = 0L;
        Iterable<DriveTest> driveTests = this.repository.findAll();
        driveTests.forEach(driveTest -> {
            if(driveTest.getId() > lastId[0]){
                lastId[0] = driveTest.getId();
            }
        });

        return lastId[0];
    }

}
