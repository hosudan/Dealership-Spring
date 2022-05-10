package Service;


import Domain.Car;
import Domain.Validators.ValidatorException;
import IdGenerators.CarIdGenerator;
import Repository.Repository;

import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Generic service for the Car object that works on a matching Repository.
 */
public class CarService implements IService<Long, Car>{
    private Repository<Long, Car> repository;

    /**
     * Constructor for the car service.
     * @param repository
     *          is a matching repository for the Car object.
     */
    public CarService(Repository<Long, Car> repository) {
        this.repository = repository;
    }

    /**
     * Parses the given Car entity to the repository to be saved.
     * @param entity
     *          must not be null.
     * @throws ValidatorException
     *          if the given car is not valid.
     */
    public void addEntity(Car entity) throws ValidatorException {
        try{
        repository.save(entity);} catch(ValidatorException e){
        throw new ValidatorException(e.getMessage()); }
    }

    /**
     * Updates the car in the repository with the matching ID.
     * @param entity
     *          must not be null.
     */
    public void updateEntity(Car entity){
        repository.update(entity);
    }

    /**
     * Deletes the car in the repository with the matching ID.
     * @param id
     *          must not be null.
     */
    public void deleteEntity(Long id){
        repository.delete(id);
    }

    /**
     * Populates the repository with some model cars.
     */
    public void populateRepository(){
        CarIdGenerator carIdGenerator = new CarIdGenerator();
        for (int i=1;i<=5;i++){
            String vin = "vin-------------"+String.valueOf(i);
            String model = String.valueOf(i);
            String manufacturer = String.valueOf(i);
            Car car = new Car(vin,model,manufacturer);
            car.setId(carIdGenerator.getId());

            repository.save(car);
        }
    }

    /**
     * Gets all the saved cars.
     * @return
     *      a {@code Set} containing all the cars in the repository.
     */
    public Set<Car> getAllEntities() {
        Iterable<Car> cars = repository.findAll();
        return StreamSupport.stream(cars.spliterator(), false).collect(Collectors.toSet());
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
        Iterable<Car> cars = this.repository.findAll();
        cars.forEach(car -> {
            if(car.getId() > lastId[0]){
                lastId[0] = car.getId();
            }
        });

        return lastId[0];
    }

    public String getVINByID(Long id){
        Iterable<Car> cars = this.repository.findAll();
        AtomicReference<String> vinNumber= new AtomicReference<>("");
        cars.forEach(car -> {
            if(car.getId().equals(id)){
                vinNumber.set(car.getVINNumber());
            }
        });
        return vinNumber.get();
    }

}