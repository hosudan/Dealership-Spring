package Service;

import Domain.Car;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface CarService extends Service{

    public CompletableFuture<Boolean> addCar(Car car);
    public CompletableFuture<Set<Car>> getAllCars();
    public CompletableFuture<Boolean> populateCars();
    public CompletableFuture<Boolean> deleteCar(Car car);
    public CompletableFuture<Boolean> updateCar(Car car);
    public CompletableFuture<Set<Car>> filterCarsByModel(String filtermodel);
    public CompletableFuture<Set<Car>> filterCarsByManufacturer(String filtermanufacturer);
    public CompletableFuture<Car> getMostTestedCar();

}
