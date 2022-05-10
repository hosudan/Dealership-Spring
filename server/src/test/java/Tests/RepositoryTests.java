package Tests;


import Domain.Car;
import Domain.Validators.CarValidator;
import IdGenerators.CarIdGenerator;
import Repository.InMemoryRepository;

import java.util.Optional;

public class RepositoryTests {

    public void runMemoryTests(){
        InMemoryRepository_constructor_valid();
        InMemoryRepository_save_goodCar_saved();
        InMemoryRepository_findOne_goodId_goodOptional();
        InMemoryRepository_findOne_badId_badOptional();
    }

    private void InMemoryRepository_constructor_valid(){
        CarValidator validator = new CarValidator();
        InMemoryRepository<Long, Car> carRepo = new InMemoryRepository<>(validator);

        assert carRepo!=null : "carRepo is null!";
    }

    private void InMemoryRepository_save_goodCar_saved(){
        CarValidator validator = new CarValidator();
        InMemoryRepository<Long, Car> carRepo = new InMemoryRepository<>(validator);
        CarIdGenerator carIdGenerator = new CarIdGenerator();
        carRepo.setIdGenerator(carIdGenerator);
        Car car1 = new Car();
        car1.setId(carRepo.getNextId());
        carRepo.save(car1);

        assert carRepo.size()==1 : "first car not saved";

        Car car2 = new Car();
        car2.setId(carRepo.getNextId());
        carRepo.save(car2);

        assert carRepo.size()==2 : "second car not saved";
    }

    private void InMemoryRepository_findOne_goodId_goodOptional(){
        CarValidator validator = new CarValidator();
        InMemoryRepository<Long, Car> carRepo = new InMemoryRepository<>(validator);
        CarIdGenerator carIdGenerator = new CarIdGenerator();
        carRepo.setIdGenerator(carIdGenerator);
        Car car1 = new Car();
        car1.setId(carRepo.getNextId());

        Optional<Car> carOptional = carRepo.findOne(car1.getId());

        assert carOptional.isPresent() : "car not found";
    }

    private void InMemoryRepository_findOne_badId_badOptional(){
        CarValidator validator = new CarValidator();
        InMemoryRepository<Long, Car> carRepo = new InMemoryRepository<>(validator);
        CarIdGenerator carIdGenerator = new CarIdGenerator();
        carRepo.setIdGenerator(carIdGenerator);
        Car car1 = new Car();
        car1.setId(carRepo.getNextId());

        Optional<Car> carOptional = carRepo.findOne(carRepo.getNextId());

        assert carOptional.isPresent() : "car was found found";
    }

















    // @Txt Repository tests

    public void runTxtTests(){

    }

}
