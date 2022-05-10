package TCP;

import Domain.Car;
import Domain.Client;
import Domain.DriveTest;
import Domain.Validators.ValidatorException;
import Message.Message;
import Service.MainController;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class MessageHandler {
    private final MainController service;
    private final String ok = "ok";
    private final String error="error";

    public MessageHandler(MainController service){
        this.service = service;
    }


    //Car section

    public Message addCar(Message message){
        Car car = new Car();
        car.fromTxt(message.body());

        try{
            String computedanswer = this.service.add(car).get();
            if(computedanswer.equals("")) {
                return new Message(ok, ok);
            }else return new Message(error,computedanswer);
        }
        catch (InterruptedException | ExecutionException | ValidatorException e) {
            return new Message(error, e.getMessage());
        }
    }

    public Message updateCar(Message message){
        Car car = new Car();
        car.fromTxt(message.body());

        try {
            String computedanswer = this.service.update(car).get();
            if(computedanswer.equals("")) {
                return new Message(ok, ok);
            }else return new Message(error,computedanswer);
        }
        catch (InterruptedException | ExecutionException | ValidatorException e) {
            return new Message(error, e.getMessage());
        }
    }

    public Message deleteCar(Message message){
        Car car = new Car();
        car.fromTxt(message.body());
        try {
            if(this.service.delete(car).get()) {
                return new Message(ok, ok);
            }else return new Message(error,"Unable to delete the entity.");
        }
        catch (Exception e){
            return new Message(error,e.getMessage());
        }
    }

    public Message getAllCars(Message message){
        try {
            Set<Car> result =  service.getAllCars().get();
            String messageBody = result.stream()
                    .map(Car::toTxt)
                    .reduce("", (s,e) -> s + e + System.lineSeparator());
            return new Message(ok, messageBody);
        }
        catch (InterruptedException | ExecutionException e) {
            return new Message(error, e.getMessage());
        }
    }

    public Message filterCarsByManufacturer(Message message){
        try {
            Set<Car> result =  service.filterCarsByManufacturer(message.body()).get();
            String messageBody = result.stream()
                    .map(Car::toTxt)
                    .reduce("", (s,e) -> s + e + System.lineSeparator());
            return new Message(ok, messageBody);
        }
        catch (InterruptedException | ExecutionException e) {
            return new Message(error, e.getMessage());
        }
    }

    public Message filterCarsByModel (Message message){
        try {
            Set<Car> result =  service.filterCarsByModel(message.body()).get();
            String messageBody = result.stream()
                    .map(Car::toTxt)
                    .reduce("", (s,e) -> s + e + System.lineSeparator());
            return new Message(ok, messageBody);
        }
        catch (InterruptedException | ExecutionException e) {
            return new Message(error, e.getMessage());
        }
    }

    public Message reportMostTestedCar(Message message){
        try{
            Car car = service.ReportMostTestedCars().get();
            return new Message(ok,car.toTxt());
        } catch (InterruptedException | ExecutionException e) {
            return new Message(error, e.getMessage());
        }
    }

    public Message populateCars(Message message){
        service.populateCars();
        return new Message(ok,ok);
    }



    //Client section

    public Message addClient(Message message){
        Client client = new Client();
        client.fromTxt(message.body());

        try{
            String computedanswer = this.service.add(client).get();
            if(computedanswer.equals("")) {
                return new Message(ok, ok);
            }else return new Message(error,computedanswer);
        }
        catch (InterruptedException | ExecutionException | ValidatorException e) {
            return new Message(error, e.getMessage());
        }
    }

    public Message updateClient(Message message){
        Client client = new Client();
        client.fromTxt(message.body());

        try {
            String computedanswer = this.service.update(client).get();
            if(computedanswer.equals("")) {
                return new Message(ok, ok);
            }else return new Message(error,computedanswer);
        }
        catch (InterruptedException | ExecutionException | ValidatorException e) {
            return new Message(error, e.getMessage());
        }
    }

    public Message deleteClient(Message message){
        Client client = new Client();
        client.fromTxt(message.body());

        try {
            if(this.service.delete(client).get()) {
                return new Message(ok, ok);
            }else return new Message(error,"Unable to delete the entity.");
        }
        catch (ValidatorException |InterruptedException | ExecutionException e){
            return new Message(error,e.getMessage());
        }
    }

    public Message getAllClient(Message message){
        try {
            Set<Client> result =  service.getAllClients().get();
            String messageBody = result.stream()
                    .map(Client::toTxt)
                    .reduce("", (s,e) -> s + e + System.lineSeparator());
            return new Message(ok, messageBody);
        }
        catch (InterruptedException | ExecutionException e) {
            return new Message(error, e.getMessage());
        }
    }

    public Message sortClientsByFirstName(Message message){
        try {
            List<Client> result =  service.filterclientsfn().get();
            String messageBody = result.stream()
                    .map(Client::toTxt)
                    .reduce("", (s,e) -> s + e + System.lineSeparator());
            return new Message(ok, messageBody);
        }
        catch (InterruptedException | ExecutionException e) {
            return new Message(error, e.getMessage());
        }
    }

    public Message reportBiggestTester(Message message){
        try{
            Client client = service.ReportBiggestTester().get();
            return new Message(ok,client.toTxt());
        } catch (InterruptedException | ExecutionException e) {
            return new Message(error, e.getMessage());
        }
    }

    public Message populateClients(Message message){
        service.populateClients();
        return new Message(ok,ok);
    }


    //Drive Test section



    public Message addDriveTest(Message message){
        DriveTest driveTest = new DriveTest();
        driveTest.fromTxt(message.body());
        try{
            String computedanswer = this.service.add(driveTest).get();
            if(computedanswer.equals("")) {
                return new Message(ok, ok);
            }else return new Message(error,computedanswer);
        }
        catch (InterruptedException | ExecutionException | ValidatorException e) {
            return new Message(error, e.getMessage());
        }
    }

    public Message updateDriveTest(Message message){
        DriveTest driveTest = new DriveTest();
        driveTest.fromTxt(message.body());

        try {
            String computedanswer = this.service.update(driveTest).get();
            if(computedanswer.equals("")) {
                return new Message(ok, ok);
            }else return new Message(error,computedanswer);
        }
        catch (InterruptedException | ExecutionException | ValidatorException e) {
            return new Message(error, e.getMessage());
        }
    }

    public Message deleteDriveTest(Message message){
        DriveTest driveTest = new DriveTest();
        driveTest.fromTxt(message.body());
        try {
            if(this.service.delete(driveTest).get()) {
                return new Message(ok, ok);
            }else return new Message(error,"Unable to delete the entity.");
        }
        catch (ValidatorException| InterruptedException | ExecutionException e){
            return new Message(error,e.getMessage());
        }
    }

    public Message getAllDriveTests(Message message){
        try {
            Set<DriveTest> result =  service.getAllDriveTests().get();
            String messageBody = result.stream()
                    .map(DriveTest::toTxt)
                    .reduce("", (s,e) -> s + e + System.lineSeparator());
            return new Message(ok, messageBody);
        }
        catch (InterruptedException | ExecutionException e) {
            return new Message(error, e.getMessage());
        }
    }

    public Message filterDriveTestsByCNP(Message message){
        try {
            Set<DriveTest> result =  service.filterDriveTestsByCnp(message.body()).get();
            String messageBody = result.stream()
                    .map(DriveTest::toTxt)
                    .reduce("", (s,e) -> s + e + System.lineSeparator());
            return new Message(ok, messageBody);
        }
        catch (InterruptedException | ExecutionException e) {
            return new Message(error, e.getMessage());
        }
    }

    public Message filterDriveTestsByVIN(Message message){
        try {
            Set<DriveTest> result =  service.filterDriveTestsByVin(message.body()).get();
            String messageBody = result.stream()
                    .map(DriveTest::toTxt)
                    .reduce("", (s,e) -> s + e + System.lineSeparator());
            return new Message(ok, messageBody);
        }
        catch (InterruptedException | ExecutionException e) {
            return new Message(error, e.getMessage());
        }
    }

    public Message filterDriveTestsByRating(Message message){
        try {
            Set<DriveTest> result =  service.filterDriveTestsByRating(Integer.parseInt(message.body())).get();
            String messageBody = result.stream()
                    .map(DriveTest::toTxt)
                    .reduce("", (s,e) -> s + e + System.lineSeparator());
            return new Message(ok, messageBody);
        }
        catch (InterruptedException | ExecutionException e) {
            return new Message(error, e.getMessage());
        }
    }

    public Message populateDriveTests(Message message){
        service.populateClients();
        return new Message(ok,ok);
    }

    public Message getDriveTestId(Message message){
        try {
            Long id = service.getDriveTestID().get();
            return new Message(ok,id.toString());
        } catch (InterruptedException | ExecutionException e) {
            return new Message(error,e.getMessage());
        }
    }




}
