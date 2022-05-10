package TCP;

import Domain.Client;
import Domain.DriveTest;
import Message.Message;
import Service.*;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class TCPClientServiceDriveTest implements DriveTestService {
    private ExecutorService executorService;
    private TCPClient tcpClient;

    public TCPClientServiceDriveTest(ExecutorService executorService, TCPClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    @Override
    public CompletableFuture<Boolean> addDriveTest(DriveTest dt){
        Message message = new Message(Service.addDriveTest, dt.toTxt());
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
    public CompletableFuture<Set<DriveTest>> getAllDriveTests(){
        Message message = new Message(Service.getAllDriveTests, "");
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (ServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        String body = response.body();
        String[] tokens = (body.split(System.lineSeparator()));
        Set<DriveTest> drivetests = Arrays.stream(tokens).map(c->DriveTest.DriveTestfromMessage(c)).collect(Collectors.toSet());
        //return executorService.submit(() -> students);
        return CompletableFuture.supplyAsync(
                ()-> drivetests,
                executorService
        );
    }

    @Override
    public CompletableFuture<Boolean> populateDriveTests(){
        Message message = new Message(Service.populateDriveTests,"");
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
    public CompletableFuture<Boolean> deleteDriveTest(DriveTest dt){
        Message message = new Message(Service.deleteDriveTest, dt.toTxt());
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
    public CompletableFuture<Boolean> updateDriveTest(DriveTest dt){
        Message message = new Message(Service.updateDriveTest, dt.toTxt());
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
    public CompletableFuture<Set<DriveTest>> filterDriveTestByCNP(String cnp){
        Message message = new Message(Service.filterDriveTestsByCNP, cnp);
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (ServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        String body = response.body();
        String[] tokens = (body.split(System.lineSeparator()));
        Set<DriveTest> drivetests = Arrays.stream(tokens).map(c->DriveTest.DriveTestfromMessage(c)).collect(Collectors.toSet());
        //return executorService.submit(() -> students);
        return CompletableFuture.supplyAsync(
                ()-> drivetests,
                executorService
        );
    }

    @Override
    public CompletableFuture<Set<DriveTest>> filterDriveTestByRating(Integer rating){
        Message message = new Message(Service.filterDriveTestByRating, ""+rating);
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (ServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        String body = response.body();
        String[] tokens = (body.split(System.lineSeparator()));
        Set<DriveTest> drivetests = Arrays.stream(tokens).map(c->DriveTest.DriveTestfromMessage(c)).collect(Collectors.toSet());
        //return executorService.submit(() -> students);
        return CompletableFuture.supplyAsync(
                ()-> drivetests,
                executorService
        );
    }

    @Override
    public CompletableFuture<Set<DriveTest>> filterDriveTestByVIN(String vin){
        Message message = new Message(Service.filterDriveTestsByVin, vin);
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (ServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        String body = response.body();
        String[] tokens = (body.split(System.lineSeparator()));
        Set<DriveTest> drivetests = Arrays.stream(tokens).map(c->DriveTest.DriveTestfromMessage(c)).collect(Collectors.toSet());
        //return executorService.submit(() -> students);
        return CompletableFuture.supplyAsync(
                ()-> drivetests,
                executorService
        );
    }

}
