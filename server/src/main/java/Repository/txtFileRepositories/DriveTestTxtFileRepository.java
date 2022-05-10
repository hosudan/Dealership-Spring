package Repository.txtFileRepositories;


import Domain.DriveTest;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;

import java.io.*;
import java.util.*;

/**
 * Car-specific implementation of the TxtFileRepository class.
 */
public class DriveTestTxtFileRepository extends TxtFileRepository<Long, DriveTest>{
    private final Map<Long, DriveTest> entities;
    private final Validator<DriveTest> validator;
    private final String filePath;


    /**
     * DriveTestTxtFileRepository constructor.
     * @param validator
     *      matching DriveTestValidator.
     * @param filePath
     *      a path to the txt file of the DriveTest repository.
     */
    public DriveTestTxtFileRepository(Validator<DriveTest> validator, String filePath) {
        super(validator, filePath);
        this.validator = validator;
        entities = new HashMap<>();
        this.filePath=filePath;
    }

    /**
     * DriveTestTxtFileRepository constructor.
     * @param validator
     *      matching DriveTestValidator.
     */
    public DriveTestTxtFileRepository(Validator<DriveTest> validator) {
        super(validator);
        this.validator = validator;
        entities = new HashMap<>();
        File file = new File("");
        this.filePath=file.getAbsolutePath()+"/FileRepo/TxtRepos/"+"DriveTestRepository"+".txt";
    }


    /**
     * Finds the entity with the given {@code id}.
     * @param id
     *            must be not null.
     * @return
     *      an {@code Optional} encapsulation of the found entity.
     */
    @Override
    public Optional<DriveTest> findOne(Long id) {
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
    public Iterable<DriveTest> findAll() {
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
    public Optional<DriveTest> save(DriveTest entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        Optional<DriveTest> optional = null;
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
    public Optional<DriveTest> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        Optional<DriveTest> optional = null;
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
    public Optional<DriveTest> update(DriveTest entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        Optional<DriveTest> optional = null;
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
                DriveTest tempDriveTest = new DriveTest();
                tempDriveTest.fromTxt(lineFromFile);
                this.entities.put(tempDriveTest.getId(),tempDriveTest);
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
        for(DriveTest entity : this.entities.values()){
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
