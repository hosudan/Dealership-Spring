package Service;




import Domain.Client;
import Domain.Validators.ValidatorException;
import IdGenerators.ClientIdGenerator;
import Repository.Repository;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Generic service for the Client object that works on a matching repository
 */
public class ClientService implements IService<Long, Client>{
    private Repository<Long, Client> repository;

    /**
     * Constructor for the client service.
     * @param repository
     *      is a matching repository for the Client object.
     */
    public ClientService(Repository<Long,Client> repository){
        this.repository = repository;
    }

    /**
     * parses the given Client entity to the repository to be saved.
     * @param entity
     *      must not be null.
     * @throws ValidatorException
     *      if the given client is not valid.
     */
    public void addEntity(Client entity)throws ValidatorException {
        this.repository.save(entity);
    }

    /**
     * Updates the client in the repository with the matching ID
     * @param entity
     *      must not be null;
     */
    public void updateEntity(Client entity){
        this.repository.update(entity);
    }

    /**
     *Deletes the clients in the repository with the matching ID.
     * @param id
     *      must not be null.
     */
    public void deleteEntity(Long id) {
        this.repository.delete(id);
    }

    /**
     * Populates the repository with some model clients.
     */
    public void populateRepository(){
        ClientIdGenerator clientIdGenerator = new ClientIdGenerator();
        for (int i=1;i<=5;i++){
            String cnp = "cnp000000000" + String.valueOf(i);
            String firstName = "First Name "+String.valueOf(i);
            String lastName = "Last Name "+String.valueOf(i);
            Integer age = i;

            Client client = new Client(cnp,firstName,lastName,age);

            client.setId(clientIdGenerator.getId());
            repository.save(client);
        }
    }

    /**
     * Gets all the saved clients.
     * @return
     *      a {@code Set} containing all the clients in the repository.
     */
    public Set<Client> getAllEntities(){
        Iterable<Client> clients = this.repository.findAll();
        return StreamSupport.stream(clients.spliterator(),false).collect(Collectors.toSet());
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
        Iterable<Client> clients = this.repository.findAll();
        clients.forEach(client -> {
            if(client.getId() > lastId[0]){
                lastId[0] = client.getId();
            }
        });

        return lastId[0];
    }

    public String getCnpByID(Long id){
        Iterable<Client> clients = this.repository.findAll();
        AtomicReference<String> cnp = new AtomicReference<>("");
        clients.forEach(client -> {
            if(client.getId().equals(id)){
                cnp.set(client.getCnp());
            }
        });
        return cnp.get();
    }
}
