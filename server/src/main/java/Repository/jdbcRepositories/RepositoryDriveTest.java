package Repository.jdbcRepositories;

import Domain.DriveTest;
import Domain.Validators.ValidatorException;
import Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

public class RepositoryDriveTest implements Repository<Long, DriveTest> {
    @Autowired
    private JdbcOperations jdbcOperations;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<DriveTest> findOne(Long aLong) {
        String sql = "select * from DriveTest where driveTestID=?";
        DriveTest result = jdbcOperations.queryForObject(sql,new Object[]{aLong}, (rs, rowNum) -> new DriveTest(
                rs.getLong("driveTestID"),
                rs.getLong("clientID"),
                rs.getLong("carID"),
                rs.getString("clientCNP"),
                rs.getString("carVIN"),
                rs.getInt("Rating")
        ));
        return Optional.ofNullable(result);
    }

    @Override
    public Iterable<DriveTest> findAll() {
        String sql = "select * from DriveTest";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new DriveTest(
                                rs.getLong("driveTestID"),
                                rs.getLong("clientID"),
                                rs.getLong("carID"),
                                rs.getString("clientCNP"),
                                rs.getString("carVIN"),
                                rs.getInt("Rating")
                        )
        );
    }

    /**
     * calculates the last id that is occupied in the database in its current form
     * @return the value of the last id
     */
    public Long getlastid(){
        Long maxid = 0L;
        Iterable<DriveTest> driveTests = this.findAll();
        for(DriveTest entity: driveTests){
            maxid = Math.max(maxid,entity.getId());
        }
        return maxid;
    }


    @Override
    public Optional<DriveTest> save(DriveTest entity) throws ValidatorException {
        String sql= "insert into DriveTest (driveTestID,clientID,carID,clientCNP,carVIN,Rating) values (?,?,?,?,?,?)";
        if(jdbcOperations.update(sql,(this.getlastid()+1),entity.getClientID(),entity.getCarID(),entity.getCnp(),entity.getVINNumber(),entity.getRating())>0){
            return Optional.empty();
        }
        else
            return Optional.of(entity);
    }

    @Override
    public Optional<DriveTest> delete(Long aLong) {
        String sql = "delete from DriveTest where driveTestID=?";
        Optional<DriveTest> entity = this.findOne(aLong);
        if(jdbcOperations.update(sql,aLong)==0)
            return Optional.empty();
        else
            return entity;
    }

    @Override
    public Optional<DriveTest> update(DriveTest entity) throws ValidatorException {
        String sql = "update DriveTest set clientID=?,carID=?,clientCNP=?,carVIN=?,Rating=? where driveTestID=?";
        if(jdbcOperations.update(sql,entity.getClientID(),entity.getCarID(),entity.getCnp(),entity.getVINNumber(),entity.getRating(),entity.getId())>0){
            return Optional.empty();
        }else
            return Optional.of(entity);
    }
}
