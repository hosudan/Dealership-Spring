package Repository;



import Domain.BaseEntity;
import Domain.DriveTest;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;
import IdGenerators.DriveTestIdGenerator;
import IdGenerators.IdGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class DBRepositoryDriveTest<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {
    private Iterable<T> data;
    private IdGenerator idGenerator;
    private final Validator<T> validator;
    private static final String connectionURL = "jdbc:postgresql://localhost:5433/postgres?user=postgres";

    public DBRepositoryDriveTest(Validator<T> validator){
        //createtables(); //-- run first time
        this.validator = validator;
        this.data = findAll();
        this.idGenerator = new DriveTestIdGenerator(getlastid());
    }

    /*
     * calculates the last id that is occupied in the database in its current form
     */
    public Long getlastid(){
        Long maxid = 0L;
        for(T entity: data){
            if(entity instanceof DriveTest){
                DriveTest dt = (DriveTest) entity;
                maxid = Math.max(maxid,dt.getId());
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
            String SQL2 ="CREATE TABLE DriveTest " +
                    "(driveTestID INT PRIMARY KEY     NOT NULL," +
                    "clientID INT NOT NULL," +
                    "carID INT NOT NULL REFERENCES Car(carID)," +
                    "clientCNP TEXT NOT NULL," +
                    "carVIN TEXT NOT NULL," +
                    "Rating INT NOT NULL," +
                    "CONSTRAINT fk_client" +
                    "   FOREIGN KEY(clientID)" +
                    "       REFERENCES Client(clientID)" +
                    "       ON DELETE CASCADE )";
            stmt.executeUpdate(SQL2);
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
            String SQL2 ="DELETE FROM DriveTest";
            stmt.executeUpdate(SQL2);
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
            SQL = "SELECT * FROM drivetest WHERE drivetestid = " + id;
            rs = stmt.executeQuery(SQL);
            DriveTest dt;
            if (rs.next() == false) {
                return Optional.empty();
            } else {

                do {
                    Long clientID = Long.parseLong(rs.getString("clientID"));
                    Long carID = Long.parseLong(rs.getString("clientID"));
                    String clientcnp = rs.getString("clientcnp");
                    String carvin = rs.getString("carvin");
                    Integer rating = rs.getInt("rating");
                    dt = new DriveTest(clientID,carID,clientcnp,carvin,rating);
                    dt.setId((Long)id);
                } while (rs.next());
            }
            return Optional.of((T)dt);
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
            ArrayList<T> TempList = new ArrayList<>();
            String SQL = "SELECT * FROM drivetest";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("drivetestID"));
                Long clientID = Long.parseLong(rs.getString("clientID"));
                Long carID = Long.parseLong(rs.getString("carID"));
                String clientCNP = rs.getString("clientCNP");
                String carVIN = rs.getString("carVIN");
                Integer rating = rs.getInt("Rating");
                DriveTest dt = new DriveTest(clientID,carID,clientCNP,carVIN,rating);
                dt.setId(Long.parseLong(id));
                TempList.add((T)dt);
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
        Connection con = null;
        Statement stmt = null;
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(connectionURL);
            stmt = con.createStatement();
            if (entity instanceof DriveTest){
                DriveTest ET = (DriveTest) entity;
                Long clientID = ET.getClientID();
                Long carID = ET.getCarID();
                Long drivetestID = ET.getId();
                String clientcnp = ET.getCnp();
                String carVIN = ET.getVINNumber();
                Integer rating = ET.getRating();
                String SQL = "INSERT INTO DriveTest(drivetestid,clientid,carid,clientcnp,carvin,rating) VALUES('"+ drivetestID+ "','"+ clientID+ "','"+ carID+ "','" + clientcnp +"','"+ carVIN + "','" + rating + "')";
                stmt.executeUpdate(SQL);
                return Optional.of((T)ET);
            }
        }
        catch(ValidatorException ve ){ throw new ValidatorException(ve.getMessage()); }
        catch(SQLException sqle){
            System.out.println(sqle.getMessage());
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
            SQL = "DELETE from drivetest where drivetestid = "+id+";";
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
            if (entity instanceof DriveTest) {
                DriveTest ET = (DriveTest) entity;
                SQL = "UPDATE drivetest set drivetestid = '" +ET.getId() + "',clientcnp = '" +ET.getCnp()+"',carVIN = '"+ET.getVINNumber()+"',rating = '"+ET.getRating()+"' where drivetestid = "+ET.getId()+";";
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

