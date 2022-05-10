package Repository;

import Domain.BaseEntity;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;
import IdGenerators.IdGenerator;

import java.util.*;

/**
 * In memory typed repository.
 * @param <ID>
 *          represents the ID of the given object entity type.
 * @param <T>
 *          represents the given object entity type to be saved in the repository.
 */
public class InMemoryRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {

    private final Map<ID, T> entities;
    private final Validator<T> validator;
    private IdGenerator idGenerator;

    /**
     * Constructor for the memory repository.
     * @param validator
     *          matching validator for the given entity type.
     */
    public InMemoryRepository(Validator<T> validator) {
        this.validator = validator;
        entities = new HashMap<>();
    }

    /**
     * Finds the entity with the given {@code id}.
     * @param id
     *            must be not null.
     * @return
     *          an {@code Optional} encapsulating the entity with the given id.
     * @throws
     *          IllegalArgumentException if the given id is null.
     */
    @Override
    public Optional<T> findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.get(id));
    }

    /**
     * Return all the entities.
     * @return
     *      an {@code Set} of given entity type.
     */
    @Override
    public Iterable<T> findAll() {
        return new HashSet<>(entities.values());
    }

    /**
     *Saves the given entity
     * @param entity
     *            must not be null.
     * @return
     *          an {@code Optional} encapsulating the given entity.
     * @throws ValidatorException
     *          if the given entity is not valid.
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
     *Deletes the given entity,
     * @param id
     *            must not be null.
     * @return
     *          an {@code Optional} of the removed entity.
     */
    @Override
    public Optional<T> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.remove(id));
    }

    /**
     * Updates an matching entity in memory with the given one.
     * @param entity
     *            must not be null.
     * @return
     *          an {@code Optional} of the updated entity.
     * @throws ValidatorException
     *          if the new entity is not valid.
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
     * Return the number of entities.
     * @return
     *          the size of the entities {@code HashMap}.
     */
    public int size(){
        return this.entities.size();
    }

    /**
     * Sets the IdGenerator for the repository.
     * @param generator
     *          should be a matching IdGenerator for the given entity type.
     * @throws RuntimeException
     *          if the idGenerator has already been set.
     */
    public void setIdGenerator(IdGenerator generator){
        if(this.idGenerator!=null){
            throw new RuntimeException("Id generator is already set!");
        }
        this.idGenerator=generator;
    }

    /**
     * Gets the next available ID.
     * @return
     *          the next available ID of the given generator.
     */
    public ID getNextId(){
        return (ID)this.idGenerator.getId();
    }
}