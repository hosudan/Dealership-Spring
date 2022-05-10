package Service;

import Domain.*;
import Domain.Validators.ValidatorException;
import IdGenerators.*;
import Repository.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.concurrent.CompletableFuture;

/**
 * Main service class to be used by the user interface.
 */
public class MainController {
    private  IService<Long, Car> carService;
    private  IService<Long, Client> clientService;
    private  IService<Long, DriveTest> driveTestService;
    private final CarIdGenerator carIdGenerator;
    private final ClientIdGenerator clientIdGenerator;
    private final DriveTestIdGenerator driveTestIdGenerator;
    private ExecutorService executorService;

    public MainController(CarService carS,ClientService clientS,DriveTestService driveTestS,ExecutorService eS){
        this.carService = carS;
        this.clientService = clientS;
        this.driveTestService = driveTestS;
        this.carIdGenerator = new CarIdGenerator(this.carService.getLastId());
        this.clientIdGenerator = new ClientIdGenerator(this.clientService.getLastId());
        this.driveTestIdGenerator = new DriveTestIdGenerator(this.driveTestService.getLastId());
        this.executorService = eS;
    }

    public void setCarService(CarService carController) {
        this.carService = carController;
    }

    public void setClientService(ClientService clientController) {
        this.clientService = clientController;
    }

    public void setDriveTestService(DriveTestService driveTestController) {
        this.driveTestService = driveTestController;
    }


    public void setExecutorService(ExecutorService executorService){
        this.executorService = executorService;
    }

