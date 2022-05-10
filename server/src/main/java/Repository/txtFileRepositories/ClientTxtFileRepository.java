package Repository.txtFileRepositories;


import Domain.Client;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;

import java.io.*;
import java.util.*;

/**
 * Car-specific implementation of the TxtFileRepository class.
 */
public class ClientTxtFileRepository extends TxtFileRepository<Long, Client>{
    private final Map<Long, Client> entities;
    private final Validator<Client> validator;
    private final String filePath;


    /**
     * ClientTxtFileRepository constructor.
     * @param validator
     *      matching ClientValidator.
     * @param filePath
     *      a path to the txt file of the Client repository.
     */
    public ClientTxtFileRepository(Validator<Client> validator, String filePath) {
        super(validator, filePath);
        this.validator = validator;
        entities = new HashMap<>();
        this.filePath=filePath;
    }

    /**
     * ClientTxtFileRepository constructor.
     * @param validator
     *      matching ClientValidator.
     */
    public ClientTxtFileRepository(Validator<Client> validator) {
        super(validator);
        this.validator = validator;
        entities = new HashMap<>();
        File file = new File("");
        this.filePath=file.getAbsolutePath()+"/FileRepo/TxtRepos/"+"ClientRepository"+".txt";
    }


    /**
     * Finds the entity with the given {@code id}.
     * @param id
     *            must be not null.
     * @return
     *      an {@code Optional} encapsulation of the found entity.
     */
    @Override
    public Optional<Client> findOne(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        this.refreshEntities();
        return Optional.ofNullable(entities.get(id));
    }

    /**
     * Gets all the entities in the repository.
     * @return
     *      a {@code Set} of all the entities.
     */
    @Override
    public Iterable<Client> findAll() {
        this.refreshEntities();
        return new HashSet<>(entities.values());
    }

    /**
     * Saves the given {@code Car} entity.
     * @param entity
     *            must not be null.
     * @return
     *      an {@code Optional} encapsulation of the saved object.
     * @throws ValidatorException
     *      if the given entity is not valid.
     */
    @Override
    public Optional<Client> save(Client entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        Optional<Client> optional = null;
        try {
            this.refreshEntities();
            optional = Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
            this.refreshFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return optional;
    }

    /**
     * Deletes the entity with the matching {@code id}.
     * @param id
     *            must not be null.
     * @return
     *      an {@code Optional} encapsulation of the deleted entity.
     */
    @Override
    public Optional<Client> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        Optional<Client> optional = null;
        try {
            this.refreshEntities();
            optional = Optional.ofNullable(entities.remove(id));
            this.refreshFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return optional;
    }

    /**
     * Updates the entity with the matching {@code id}.
     * @param entity
     *            must not be null.
     * @return
     *      an {@code Optional} encapsulation of the updated entity.
     * @throws ValidatorException
     *      if the given entity is not valid.
     */
    @Override
    public Optional<Client> update(Client entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        Optional<Client> optional = null;
        try {
            this.refreshEntities();
            optional = Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
            this.refreshFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return optional;
    }


    /**
     * Refreshes the entities.
     */
    private void refreshEntities(){
        try {
            this.entities.clear();

            String lineFromFile;

            BufferedReader fileReader = new BufferedReader(new FileReader(this.filePath));

            while((lineFromFile = fileReader.readLine()) != null){
                Client tempClient = new Client();
                tempClient.fromTxt(lineFromFile);
                this.entities.put(tempClient.getId(),tempClient);
            }

            fileReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Loads the file with all the entities.
     * @throws IOException
     *      is there is an IO error.
     */
    private void refreshFile() throws IOException {
        this.clearRepoFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath));
        for(Client entity : this.entities.values()){
            writer.append(entity.toTxt());
            writer.append(System.lineSeparator());
        }
        writer.close();
    }

    /**
     * Clears the repository file.
     */
    private void clearRepoFile(){
        try {
            PrintWriter writer = new PrintWriter(this.filePath);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
