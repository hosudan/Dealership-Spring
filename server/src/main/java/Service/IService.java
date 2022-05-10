package Service;

import Domain.BaseEntity;

import java.util.Set;

public interface IService<ID,T extends BaseEntity<ID>>{

    public void addEntity(T entity);
    public void updateEntity(T entity);
    public void deleteEntity(ID id);
    public Set<T> getAllEntities();
    public ID getLastId();
}
