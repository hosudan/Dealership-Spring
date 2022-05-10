package Repository.jdbcRepositories;

import Domain.Car;
import Domain.Validators.ValidatorException;
import IdGenerators.CarIdGenerator;
import Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

public class RepositoryCar implements Repository<Long, Car>{
    @Autowired
    private JdbcOperations jdbcOperations;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CarIdGenerator generator = null ;


    @Override
    public Optional<Car> findOne(Long aLong) {
        String sql = "select * from Car where carID=?";
        Car result = jdbcOperations.queryForObject(sql,new Object[]{aLong}, (rs, rowNum) -> new Car(
                rs.getString("carVIN"),
                rs.getString("carModel"),
                rs.getString("carManufacturer")
        ));
        result.setId(aLong);
        return Optional.ofNullable(result);
    }

    @Override
    public Iterable<Car> findAll() {
        String sql = "select * from Car";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new Car(
                                rs.getLong("carID"),
                                rs.getString("carVIN"),
                                rs.getString("carModel"),
                                rs.getString("carManufacturer")
                        )
        );
    }

    /**
     * calculates the last id that is occupied in the database in its current form
     * @return the value of the last id
     */
    public Long getlastid(){
        Long maxid = 0L;
        Iterable<Car> cars = this.findAll();
        for(Car entity: cars){
            maxid = Math.max(maxid,entity.getId());
        }
        return maxid;
    }

    @Override
    public Optional<Car> save(Car entity) throws ValidatorException {
        String sql= "insert into Car (carID,carVIN,carModel,carManufacturer) values (?,?,?,?)";
        if(jdbcOperations.update(sql,(this.getlastid()+1),entity.getVINNumber(),entity.getModel(),entity.getManufacturer())>0){
            return Optional.empty();
        }
        else
            return Optional.of(entity);
    }

    @Override
    public Optional<Car> delete(Long aLong) {
        String sql = "delete from Car where carID=?";
        Optional<Car> entity = this.findOne(aLong);
        if(jdbcTemplate.update(sql,aLong)==0)
            return Optional.empty();
        else
            return entity;
    }

    @Override
    public Optional<Car> update(Car entity) throws ValidatorException {
        String sql = "update Car set carVIN=?, carModel=?, carManufacturer=? where carID=?";
        if(jdbcOperations.update(sql,entity.getVINNumber(),entity.getModel(),entity.getManufacturer(),entity.getId())>0){
            return Optional.empty();
        }else
            return Optional.of(entity);
    }
}
