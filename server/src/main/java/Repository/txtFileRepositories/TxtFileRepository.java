package Repository.txtFileRepositories;

import Domain.BaseEntity;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;
import Repository.Repository;

import java.io.*;
import java.util.*;

/**
 * Base text repository.
 * @param <ID>
 *     entity ID type.
 * @param <T>
 *     entity type.
 */
public abstract class TxtFileRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {

    private Map<ID, T> entities;
    private Validator<T> validator;
    private final String filePath;

    /**
     * Constructor for the TxtFileRepository.
     * @param validator
     *      given validator matching the entity type.
     * @param filePath
     *      file path to the txt file.
     */
    public TxtFileRepository(Validator<T> validator,String filePath) {
        this.validator = validator;
        entities = new HashMap<>();
        this.filePath=filePath;
    }

    /**
     * Constructor for the TxtFileRepository.
     * @param validator
     *      given validator matching the entity type.
     */
    public TxtFileRepository(Validator<T> validator) {
        this.validator = validator;
        entities = new HashMap<>();
        File file = new File("");
        this.filePath = file.getAbsolutePath()+"/FileRepo/TxtRepos/txtRepo.txt";
    }


    /**
     * Finds the entity with the given id.
     * @param id
     *            must be not null.
     * @return
     *      an {@code Optional} encapsulating the found object.
     */
    @Override
    public Optional<T> findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        this.refreshEntities();
        return Optional.ofNullable(entities.get(id));
    }

    /**
     * Gets all the entities in the repository.
     * @return
     *      a {@code Set} of the entities.
     */
    @Override
    public Iterable<T> findAll() {
        return new HashSet<>(entities.values());
    }

    /**
     * Saves the given entity.
     * @param entity
     *            must not be null.
     * @return
     *      an {@code Optional} encapsulation of the saved entity
     * @throws ValidatorException
     *      if the entity is not valid.
     */
    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    /**
     * Deletes the entity with the matching id.
     * @param id
     *            must not be null.
     * @return
     *      an {@code Optional} encapsulation of the deleted entity.
     */
    @Override
    public Optional<T> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.remove(id));
    }

    /**
     * Updates the matching entity with new attributes.
     * @param entity
     *            must not be null.
     * @return
     *      an {@code Optional} encapsulation of the updated entity.
     * @throws ValidatorException
     *      if the new entity is not valid.
     */
    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }

    /**
     * Refreshes the entities.
     */
    private void refreshEntities(){

        try {
            Map<ID,T> tempMap = new HashMap<>();

            String lineFromFile;

            BufferedReader fileReader = new BufferedReader(new FileReader(this.filePath));

            //Implement for each entity

            while((lineFromFile = fileReader.readLine()) != null){
                /*

                Does not work with switch!
                switch (this.validator.getType()) {
                    case Car.class -> {

                    }
                    case Client.class ->{

                    }
                    case DriveTest.class->{

                    }
                    case Employee.class->{

                    }
                    case Sale.class->{

                    }
                }


                Class<T> type = this.validator.getType();
                if (Car.class.equals(type)) {
                    Car newEntity = new Car();
                    newEntity.fromTxt(lineFromFile);
                    entities.putIfAbsent((ID)newEntity.getId(), (T)newEntity);
                }
                else if (Client.class.equals(type)) {
                    Client newEntity = new Client();
                    newEntity.fromTxt(lineFromFile);
                    entities.putIfAbsent((ID)newEntity.getId(), (T)newEntity);
                }
                else if (DriveTest.class.equals(type)) {
                    DriveTest newEntity = new DriveTest();
                    newEntity.fromTxt(lineFromFile);
                    entities.putIfAbsent((ID)newEntity.getId(), (T)newEntity);
                }
                else if (Employee.class.equals(type)) {
                    Employee newEntity = new Employee();
                    newEntity.fromTxt(lineFromFile);
                    entities.putIfAbsent((ID)newEntity.getId(), (T)newEntity);
                }
                else if (Sale.class.equals(type)) {
                    Sale newEntity = new Sale();
                    newEntity.fromTxt(lineFromFile);
                    entities.putIfAbsent((ID)newEntity.getId(), (T)newEntity);
                }
                */

            }


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
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath));
        this.clearRepoFile();

        //Implement for each repo entity;

        this.entities.forEach((id,entity)->{

        });
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
