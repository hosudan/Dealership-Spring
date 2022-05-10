package UX;

import Domain.*;
import TCP.TCPClientServiceCar;
import TCP.TCPClientServiceClient;
import TCP.TCPClientServiceDriveTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

/**
 * Basic console UI for the program.
 */
public class Console {
    private final TCPClientServiceCar carService;
    private final TCPClientServiceClient clientService;
    private final TCPClientServiceDriveTest driveTestService;
    /**
     * Constructor for the console UI.
     * @param carservice,clientService
     *          must be an existing car service.
     */
    public Console(TCPClientServiceCar carservice,TCPClientServiceClient clientService,TCPClientServiceDriveTest driveTestService) {
        this.carService = carservice;
        this.clientService = clientService;
        this.driveTestService=driveTestService;
    }

    /**
     * Main loop for the console UI.
     */
    public void runConsole() {
        boolean isRunning = true;
        while(isRunning){
            printMenu();
            isRunning = getCommand();
        }
    }

    /**
     * Prints the console menu with the available procedures.
     */
    private void printMenu() {
        StringBuilder builder = new StringBuilder();

        builder.append("\n\n##################\n");
        builder.append("1. Car functionalities\n");
        builder.append("2. Client functionalities\n");
        builder.append("3. Drive test functionalities\n");
        builder.append("4. Filters\n");
        builder.append("5. Reports\n");
        builder.append("\n");
        builder.append("0. Exit\n");
        builder.append("##################\n");


        System.out.println(builder);
    }
    //attendance

