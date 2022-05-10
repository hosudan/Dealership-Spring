package Service;

import Domain.Client;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface ClientService extends Service{


    public CompletableFuture<Boolean> addClient(Client client);
    public CompletableFuture<Set<Client>> getAllClients();
    public CompletableFuture<Boolean> populateClients();
    public CompletableFuture<Boolean> deleteClient(Client client);
    public CompletableFuture<Boolean> updateClient(Client client);
    public CompletableFuture<Set<Client>> filtersortClients();
    public CompletableFuture<Client> ReportBiggestTester();
}
