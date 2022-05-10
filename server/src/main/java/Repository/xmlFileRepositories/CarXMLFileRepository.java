package Repository.xmlFileRepositories;


import Domain.Car;
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
public class CarXMLFileRepository extends XMLFileRepository<Long, Car>{
    private final Map<Long, Car> entities;
    private final Validator<Car> validator;
    private final String filePath;


    /**
     * CarTxtFileRepository constructor.
     * @param validator
     *      matching CaValidator.
     * @param filePath
     *      a path to the txt file of the Car repository.
     */
    public CarXMLFileRepository(Validator<Car> validator, String filePath) {
        super(validator, filePath);
        this.validator = validator;
        entities = new HashMap<>();
        this.filePath=filePath;
    }

    /**
     * CarXMLFileRepository constructor.
     * @param validator
     *      matching CarValidator.
     */
    public CarXMLFileRepository(Validator<Car> validator) {
        super(validator);
        this.validator = validator;
        entities = new HashMap<>();
        File file = new File("");
        this.filePath=file.getAbsolutePath()+"/FileRepo/XMLRepos/"+"CarRepository"+".xml";
    }


    /**
     * Finds the entity with the given {@code id}.
     * @param id
     *            must be not null.
     * @return
     *      an {@code Optional} encapsulation of the found entity.
     */
    @Override
    public Optional<Car> findOne(Long id) {
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
    public Iterable<Car> findAll()
    {
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
    public Optional<Car> save(Car entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        Optional<Car> optional=null;
        try{
            this.refreshEntities();
            optional=Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
            this.refreshFile();
        }catch(Exception e)
        {
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
    public Optional<Car> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        Optional<Car> optional = null;
        try {
            this.refreshEntities();
            optional = Optional.ofNullable(entities.remove(id));
            this.refreshFile();
        } catch (Exception e) {
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
    public Optional<Car> update(Car entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        Optional<Car> optional = null;

        try {
            this.refreshEntities();
            optional = Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
            this.refreshFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return optional;
    }

    /**
     * Refreshes the xml file
     */
    private void refreshFile()
    {
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("Cars");
            doc.appendChild(rootElement);
            this.entities.values().stream().forEach(car -> {
                rootElement.appendChild(car.toXml(doc));
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
     * Refreshes the entities.
     */
    private void refreshEntities()
    {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(this.filePath);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Car");
            Stream.iterate(0, i -> i + 1)
                    .limit (nList.getLength())
                    .map (nList::item).forEach(node -> {
                Car tempCar=new Car();
                tempCar.fromXml(node);
                this.entities.put(tempCar.getId(),tempCar);
            });

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