    /**
     * Reads the command from the user and executes the matching procedure.
     * @return the running state of the program.
     */
    private Boolean getCommand(){
        boolean keepRunning=true;

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {
            String readLine  = bufferRead.readLine();
            String command =  readLine.strip();
            boolean subMenuRun = true;


            switch (command) {
                case "0" -> keepRunning = false;
                case "1" -> {
                    while(subMenuRun){
                        this.printCarMenu();
                        subMenuRun = this.getCarCommand();
                    }
                }
                case "2" -> {
                    while(subMenuRun){
                        this.printClientMenu();
                        subMenuRun = this.getClientCommand();
                    }
                }
                case "3" -> {
                    while(subMenuRun){
                        this.printDriveTestMenu();
                        subMenuRun = this.getDriveTestCommand();
                    }
                }
                case "4" -> {
                    while(subMenuRun){
                        this.printFilterMenu();
                        subMenuRun = this.getFilterCommand();
                    }
                }
                case "5" -> {
                    while(subMenuRun){
                        this.printReportMenu();
                        subMenuRun = this.getReportCommand();
                    }
                }
                default -> System.out.println("Wrong command!");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        return keepRunning;
    }

    //Car section


    /**
     * Prints the console menu for the Car object with the available procedures.
     */
    private void printCarMenu(){
        StringBuilder builder = new StringBuilder();

        builder.append("\n\n                 Car menu\n");
        builder.append("101. Populate cars\n");
        builder.append("1. Add car\n");
        builder.append("2. Print all cars\n");
        builder.append("3. Delete car\n");
        builder.append("4. Update car\n");
        builder.append("\n");
        builder.append("0. Back\n");
        builder.append("##################\n");


        System.out.println(builder);
    }

    /**
     * Reads the command from the user and executes the matching procedure.
     * @return the running state of the program.
     */
    private Boolean getCarCommand(){
        boolean keepRunning=true;

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {
            String readLine  = bufferRead.readLine();
            String command =  readLine.strip();

            switch (command) {
                case "0" -> keepRunning = false;
                case "1" -> this.addCar();
                case "2" -> this.printAllCars();
                case "3" -> this.deleteCar();
                case "4" -> this.updateCar();
                case "101" -> this.addCars();
                default -> System.out.println("Wrong command!");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        return keepRunning;
    }


    /**
     * Prints all the cars in the repository.
     */
    private void printAllCars() {
        try {
            carService.getAllCars().thenAccept(car -> car.forEach(System.out::println));
        }catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Processes the populate procedure.
     */
    private void addCars() {
        try {
            this.carService.populateCars();
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Processes the add car procedure.
     */
    private void addCar(){
        try {
            Car car = readCarAddition();

            if (car == null) {
                return;
            }
            this.carService.addCar(car);
        }
        catch (RuntimeException exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Reads the attributes of a car object and creates the matching Car.
     *
     * @throws RuntimeException
     *          if the number of given arguments does not match the number of object parameters.
     * @return return the created car object if it has a good input.
     */
    private Car readCarAddition() {
        System.out.println("Read car {VINNumber, Model, Manufacturer}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            String inputLine = bufferRead.readLine();
            String[] lineAttributes = inputLine.split(",");

            if(lineAttributes.length!=3){
                throw new RuntimeException("Invalid number of attributes!");
            }

            String vinNumber = lineAttributes[0].strip();
            String model = lineAttributes[1].strip();
            String manufacturer = lineAttributes[2].strip();

            Car car = new Car(vinNumber,model,manufacturer);
            car.setId(-1L);

            return car;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Processes the delete car procedure.
     */
    private void deleteCar(){
        try {
            Long id = readCarDeletion();

            if (id == null) {
                return;
            }

            Car tempCar = new Car();
            tempCar.setId(id);
            carService.deleteCar(tempCar);
        }
        catch (RuntimeException exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Reads the ID a car object.
     *
     * @throws RuntimeException
     *          if the number of given arguments does not match the number of object parameters.
     * @return return the deleted car ID.
     */
    private Long readCarDeletion(){
        System.out.println("Read car {ID}");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            String inputLine = bufferRead.readLine();
            String[] lineAttributes = inputLine.split(",");

            if(lineAttributes.length!=1){
                throw new RuntimeException("Invalid number of attributes!");
            }

            return Long.parseLong(lineAttributes[0].strip());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Processes the update car procedure.
     */
    private void updateCar(){
        try {
            Car car = readCarUpdate();

            if (car == null) {
                return;
            }
            this.carService.updateCar(car);
        }
        catch (RuntimeException exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Reads the attributes of a car object and creates the matching Car.
     *
     * @throws RuntimeException
     *          if the number of given arguments does not match the number of object parameters.
     * @return return the updated car object if it has a good input.
     */
    private Car readCarUpdate() {
        System.out.println("Read car {ID, VINNumber, Model, Manufacturer}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            String inputLine = bufferRead.readLine();
            String[] lineAttributes = inputLine.split(",");

            if(lineAttributes.length!=4){
                throw new RuntimeException("Invalid number of attributes!");
            }

            Long id = Long.parseLong(lineAttributes[0].strip());
            String vinNumber = lineAttributes[1].strip();
            String model = lineAttributes[2].strip();
            String manufacturer = lineAttributes[3].strip();

            Car car = new Car(vinNumber,model,manufacturer);
            car.setId(id);

            return car;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    //Client section


    /**
     * Prints the console menu for the Client object with the available procedures.
     */
    private void printClientMenu(){
        StringBuilder builder = new StringBuilder();

        builder.append("\n\n                 Client menu\n");
        builder.append("101. Populate clients\n");
        builder.append("1. Add client\n");
        builder.append("2. Print all clients\n");
        builder.append("3. Delete client\n");
        builder.append("4. Update client\n");
        builder.append("\n");
        builder.append("0. Back\n");
        builder.append("##################\n");


        System.out.println(builder);
    }

    /**
     * Reads the command from the user and executes the matching procedure.
     * @return the running state of the program.
     */
    private Boolean getClientCommand(){
        boolean keepRunning=true;

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {
            String readLine  = bufferRead.readLine();
            String command =  readLine.strip();

            switch (command) {
                case "0" -> keepRunning = false;
                case "1" -> this.addClient();
                case "2" -> this.printAllClients();
                case "3" -> this.deleteClient();
                case "4" -> this.updateClient();
                case "101" -> this.addClients();
                default -> System.out.println("Wrong command!");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        return keepRunning;
    }


    /**
     * Prints all the clients in the repository.
     */
    private void printAllClients() {
        try {
            clientService.getAllClients().thenAccept(clients->clients.forEach(System.out::println));
        }catch(RuntimeException e) {System.out.println(e.getMessage()); }
    }

    /**
     * Processes the populate procedure.
     */
    private void addClients() {
        try {
            this.clientService.populateClients();
        }
        catch(RuntimeException e) { System.out.println(e.getMessage()); }
    }

    /**
     * Processes the add client procedure.
     */
    private void addClient(){
        try {
            Client client = readClientAddition();

            if (client == null) {
                return;
            }
            this.clientService.addClient(client);
        }
        catch (RuntimeException exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Reads the attributes of a client object and creates the matching Client.
     *
     * @throws RuntimeException
     *          if the number of given arguments does not match the number of object parameters.
     * @return return the created client object if it has a good input.
     */
    private Client readClientAddition() {
        System.out.println("Read client {CNP, first name, last name, age}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            String inputLine = bufferRead.readLine();
            String[] lineAttributes = inputLine.split(",");

            if(lineAttributes.length!=4){
                throw new RuntimeException("Invalid number of attributes!");
            }

            String cnp = lineAttributes[0].strip();
            String firstName = lineAttributes[1].strip();
            String lastName = lineAttributes[2].strip();
            Integer age = Integer.parseInt(lineAttributes[3].strip());

            Client client = new Client(cnp,firstName,lastName,age);
            client.setId(-1L);

            return client;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Processes the delete client procedure.
     */
    private void deleteClient(){
        try {
            Long id = readClientDeletion();

            if (id == null) {
                return;
            }

            Client tempClient = new Client();
            tempClient.setId(id);
            clientService.deleteClient(tempClient);
        }
        catch (RuntimeException exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Reads the ID a client object.
     *
     * @throws RuntimeException
     *          if the number of given arguments does not match the number of object parameters.
     * @return return the deleted client ID.
     */
    private Long readClientDeletion(){
        System.out.println("Read client {ID}");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            String inputLine = bufferRead.readLine();
            String[] lineAttributes = inputLine.split(",");

            if(lineAttributes.length!=1){
                throw new RuntimeException("Invalid number of attributes!");
            }

            return Long.parseLong(lineAttributes[0].strip());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Processes the update client procedure.
     */
    private void updateClient(){
        try {
            Client client = readClientUpdate();

            if (client == null) {
                return;
            }
            this.clientService.updateClient(client);
        }
        catch (RuntimeException exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Reads the attributes of a car object and creates the matching Car.
     *
     * @throws RuntimeException
     *          if the number of given arguments does not match the number of object parameters.
     * @return return the updated car object if it has a good input.
     */
    private Client readClientUpdate() {
        System.out.println("Read client {ID, CNP, first name, last name, age}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            String inputLine = bufferRead.readLine();
            String[] lineAttributes = inputLine.split(",");

            if(lineAttributes.length!=5){
                throw new RuntimeException("Invalid number of attributes!");
            }

            Long id = Long.parseLong(lineAttributes[0].strip());
            String cnp = lineAttributes[1].strip();
            String firstName = lineAttributes[2].strip();
            String lastName = lineAttributes[3].strip();
            Integer age = Integer.parseInt(lineAttributes[4].strip());


            Client client = new Client(cnp,firstName,lastName,age);
            client.setId(id);

            return client;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }






    //Drive test section



    /**
     * Prints the console menu for the DriveTest object with the available procedures.
     */
    private void printDriveTestMenu(){
        StringBuilder builder = new StringBuilder();

        builder.append("\n\n                 Drive test menu\n");
        builder.append("101. Populate drive tests\n");
        builder.append("1. Add drive test\n");
        builder.append("2. Print all drive tests\n");
        builder.append("3. Delete drive test\n");
        builder.append("4. Update drive test\n");
        builder.append("\n");
        builder.append("0. Back\n");
        builder.append("##################\n");


        System.out.println(builder);
    }

    /**
     * Reads the command from the user and executes the matching procedure.
     * @return the running state of the program.
     */
    private Boolean getDriveTestCommand(){
        boolean keepRunning=true;

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {
            String readLine  = bufferRead.readLine();
            String command =  readLine.strip();

            switch (command) {
                case "0" -> keepRunning = false;
                case "1" -> this.addDriveTest();
                case "2" -> this.printAllDriveTests();
                case "3" -> this.deleteDriveTest();
                case "4" -> this.updateDriveTest();
                case "101" -> this.addDriveTests();
                default -> System.out.println("Wrong command!");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        return keepRunning;
    }


    /**
     * Prints all the drive tests in the repository.
     */
    private void printAllDriveTests() {
        try {
            driveTestService.getAllDriveTests().thenAccept(dts->dts.forEach(System.out::println));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Processes the populate procedure.
     */
    private void addDriveTests() {
        try {
            this.driveTestService.populateDriveTests();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Processes the add drive test procedure.
     */
    private void addDriveTest(){
        try {
            DriveTest driveTest = readDriveTestAddition();

            if (driveTest == null) {
                return;
            }
            this.driveTestService.addDriveTest(driveTest);
        }
        catch (RuntimeException exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Reads the attributes of a drive test object and creates the matching DriveTest.
     *
     * @throws RuntimeException
     *          if the number of given arguments does not match the number of object parameters.
     * @return return the created drive test object if it has a good input.
     */
    private DriveTest readDriveTestAddition() {
        System.out.println("Read drive test {ClientId, CarId, rating}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            String inputLine = bufferRead.readLine();
            String[] lineAttributes = inputLine.split(",");

            if(lineAttributes.length!=3){
                throw new RuntimeException("Invalid number of attributes!");
            }

            Long ClientId = Long.parseLong(lineAttributes[0].strip());
            Long CarId = Long.parseLong(lineAttributes[1].strip());
            int rating = Integer.parseInt(lineAttributes[2].strip());

            DriveTest driveTest = new DriveTest(ClientId,CarId,"null","null",rating);
            driveTest.setId(-1L);

            return driveTest;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Processes the delete drive test procedure.
     */
    private void deleteDriveTest(){
        try {
            Long id = readDriveTestDeletion();

            if (id == null) {
                return;
            }

            DriveTest tempDriveTest = new DriveTest();
            tempDriveTest.setId(id);
            driveTestService.deleteDriveTest(tempDriveTest);
        }
        catch (RuntimeException exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Reads the ID of a drive test object.
     *
     * @throws RuntimeException
     *          if the number of given arguments does not match the number of object parameters.
     * @return return the deleted drive test ID.
     */
    private Long readDriveTestDeletion(){
        System.out.println("Read drive test {ID}");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            String inputLine = bufferRead.readLine();
            String[] lineAttributes = inputLine.split(",");

            if(lineAttributes.length!=1){
                throw new RuntimeException("Invalid number of attributes!");
            }

            return Long.parseLong(lineAttributes[0].strip());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Processes the update car procedure.
     */
    private void updateDriveTest(){
        try {
            DriveTest driveTest = readDriveTestUpdate();

            if (driveTest == null) {
                return;
            }
            this.driveTestService.updateDriveTest(driveTest);
        }
        catch (RuntimeException exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Reads the attributes of a drive test object and creates the matching DriveTest.
     *
     * @throws RuntimeException
     *          if the number of given arguments does not match the number of object parameters.
     * @return return the updated drive test object if it has a good input.
     */
    private DriveTest readDriveTestUpdate() {
        System.out.println("Read drive test {ID, ClientId, CarId, rating}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            String inputLine = bufferRead.readLine();
            String[] lineAttributes = inputLine.split(",");

            if(lineAttributes.length!=4){
                throw new RuntimeException("Invalid number of attributes!");
            }

            Long id = Long.parseLong(lineAttributes[0].strip());

            Long ClientId = Long.parseLong(lineAttributes[1].strip());
            Long CarId = Long.parseLong(lineAttributes[2].strip());
            int rating = Integer.parseInt(lineAttributes[3].strip());

            DriveTest driveTest = new DriveTest(ClientId,CarId,"null","null",rating);
            driveTest.setId(id);

            return driveTest;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }





    //Filter section


    /**
     * Prints the console menu used for filtering procedures.
     */
    private void printFilterMenu(){
        StringBuilder builder = new StringBuilder();

        builder.append("\n\n                 Filter menu\n");
        builder.append("1. Filter cars based on a given model\n");
        builder.append("2. Filter drive tests based on rating\n");
        builder.append("3. Filter drive tests based on cnp\n");
        builder.append("4. Filter drive tests based in VIN number\n");
        builder.append("5. Filter/Sort clients by firstname\n");
        builder.append("6. Filter cars based on given manufacturer\n");
        builder.append("\n");
        builder.append("0. Back\n");
        builder.append("##################\n");


        System.out.println(builder);
    }

    /**
     * Reads the command from the user and executes the matching procedure.
     * @return the running state of the program.
     */
    private Boolean getFilterCommand(){
        boolean keepRunning=true;

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {
            String readLine  = bufferRead.readLine();
            String command =  readLine.strip();

            switch (command) {
                case "0" -> keepRunning = false;
                case "1" -> this.filterCarsByModel();
                case "2" -> this.filterDriveTestsByRating();
                case "3" -> this.filterDriveTestsByCNP();
                case "4" -> this.filterDriveTestsByVIN();
                case "5" -> this.filtersortClients();
                case "6" -> this.filterCarsByManufacturer();
                default -> System.out.println("Wrong command!");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        return keepRunning;
    }

    /**
     * sorts the clients based on their firstname
     */
    private void filtersortClients(){
        System.out.println("Clients sorted based on their firstname:");
        try {
            clientService.filtersortClients().thenAcceptAsync(clients->clients.forEach(System.out::println));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Filters the cars based on the given input.
     */
    private void filterCarsByManufacturer(){
        String filterManufacturer = this.filterCarsManufacturerReader();
        try {
            carService.filterCarsByManufacturer(filterManufacturer).thenAcceptAsync(cars->cars.forEach(System.out::println));
        }catch (RuntimeException e){System.out.println(e.getMessage());}
    }

    /**
     * Reads a manufacturer from the keyboard and parses it.
     *
     * @throws RuntimeException
     *      if the number of given arguments does not match the number of object parameters.
     * @return
     *      the read manufacturer.
     */
    private String filterCarsManufacturerReader(){
        System.out.println("Enter the mawnufacturer you want to see: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            String inputLine = bufferRead.readLine();
            String[] lineAttributes = inputLine.split(",");

            if(lineAttributes.length!=1){
                throw new RuntimeException("Invalid number of attributes!");
            }

            String filterModel = lineAttributes[0].strip();

            return filterModel;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Filters the cars based on the given input.
     */
    private void filterCarsByModel(){
        String filterModel = this.filterCarsModelReader();
        try {
            carService.filterCarsByModel(filterModel).thenAcceptAsync(cars->cars.forEach(System.out::println));
        }catch (RuntimeException e)
        {e.getMessage();}
    }


    /**
     * Reads a model from the keyboard and parses it.
     *
     * @throws RuntimeException
     *      if the number of given arguments does not match the number of object parameters.
     * @return
     *      the read model.
     */
    private String filterCarsModelReader(){
        System.out.println("Enter the model you want to see: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            String inputLine = bufferRead.readLine();
            String[] lineAttributes = inputLine.split(",");

            if(lineAttributes.length!=1){
                throw new RuntimeException("Invalid number of attributes!");
            }

            String filterModel = lineAttributes[0].strip();

            return filterModel;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Filters the drive tests based on the given input.
     */
    private void filterDriveTestsByRating(){
        int filterRating = this.filterDriveTestsRatingReader();

        try {
            driveTestService.filterDriveTestByRating(filterRating).thenAccept(dts->dts.forEach(System.out::println));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads a rating from the keyboard and parses it.
     *
     * @throws RuntimeException
     *      if the number of given arguments does not match the number of object parameters.
     * @return
     *      the read rating.
     */
    private int filterDriveTestsRatingReader(){
        System.out.println("Enter the rating: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            String inputLine = bufferRead.readLine();
            String[] lineAttributes = inputLine.split(",");

            if(lineAttributes.length!=1){
                throw new RuntimeException("Invalid number of attributes!");
            }

            int filterRating = Integer.parseInt(lineAttributes[0].strip());

            return filterRating;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    /**
     * Filters the drive tests based on the given input.
     */
    private void filterDriveTestsByCNP(){
        String filterCNP = this.filterDriveTestsCNPReader();

        try {
            driveTestService.filterDriveTestByCNP(filterCNP).thenAcceptAsync(dts->dts.forEach(System.out::println));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads a CNP from the keyboard and parses it.
     *
     * @throws RuntimeException
     *      if the number of given arguments does not match the number of object parameters.
     * @return
     *      the read CNP.
     */
    private String filterDriveTestsCNPReader(){
        System.out.println("Enter the CNP: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            String inputLine = bufferRead.readLine();
            String[] lineAttributes = inputLine.split(",");

            if(lineAttributes.length!=1){
                throw new RuntimeException("Invalid number of attributes!");
            }

            String filterCNP = lineAttributes[0].strip();

            return filterCNP;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Filters the drive tests based on the given input.
     */
    private void filterDriveTestsByVIN(){
        String filterVIN = this.filterDriveTestsVINReader();

        try {
            driveTestService.filterDriveTestByVIN(filterVIN).thenAcceptAsync(dts->dts.forEach(System.out::println));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads a VIN number from the keyboard and parses it.
     *
     * @throws RuntimeException
     *      if the number of given arguments does not match the number of object parameters.
     * @return
     *      the read VIN number.
     */
    private String filterDriveTestsVINReader(){
        System.out.println("Enter the VIN number: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            String inputLine = bufferRead.readLine();
            String[] lineAttributes = inputLine.split(",");

            if(lineAttributes.length!=1){
                throw new RuntimeException("Invalid number of attributes!");
            }

            String filterVIN = lineAttributes[0].strip();

            return filterVIN;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }




    //Report section



    /**
     * Prints the console menu for the Report funtionalities with the available procedures.
     */
    private void printReportMenu(){
        StringBuilder builder = new StringBuilder();

        builder.append("\n\n                 Reports\n");
        builder.append("1. Get the most tested car\n");
        builder.append("2. Get biggest tester\n");
        builder.append("\n");
        builder.append("0. Back\n");
        builder.append("##################\n");


        System.out.println(builder);
    }

    /**
     * Reads the command from the user and executes the matching procedure.
     * @return the running state of the program.
     */
    private Boolean getReportCommand(){
        boolean keepRunning=true;

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {
            String readLine  = bufferRead.readLine();
            String command =  readLine.strip();

            switch (command) {
                case "0" -> keepRunning = false;
                case "1" -> this.getMostTestedCar();
                case "2" -> this.getBiggestTester();

                default -> System.out.println("Wrong command!");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        return keepRunning;
    }

    private void getMostTestedCar() {
        try {
            carService.getMostTestedCar().thenAcceptAsync(c->System.out.println(c.toString()));
        } catch (RuntimeException e) {
           System.out.println(e.getMessage());
        }
    }

    private void getBiggestTester(){
        try {
            this.clientService.ReportBiggestTester().thenAcceptAsync(s->System.out.println(s.toString()));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

}

