package TCP;


import Domain.Car;
import Domain.Client;
import Message.Message;
import Service.*;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class TCPClientServiceCar implements CarService {
    private ExecutorService executorService;
    private TCPClient tcpClient;

    public TCPClientServiceCar(ExecutorService executorService, TCPClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    @Override
    public CompletableFuture<Boolean> addCar(Car car){
        Message message = new Message(Service.addCar, car.toTxt());
        String body = "";
        String header = "";
        try {
            Message response = tcpClient.sendAndReceive(message);
            body = response.body();
            header = response.header();
        }catch(ServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        if (header.equals("error"))
            throw new RuntimeException(body);
        return CompletableFuture.supplyAsync(
                ()->true,
                executorService
        );
    }

    @Override
    public CompletableFuture<Set<Car>> getAllCars(){
        Message message = new Message(Service.getAllCars, "");
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (ServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        String body = response.body();
        String[] tokens = (body.split(System.lineSeparator()));
        Set<Car> cars = Arrays.stream(tokens).map(c->Car.CarfromMessage(c)).collect(Collectors.toSet());
        //return executorService.submit(() -> students);
        return CompletableFuture.supplyAsync(
                ()-> cars,
                executorService
        );
    }

    @Override
    public CompletableFuture<Boolean> populateCars(){
        Message message = new Message(Service.populateCars,"");
        String body = "";
        String header = "";
        try {
            Message response = tcpClient.sendAndReceive(message);
            body = response.body();
            header = response.header();
        }catch(ServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        if (header.equals("error"))
            throw new RuntimeException(body);
        return CompletableFuture.supplyAsync(
                ()->true,
                executorService
        );
    }

    @Override
    public CompletableFuture<Boolean> deleteCar(Car car){
        Message message = new Message(Service.deleteCar, car.toTxt());
        String body = "";
        String header = "";
        try {
            Message response = tcpClient.sendAndReceive(message);
            body = response.body();
            header = response.header();
        }catch(ServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        if (header.equals("error"))
            throw new RuntimeException(body);
        return CompletableFuture.supplyAsync(
                ()->true,
                executorService
        );
    }

    @Override
    public CompletableFuture<Boolean> updateCar(Car car){
        Message message = new Message(Service.updateCar, car.toTxt());
        String body = "";
        String header = "";
        try {
            Message response = tcpClient.sendAndReceive(message);
            body = response.body();
            header = response.header();
        }catch(ServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        if (header.equals("error"))
            throw new RuntimeException(body);
        return CompletableFuture.supplyAsync(
                ()->true,
                executorService
        );
    }

    @Override
    public CompletableFuture<Set<Car>> filterCarsByModel(String filtermodel){
        Message message = new Message(Service.filterCarsByModel, filtermodel);
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (ServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        String body = response.body();
        String[] tokens = (body.split(System.lineSeparator()));
        Set<Car> cars = Arrays.stream(tokens).map(c->Car.CarfromMessage(c)).collect(Collectors.toSet());
        //return executorService.submit(() -> students);
        return CompletableFuture.supplyAsync(
                ()-> cars,
                executorService
        );
    }

    @Override
    public CompletableFuture<Set<Car>> filterCarsByManufacturer(String filtermanufacturer){
        Message message = new Message(Service.filterCarsByManufacturer, filtermanufacturer);
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (ServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        String body = response.body();
        String[] tokens = (body.split(System.lineSeparator()));
        Set<Car> cars = Arrays.stream(tokens).map(c->Car.CarfromMessage(c)).collect(Collectors.toSet());
        //return executorService.submit(() -> students);
        return CompletableFuture.supplyAsync(
                ()-> cars,
                executorService
        );
    }

    @Override
    public CompletableFuture<Car> getMostTestedCar(){
        Message message = new Message(Service.ReportMostTestedCars, "");
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (ServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        String body = response.body();
        Car car = Car.CarfromMessage(body);
        return CompletableFuture.supplyAsync(
                ()-> car,
                executorService
        );
    }

}
