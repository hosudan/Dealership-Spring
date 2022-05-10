package Repository.xmlFileRepositories;


import Domain.BaseEntity;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;
import Repository.Repository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class XMLFileRepository<ID, T extends BaseEntity<ID>> implements Repository<ID,T> {
    private Map<ID, T> entities;
    private Validator<T> validator;
    private final String filePath;


    /**
     * Constructor for the XMLFileRepository.
     * @param validator
     *      given validator matching the entity type.
     * @param filePath
     *      file path to the xml file.
     */
    public XMLFileRepository(Validator<T> validator,String filePath) {
        this.validator = validator;
        entities = new HashMap<>();
        this.filePath=filePath;
    }

    public XMLFileRepository(Validator<T> validator) {
        this.validator = validator;
        entities = new HashMap<>();
        File file = new File("");
        this.filePath = file.getAbsolutePath()+"/FileRepo/XMLRepos/xmlRepo.xml";
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
        return Optional.empty();
    }

    /**
     * Gets all the entities in the repository.
     * @return
     *      a {@code Set} of the entities.
     */
    @Override
    public Iterable<T> findAll() {

        return null;
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
        return Optional.empty();
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
        return Optional.empty();
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
        return Optional.empty();
    }

    /**
     * Refreshes the entities.
     */
    private void refreshEntities(){
    //TODO: Delete these methods because are not used here

        try {
            Map<ID,T> tempMap = new HashMap<>();

            String lineFromFile;

            BufferedReader fileReader = new BufferedReader(new FileReader(this.filePath));

            while((lineFromFile = fileReader.readLine()) != null){
                /*

                 */
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

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
