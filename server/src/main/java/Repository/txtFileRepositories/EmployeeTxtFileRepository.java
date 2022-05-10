package Repository.txtFileRepositories;



import Domain.Employee;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;

import java.io.*;
import java.util.*;

/**
 * Car-specific implementation of the TxtFileRepository class.
 */
public class EmployeeTxtFileRepository extends TxtFileRepository<Long, Employee>{
    private final Map<Long, Employee> entities;
    private final Validator<Employee> validator;
    private final String filePath;


    /**
     * EmployeeTxtFileRepository constructor.
     * @param validator
     *      matching EmployeeValidator.
     * @param filePath
     *      a path to the txt file of the Employee repository.
     */
    public EmployeeTxtFileRepository(Validator<Employee> validator, String filePath) {
        super(validator, filePath);
        this.validator = validator;
        entities = new HashMap<>();
        this.filePath=filePath;
    }

    /**
     * EmployeeTxtFileRepository constructor.
     * @param validator
     *      matching EmployeeValidator.
     */
    public EmployeeTxtFileRepository(Validator<Employee> validator) {
        super(validator);
        this.validator = validator;
        entities = new HashMap<>();
        File file = new File("");
        this.filePath=file.getAbsolutePath()+"/FileRepo/TxtRepos/"+"EmployeeRepository"+".txt";
    }


    /**
     * Finds the entity with the given {@code id}.
     * @param id
     *            must be not null.
     * @return
     *      an {@code Optional} encapsulation of the found entity.
     */
    @Override
    public Optional<Employee> findOne(Long id) {
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
    public Iterable<Employee> findAll() {
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
    public Optional<Employee> save(Employee entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        Optional<Employee> optional = null;
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
    public Optional<Employee> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        Optional<Employee> optional = null;
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
    public Optional<Employee> update(Employee entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        Optional<Employee> optional = null;
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
                Employee tempEmployee = new Employee();
                tempEmployee.fromTxt(lineFromFile);
                this.entities.put(tempEmployee.getId(),tempEmployee);
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
        for(Employee entity : this.entities.values()){
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
