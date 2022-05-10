package Service;



import Domain.Employee;
import Domain.Validators.ValidatorException;
import IdGenerators.EmployeeIdGenerator;
import Repository.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 *
 */
public class EmployeeService implements IService<Long, Employee>{
    private Repository<Long, Employee> repository;

    /**
     * Constructor for the employee service.
     * @param repository
     *      is a matching repository for the Employee object.
     */
    public EmployeeService(Repository<Long,Employee> repository){
        this.repository = repository;
    }

    /**
     * parses the given Employee entity to the repository to be saved.
     * @param entity
     *      must not be null.
     * @throws ValidatorException
     *      if the given employee is not valid.
     */
    public void addEntity(Employee entity)throws ValidatorException {
        this.repository.save(entity);
    }

    /**
     * Updates the employee in the repository with the matching ID
     * @param entity
     *      must not be null;
     */
    public void updateEntity(Employee entity){
        this.repository.update(entity);
    }

    /**
     *Deletes the employees in the repository with the matching ID.
     * @param id
     *      must not be null.
     */
    public void deleteEntity(Long id) {
        this.repository.delete(id);
    }

    /**
     * Populates the repository with some model employees.
     */
    public void populateRepository(){
        EmployeeIdGenerator employeeIdGenerator = new EmployeeIdGenerator();
        for (int i=1;i<=5;i++){
            String cnp = "cnp000000001" + String.valueOf(i);
            String firstName = "First Name "+String.valueOf(i);
            String lastName = "Last Name "+String.valueOf(i);
            String role = "sales";

            Employee employee = new Employee(cnp,firstName,lastName,role);

            employee.setId(employeeIdGenerator.getId());
            repository.save(employee);
        }
    }

    /**
     * Gets all the saved employees.
     * @return
     *      a {@code Set} containing all the employees in the repository.
     */
    public Set<Employee> getAllEntities(){
        Iterable<Employee> employees = this.repository.findAll();
        return StreamSupport.stream(employees.spliterator(),false).collect(Collectors.toSet());
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
        Iterable<Employee> clients = this.repository.findAll();
        clients.forEach(client -> {
            if(client.getId() > lastId[0]){
                lastId[0] = client.getId();
            }
        });

        return lastId[0];
    }
}
