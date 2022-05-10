package TCP;

import Domain.Client;
import Message.Message;
import Service.*;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class TCPClientServiceClient implements ClientService {
    private ExecutorService executorService;
    private TCPClient tcpClient;

    public TCPClientServiceClient(ExecutorService executorService, TCPClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    @Override
    public CompletableFuture<Boolean> addClient(Client client){
        Message message = new Message(Service.addClient, client.toTxt());
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
    public CompletableFuture<Set<Client>> getAllClients(){
        Message message = new Message(Service.getAllClients, "");
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (ServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        String body = response.body();
        String[] tokens = (body.split(System.lineSeparator()));
        Set<Client> clients = Arrays.stream(tokens).map(c->Client.ClientfromMessage(c)).collect(Collectors.toSet());
        //return executorService.submit(() -> students);
        return CompletableFuture.supplyAsync(
                ()-> clients,
                executorService
        );
    }

    @Override
    public CompletableFuture<Boolean> populateClients(){
        Message message = new Message(Service.populateClients,"");
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
    public CompletableFuture<Boolean> deleteClient(Client client){
        Message message = new Message(Service.deleteClient, client.toTxt());
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
    public CompletableFuture<Boolean> updateClient(Client client){
        Message message = new Message(Service.updateClient, client.toTxt());
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
    public CompletableFuture<Set<Client>> filtersortClients(){
        Message message = new Message(Service.sortClientsByFirstName, "");
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (ServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        String body = response.body();
        String[] tokens = (body.split(System.lineSeparator()));
        Set<Client> clients = Arrays.stream(tokens).map(c->Client.ClientfromMessage(c)).collect(Collectors.toSet());
        //return executorService.submit(() -> students);
        return CompletableFuture.supplyAsync(
                ()-> clients,
                executorService
        );
    }

    @Override
    public CompletableFuture<Client> ReportBiggestTester(){
        Message message = new Message(Service.ReportBiggestTester, "");
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
        Client cl = Client.ClientfromMessage(body);
        return CompletableFuture.supplyAsync(
                ()->cl,
                executorService
        );
    }
}
