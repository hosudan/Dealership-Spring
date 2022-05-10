package Repository.xmlFileRepositories;


import Domain.Employee;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Car-specific implementation of the TxtFileRepository class.
 */
public class EmployeeXMLFileRepository extends XMLFileRepository<Long, Employee>{
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
    public EmployeeXMLFileRepository(Validator<Employee> validator, String filePath) {
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
    public EmployeeXMLFileRepository(Validator<Employee> validator) {
        super(validator);
        this.validator = validator;
        entities = new HashMap<>();
        File file = new File("");
        this.filePath=file.getAbsolutePath()+"/FileRepo/XMLRepos/"+"EmployeeRepository"+".xml";
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
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(this.filePath);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Employee");
            Stream.iterate(0, i -> i + 1)
                    .limit (nList.getLength())
                    .map (nList::item).forEach(node -> {
                Employee tempEmployee=new Employee();
                tempEmployee.fromXml(node);
                this.entities.put(tempEmployee.getId(),tempEmployee);
            });

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Refreshes the xml file
     */
    private void refreshFile() throws IOException {
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("Employees");
            doc.appendChild(rootElement);
            this.entities.values().stream().forEach(client -> {
                rootElement.appendChild(client.toXml(doc));
            });
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(this.filePath));
            transformer.transform(source, result);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
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