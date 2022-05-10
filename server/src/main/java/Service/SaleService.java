package Service;


import Domain.Sale;
import Domain.Validators.ValidatorException;
import IdGenerators.SaleIdGenerator;
import Repository.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Generic service for the DriveTest object that works on a matching repository
 */
public class SaleService implements IService<Long, Sale>{
    private Repository<Long, Sale> repository;

    /**
     * Constructor for the drive test service.
     * @param repository
     *      is a matching repository for the DriveTest object.
     */
    public SaleService(Repository<Long,Sale> repository){
        this.repository = repository;
    }

    /**
     * Parses the given DriveTest entity to the repository to be saved.
     * @param entity
     *      must not be null.
     * @throws ValidatorException
     *      if the given entity is not valid.
     */
    public void addEntity(Sale entity)throws ValidatorException {
        this.repository.save(entity);
    }

    /**
     * Updates the drive test in the repository with the matching ID.
     * @param entity
     *      must not be null.
     */
    public void updateEntity(Sale entity){
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
        SaleIdGenerator saleIdGenerator = new SaleIdGenerator();
        for (int i=1;i<=5;i++){
            String cnp = "cnp000000000" + String.valueOf(i);
            String model = String.valueOf(i);
            String manufacturer = String.valueOf(i);
            double price=5000;
            Sale newSale=new Sale(cnp,model,manufacturer,price);
            newSale.setId(saleIdGenerator.getId());
            repository.save(newSale);
        }
    }

    /**
     * Gets all the saved drive tests.
     * @return
     *      a {@code Set} containing all the drive tests in the repository.
     */
    public Set<Sale> getAllEntities(){
        Iterable<Sale> sales = this.repository.findAll();
        return StreamSupport.stream(sales.spliterator(),false).collect(Collectors.toSet());
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
        Iterable<Sale> driveTests = this.repository.findAll();
        driveTests.forEach(driveTest -> {
            if(driveTest.getId() > lastId[0]){
                lastId[0] = driveTest.getId();
            }
        });

        return lastId[0];
    }

}
