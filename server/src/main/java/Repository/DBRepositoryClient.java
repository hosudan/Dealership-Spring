package Repository;



import Domain.BaseEntity;
import Domain.Client;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;
import IdGenerators.ClientIdGenerator;
import IdGenerators.IdGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class DBRepositoryClient<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {
    private Iterable<T> data;
    private IdGenerator idGenerator;
    private final Validator<T> validator;
    private static final String connectionURL = "jdbc:postgresql://localhost:5433/postgres?user=postgres";

    public DBRepositoryClient(Validator<T> validator){
        //createtables(); //-- run first time
        this.validator = validator;
        this.data = findAll();
        this.idGenerator = new ClientIdGenerator(getlastid());
    }

    /*
     * calculates the last id that is occupied in the database in its current form
     */
    public Long getlastid(){
        Long maxid = 0L;
        for(T entity: data){
            if(entity instanceof Client){
                Client cl = (Client) entity;
                maxid = Math.max(maxid,cl.getId());
            }
        }
        return maxid;
    }

    /**
     * creates the database tables needed for the application.
     */
    public static void createtables(){
        Connection con = null;
        Statement stmt = null;
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(connectionURL);
            stmt = con.createStatement();
            String SQL1 ="CREATE TABLE Client " +
                    "(clientID INT PRIMARY KEY     NOT NULL," +
                    "clientCNP TEXT NOT NULL," +
                    "clientFirstName TEXT NOT NULL," +
                    "clientLastName TEXT NOT NULL," +
                    "clientAge INT NOT NULL," +
                    "UNIQUE(clientCNP)," +
                    "CHECK(clientAge>0))";
            stmt.executeUpdate(SQL1);
        }
        catch (Exception e) { System.out.println(e.getMessage());}
        finally {
            if (stmt != null) try { stmt.close(); } catch(Exception e) {System.out.println("Cannot close statement.");}
            if (con != null) try { con.close(); } catch(Exception e) {System.out.println("Cannot close connection.");}
        }
    }

    /**
     * deletes the database contents used by the application.
     */
    public static void clearall(){
        Connection con = null;
        Statement stmt = null;
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(connectionURL);
            stmt = con.createStatement();
            String SQL1 ="DELETE FROM Client";
            stmt.executeUpdate(SQL1);
        }
        catch (Exception e) {System.out.println(e.getMessage());}
        finally {
            if (stmt != null) try { stmt.close(); } catch(Exception e) {System.out.println("Cannot close statement.");}
            if (con != null) try { con.close(); } catch(Exception e) {System.out.println("Cannot close connection.");}
        }
    }

    /**
     * Find the entity with the given {@code id}.
     *
     * @param id
     *            must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    public Optional<T> findOne(ID id){
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(connectionURL);
            stmt = con.createStatement();
            String SQL;
            SQL = "SELECT * FROM Client WHERE clientID = " + id;
            rs = stmt.executeQuery(SQL);
            Client cl;
            if (rs.next() == false) {
                return Optional.ofNullable(null);
            } else {

                do {
                    String CNP = rs.getString("clientCNP");
                    String firstname = rs.getString("clientFirstName");
                    String lastname = rs.getString("clientLastName");
                    Integer age = rs.getInt("clientAge");
                    cl = new Client(CNP,firstname,lastname,age);
                    cl.setId((Long)id);
                } while (rs.next());
            }
            return Optional.of((T)cl);
        }
        catch (Exception e){System.out.println(e.getMessage());}
        finally{
            if (stmt != null) try { stmt.close(); } catch(Exception e) {System.out.println("Cannot close statement.");}
            if (con != null) try { con.close(); } catch(Exception e) {System.out.println("Cannot close connection.");}
        }
        return Optional.empty();
    }

    /**
     *
     * @return all entities.
     */
    public Iterable<T> findAll(){
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // Establish the connection.
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(connectionURL);
            String SQL1 = "SELECT * FROM Client";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL1);
            ArrayList<T> TempList = new ArrayList<>();

            // Iterate through the data in the result set
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("clientID"));
                String CNP = rs.getString("clientCNP");
                String firstname = rs.getString("clientFirstName");
                String lastname = rs.getString("clientLastName");
                Integer age = rs.getInt("clientAge");
                Client cl = new Client(CNP, firstname, lastname, age);
                cl.setId(Long.parseLong(id));
                TempList.add((T) cl);
            }
            return TempList;
        }

        // Handle any errors that may have occurred.
        catch (Exception e) { System.out.println(e.getMessage()); }
        finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {System.out.println(e.getMessage());}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {System.out.println("Cannot close statement.");}
            if (con != null) try { con.close(); } catch(Exception e) {System.out.println("Cannot close connection.");}
        }
        return new ArrayList<>();
    }

    /**
     * Saves the given entity.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        //entity.setId((ID)idGenerator.getId());
        Connection con = null;
        Statement stmt = null;
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(connectionURL);
            stmt = con.createStatement();
            if (entity instanceof Client){
                Client ET = (Client) entity;
                Long clientID = ET.getId();
                String clientCNP = ET.getCnp();
                String clientFirstName = ET.getFirstName();
                String clientLastName = ET.getLastName();
                Integer clientAge = ET.getAge();
                String SQL = "INSERT INTO Client(clientID,clientCNP,clientfirstname,clientlastname,clientage) VALUES('"+ clientID+ "','" + clientCNP +"','"+ clientFirstName + "','" + clientLastName + "','" + clientAge + "')";
                stmt.executeUpdate(SQL);
                return Optional.of((T)ET);
            }
        }
        catch(ValidatorException ve ){ throw new ValidatorException(ve.getMessage()); }
        catch(SQLException esql){
            return Optional.ofNullable(null);
        }
        catch (Exception e){System.out.println(e.getMessage());}
        finally{
            if (stmt != null) try { stmt.close(); } catch(Exception e) {System.out.println("Cannot close statement.");}
            if (con != null) try { con.close(); } catch(Exception e) {System.out.println("Cannot close connection.");}
        }
        return Optional.empty();
    }

    /**
     * Removes the entity with the given id.
     *
     * @param id
     *            must not be null.
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    public Optional<T> delete(ID id){
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        Connection con = null;
        Statement stmt = null;
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(connectionURL);
            stmt = con.createStatement();
            String SQL;
            SQL = "DELETE from Client where clientID = "+id;
            stmt.executeUpdate(SQL);
            return Optional.ofNullable(null);
        }
        catch(SQLException sqle){
            return Optional.empty();
        }
        catch (Exception e){System.out.println(e.getMessage());}
        finally{
            if (stmt != null) try { stmt.close(); } catch(Exception e) {System.out.println("Cannot close statement.");}
            if (con != null) try { con.close(); } catch(Exception e) {System.out.println("Cannot close connection.");}
        }
        return Optional.empty();
    }

    /**
     * Updates the given entity.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was updated otherwise (e.g. id does not exist) returns the
     *         entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    public Optional<T> update(T entity) throws ValidatorException{
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        Connection con = null;
        Statement stmt = null;
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(connectionURL);
            stmt = con.createStatement();
            String SQL;
            if (entity instanceof Client) {
                Client ET = (Client)entity;
                SQL = "UPDATE Client set clientID = '" +ET.getId() + "',clientCNP = '" +ET.getCnp()+"',clientfirstname = '"+ET.getFirstName()+"',clientlastname = '"+ET.getLastName()+"',clientage = '"+ET.getAge()+"' where clientId = "+ET.getId()+";";
                stmt.executeUpdate(SQL);
                return Optional.of((T)ET);
            }
        }
        catch(SQLException sqle){
            return Optional.ofNullable(null);
        }
        catch (Exception e){System.out.println(e.getMessage());}
        finally{
            if (stmt != null) try { stmt.close(); } catch(Exception e) {System.out.println("Cannot close statement.");}
            if (con != null) try { con.close(); } catch(Exception e) {System.out.println("Cannot close connection.");}
        }
        return Optional.empty();
    }
}