    /**
     * Adds the given entity to the matching repository.
     * @param entity
     *      a given entity, has to match to one of the objects in domain.
     * @throws ValidatorException
     *      if the given entity is not valid.
     */
    public CompletableFuture<String> add(Object entity) throws ValidatorException, ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(()->{
            if (Car.class.equals(entity.getClass())) {
                try {
                    ((Car) entity).setId(this.getCarID().get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return e.getMessage();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    return e.getMessage();
                }
                try {
                    this.carService.addEntity((Car) entity);
                } catch (ValidatorException e){
                    return e.getMessage();
                }
            } else if (Client.class.equals(entity.getClass())) {
                try {
                    ((Client) entity).setId(this.getClientID().get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return e.getMessage();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    return e.getMessage();
                }
                try {
                    this.clientService.addEntity((Client) entity);
                } catch (ValidatorException e){
                    return e.getMessage();
                }
            } else if (DriveTest.class.equals(entity.getClass())) {
                Long id = null;
                try {
                    id = this.getDriveTestID().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                ((DriveTest) entity).setId(id);
                ((DriveTest) entity).setCnp(this.getCnpById(((DriveTest) entity).getClientID()));
                ((DriveTest) entity).setVINNumber(this.getVinById(((DriveTest) entity).getCarID()));
                try {
                    this.driveTestService.addEntity((DriveTest) entity);
                } catch (ValidatorException e){
                    return e.getMessage();
                }
            }
            return "";
        },this.executorService);


    }


    /**
     * Updates the given entity in the matching repository.
     * @param entity
     *      a given entity, has to match to one of the objects in domain.
     */
    public CompletableFuture<String> update(Object entity) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(()->{
            if (Car.class.equals(entity.getClass())) {
                try {
                    this.carService.updateEntity((Car) entity);
                }catch(ValidatorException e) { return e.getMessage(); }
            }
            else if (Client.class.equals(entity.getClass())) {
                try {
                    this.clientService.updateEntity((Client) entity);
                } catch(ValidatorException e) { return e.getMessage(); }
            }
            else if (DriveTest.class.equals(entity.getClass())) {

                Long id = ((DriveTest) entity).getId();
                ((DriveTest) entity).setId(id);
                ((DriveTest) entity).setCnp(this.getCnpById(((DriveTest) entity).getClientID()));
                ((DriveTest) entity).setVINNumber(this.getVinById(((DriveTest) entity).getCarID()));

                try {
                    this.driveTestService.updateEntity((DriveTest) entity);
                } catch(ValidatorException e) { return e.getMessage(); }
            }
        return "";
        },this.executorService);
    }


    /**
     * Deletes the matching entity in the corresponding repository based on ID.
     * @param entity
     *      a given entity, has to match to one of the objects in domain.
     */
    public CompletableFuture<Boolean> delete(Object entity) throws ExecutionException, InterruptedException{
        return CompletableFuture.supplyAsync(()-> {
            if (Car.class.equals(entity.getClass())) {
                this.carService.deleteEntity(((Car) entity).getId());
            } else if (Client.class.equals(entity.getClass())) {
                this.clientService.deleteEntity(((Client) entity).getId());
            } else if (DriveTest.class.equals(entity.getClass())) {
                this.driveTestService.deleteEntity(((DriveTest) entity).getId());
            }
            return true;
        },this.executorService);
    }


    /**
     * Filters the drive tests in the repository based on the rating
     * @param filterRating
     *      the given rating, must not be null.
     * @return
     *      a {@code Set} containing filtered drive tests.
     */
    public CompletableFuture<Set<DriveTest>> filterDriveTestsByRating(int filterRating){

        return CompletableFuture.supplyAsync(()->{
            Iterable<DriveTest> driveTests = this.driveTestService.getAllEntities();
            return StreamSupport.stream(driveTests.spliterator(),false)
                    .filter(driveTest  ->
                            driveTest.getRating() >= filterRating
                    )
                    .collect(Collectors.toSet());
        },this.executorService);
    }

    /**
     * Filters the drive tests in the repository based on the CNP.
     * @param filterCnp
     *      the given CNP, must not be null.
     * @return
     *       a {@code Set} containing filtered drive tests.
     */
    public CompletableFuture<Set<DriveTest>> filterDriveTestsByCnp(String filterCnp){

        return CompletableFuture.supplyAsync(()->{
            Iterable<DriveTest> driveTests = this.driveTestService.getAllEntities();
            return StreamSupport.stream(driveTests.spliterator(),false)
                    .filter(driveTest ->
                            driveTest.getCnp().equals(filterCnp)
                    )
                    .collect(Collectors.toSet());
        });
    }


    /**
     * Filters the cars in the repository based on the manufacturer.
     * @param manufacturer
     *      the given manufacturer, must not be null.
     * @return
     *       a {@code Set} containing filtered cars.
     */
    public CompletableFuture<Set<Car>> filterCarsByManufacturer(String manufacturer){

        return CompletableFuture.supplyAsync(()->{
            Iterable<Car> cars = this.carService.getAllEntities();
            return StreamSupport.stream(cars.spliterator(),false)
                    .filter(car ->
                            car.getManufacturer().equals(manufacturer)
                    )
                    .collect(Collectors.toSet());
        },this.executorService);
    }

    /**
     * Filters the clients based on their firstname
     *      clients sorted by firstname
     * @return
     *      a {@code List} containing the sorted list of clients.
     */
    public CompletableFuture<List<Client>> filterclientsfn(){

        return CompletableFuture.supplyAsync(()->{
            Iterable<Client> clients = this.clientService.getAllEntities();
            return StreamSupport.stream(clients.spliterator(),false)
                    .sorted(Comparator.comparing(Client::getFirstName))
                    .collect(Collectors.toList());
        },this.executorService);
    }



    /**
     * Filters the drive tests in the repository based on the VIN.
     * @param filterVin
     *      the given VIN, must no tbe null.
     * @return
     *      a {@code Set} containing filtered drive tests.
     */
    public CompletableFuture<Set<DriveTest>> filterDriveTestsByVin(String filterVin){

        return CompletableFuture.supplyAsync(()->{
            Iterable<DriveTest> driveTests = this.driveTestService.getAllEntities();
            return StreamSupport.stream(driveTests.spliterator(),false)
                    .filter(driveTest ->
                            driveTest.getVINNumber().equals(filterVin)
                    )
                    .collect(Collectors.toSet());
        },this.executorService);
    }


    /**
     * Filters the cars in the repository based of the model.
     * @param filterModel
     *      the given model, must not be null.
     * @return
     *      a {@code Set} containing filtered cars.
     */
    public CompletableFuture<Set<Car>> filterCarsByModel(String filterModel){

        return CompletableFuture.supplyAsync(()->{
            Iterable<Car> cars = this.carService.getAllEntities();
            return StreamSupport.stream(cars.spliterator(),false)
                    .filter(car ->
                            car.getModel().equals(filterModel)
                    )
                    .collect(Collectors.toSet());
        },this.executorService);
    }


    public CompletableFuture<Car> ReportMostTestedCars() {
        return CompletableFuture.supplyAsync(()->{


        Iterable<DriveTest> DTCars = this.driveTestService.getAllEntities();
        var freq = StreamSupport.stream(DTCars.spliterator(), false).collect(Collectors.groupingBy(DriveTest::getVINNumber, Collectors.counting()));
        String mostSoldCarVin = "";
        Long mxNr = 0L;
        Long currMx = 0L;
        for (Map.Entry<String, Long> entry : freq.entrySet()) {
            currMx = entry.getValue();
            if(currMx > mxNr) {
                mxNr = currMx;
                mostSoldCarVin = entry.getKey();
            }
        }
        Iterable<Car> MSCars = this.carService.getAllEntities();
        String finalMostSoldCarVin = mostSoldCarVin;
        var MSCarsSolution = StreamSupport.stream(MSCars.spliterator(),false)
                .filter(car ->
                        car.getVINNumber().equals(finalMostSoldCarVin)
                )
                .collect(Collectors.toList());
        return MSCarsSolution.get(0);
        },this.executorService);
    }


    public CompletableFuture<Client> ReportBiggestTester() {

        return CompletableFuture.supplyAsync(()->{

        Iterable<DriveTest> DTCars = this.driveTestService.getAllEntities();

        var freq = StreamSupport.stream(DTCars.spliterator(), false).collect(Collectors.groupingBy(DriveTest::getCnp, Collectors.counting()));
        String biggestTesterCNP = "";
        Long mxNr = 0L;
        Long currMx = 0L;
        for (Map.Entry<String, Long> entry : freq.entrySet()) {
            currMx = entry.getValue();
            if(currMx > mxNr) {
                mxNr = currMx;
                biggestTesterCNP = entry.getKey();
            }
        }
        Iterable<Client> MSClients = this.clientService.getAllEntities();
        String finalBiggestTesterCNP = biggestTesterCNP;
        var MSClientsSolution = StreamSupport.stream(MSClients.spliterator(),false)
                .filter(client ->
                        client.getCnp().equals(finalBiggestTesterCNP)
                )
                .collect(Collectors.toSet());
        Client[] clients = new Client[1];
        MSClientsSolution.toArray(clients);
        return clients[0];
        },this.executorService);
    }


    /**
     * Gets all the cars in the car repository.
     * @return
     *      a {@code Set} containing all the cars.
     */
    public CompletableFuture<Set<Car>> getAllCars(){
        return CompletableFuture.supplyAsync(this.carService::getAllEntities,this.executorService);
    }

    /**
     * Gets all the clients in the client repository.
     * @return
     *      a {@code Set} containing all the clients.
     */
    public CompletableFuture<Set<Client>> getAllClients(){
        return CompletableFuture.supplyAsync(this.clientService::getAllEntities,this.executorService);
    }

    /**
     * Gets all the drive tests in the drive test repository.
     * @return
     *      a {@code Set} containing all the drive tests.
     */
    public CompletableFuture<Set<DriveTest>> getAllDriveTests(){
        return CompletableFuture.supplyAsync(this.driveTestService::getAllEntities,this.executorService);
    }

    public String getCnpById(Long id){
        return ((ClientService)clientService).getCnpByID(id);
    }

    public String getVinById(Long id){
        return ((CarService)carService).getVINByID(id);
    }


    /**
     * Instantiates the car repository with some example cars.
     */
    public void populateCars(){
        ((CarService)this.carService).populateRepository();
    }

    /**
     * Instantiates the client repository with some example clients.
     */
    public void populateClients(){
        ((ClientService)this.clientService).populateRepository();
    }

    /**
     * Instantiates the drive test repository with some example drive tests.
     */
    public void populateDriveTests(){
        ((DriveTestService)this.driveTestService).populateRepository();
    }

    /**
     * Gets the next available car ID.
     * @return
     *      the nest available car ID.
     */
    public CompletableFuture<Long> getCarID(){
        return CompletableFuture.supplyAsync(this.carIdGenerator::getId,this.executorService);
    }

    /**
     * Gets the next available client ID.
     * @return
     *      the next available client ID.
     */
    public CompletableFuture<Long> getClientID(){
        return CompletableFuture.supplyAsync(this.clientIdGenerator::getId,this.executorService);
    }

    /**
     * Gets the next available drive test ID.
     * @return
     *      the next available drive test ID.
     */
    public CompletableFuture<Long> getDriveTestID(){
        return CompletableFuture.supplyAsync(this.driveTestIdGenerator::getId,this.executorService);
    }

    //todo lab
}