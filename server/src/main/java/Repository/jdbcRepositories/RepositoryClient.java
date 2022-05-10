package Repository.jdbcRepositories;


import Domain.Client;
import Domain.Validators.ValidatorException;
import Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

public class RepositoryClient implements Repository<Long, Client>{
    @Autowired
    private JdbcOperations jdbcOperations;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Client> findOne(Long aLong) {
        String sql = "select * from Client where clientID=?";
        Client result = jdbcOperations.queryForObject(sql,new Object[]{aLong}, (rs, rowNum) -> new Client(
                rs.getLong("clientID"),
                rs.getString("clientCNP"),
                rs.getString("clientFirstName"),
                rs.getString("clientLastName"),
                rs.getInt("clientAge")
        ));
        return Optional.ofNullable(result);
    }

    @Override
    public Iterable<Client> findAll() {
        String sql = "select * from Client";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new Client(
                                rs.getLong("clientID"),
                                rs.getString("clientCNP"),
                                rs.getString("clientFirstName"),
                                rs.getString("clientLastName"),
                                rs.getInt("clientAge")
                        )
        );
    }

    /**
     * calculates the last id that is occupied in the database in its current form
     * @return the value of the last id
     */
    public Long getlastid(){
        Long maxid = 0L;
        Iterable<Client> clients = this.findAll();
        for(Client entity: clients){
            maxid = Math.max(maxid,entity.getId());
        }
        return maxid;
    }


    @Override
    public Optional<Client> save(Client entity) throws ValidatorException {
        String sql= "insert into Client (clientID,clientCNP,clientFirstName,clientLastName,clientAge) values (?,?,?,?,?)";
        if(jdbcOperations.update(sql,(this.getlastid()+1),entity.getCnp(),entity.getFirstName(),entity.getLastName(),entity.getAge())>0){
            return Optional.empty();
        }
        else
            return Optional.of(entity);
    }

    @Override
    public Optional<Client> delete(Long aLong) {
        String sql = "delete from Client where clientID=?";
        Optional<Client> entity = this.findOne(aLong);
        if(jdbcOperations.update(sql,aLong)==0)
            return Optional.empty();
        else
            return entity;
    }

    @Override
    public Optional<Client> update(Client entity) throws ValidatorException {
        String sql = "update Client set clientCNP=?, clientFirstName=?, clientLastName=?,clientAge=? where clientID=?";
        if(jdbcOperations.update(sql,entity.getCnp(),entity.getFirstName(),entity.getLastName(),entity.getAge(),entity.getId())>0){
            return Optional.empty();
        }else
            return Optional.of(entity);
    }
}
