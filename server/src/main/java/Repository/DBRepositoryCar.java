package Repository;


import Domain.BaseEntity;
import Domain.Car;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;
import IdGenerators.CarIdGenerator;
import IdGenerators.IdGenerator;

import java.sql.*;
import java.util.*;

public class DBRepositoryCar<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {
    private Iterable<T> data;
    private IdGenerator idGenerator;
    private final Validator<T> validator;
    private static final String connectionURL = "jdbc:postgresql://localhost:5433/postgres?user=postgres";

    public DBRepositoryCar(Validator<T> validator){
        //createtables(); //-- run first time
        this.validator = validator;
        this.data = findAll();
        this.idGenerator = new CarIdGenerator(getlastid());
    }

    /**
     * calculates the last id that is occupied in the database in its current form
     * @return the value of the last id
     */
    public Long getlastid(){
        Long maxid = 0L;
        for(T entity: data){
            if(entity instanceof Car){
                Car car = (Car) entity;
                maxid = Math.max(maxid,car.getId());
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
            String SQL ="CREATE TABLE Car " +
                    "(carID INT PRIMARY KEY     NOT NULL," +
                    "carVIN TEXT NOT NULL," +
                    "carModel TEXT NOT NULL," +
                    "carManufacturer TEXT NOT NULL," +
                    "UNIQUE(carVIN,carModel,carManufacturer))";
            stmt.executeUpdate(SQL);
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
            String SQL ="DELETE FROM Car";
            stmt.executeUpdate(SQL);
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
            SQL = "SELECT * FROM Car WHERE carID = " + id;
            rs = stmt.executeQuery(SQL);
            Car car;
            if (rs.next() == false) {
                return Optional.ofNullable(null);
            } else {

                do {
                    String vin = rs.getString("carVIN");
                    String model = rs.getString("carModel");
                    String manufacturer = rs.getString("carManufacturer");
                    car = new Car(vin,model,manufacturer);
                    car.setId((Long)id);
                } while (rs.next());
            }
            return Optional.of((T)car);
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
            String SQL = "SELECT * FROM Car";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            ArrayList<T> TempList = new ArrayList<>();

            // Iterate through the data in the result set
            while (rs.next()) {
                /**System.out.println(rs.getString(1) + " " + rs.getString(2)); **/
                String id = rs.getString("carID");
                String vin = rs.getString("carVIN");
                String model = rs.getString("carModel");
                String manufacturer = rs.getString("carManufacturer");
                Car car = new Car(vin,model,manufacturer);
                car.setId(Long.parseLong(id));

                TempList.add((T)car);
            }

            return TempList;
        }

        // Handle any errors that may have occurred.
        catch (Exception e) { System.out.println(e.getMessage()); }
        finally {
            if (rs != null) try { rs.close(); } catch(Exception e) { System.out.println(e.getMessage()); }
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
            if (entity instanceof Car){
                Car ET = (Car) entity;
                Long carID = ET.getId();
                String carVIN = ET.getVINNumber();
                String carModel = ET.getModel();
                String carManufacturer = ET.getManufacturer();
                String SQL = "INSERT INTO Car(carID,carVIN,carModel,carManufacturer) VALUES('"+ carID+ "','" + carVIN +"','"+ carModel + "','" + carManufacturer + "')";
                stmt.executeUpdate(SQL);
                return Optional.of((T)ET);
            }
        }
        catch(ValidatorException ve ){ throw new ValidatorException(ve.getMessage()); }
        catch(SQLException esql){
            esql.printStackTrace();
            return Optional.ofNullable(null);
        }
        catch (Exception e){ System.out.println(e.getMessage()); }
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
            SQL = "DELETE from Car where carID = "+id+";";
            stmt.executeUpdate(SQL);
            return Optional.ofNullable(null);
        }
        catch(SQLException sqle){
            System.out.println("DB ERROR: "+sqle.getMessage());
            return Optional.empty();
        }
        catch (Exception e){e.printStackTrace();}
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
            if (entity instanceof Car) {
                Car ET = (Car)entity;
                SQL = "UPDATE Car SET carVIN = '" +ET.getVINNumber()+"',carModel = '"+ET.getModel()+"',carManufacturer = '"+ET.getManufacturer()+"' where carID = "+ET.getId()+";";
                stmt.executeUpdate(SQL);
                return Optional.of((T)ET);
            }
        }
        catch(SQLException sqle){
            System.out.println("DB ERROR: "+sqle.getMessage());
            return Optional.ofNullable(null);
        }
        catch (Exception e){e.printStackTrace();}
        finally{
            if (stmt != null) try { stmt.close(); } catch(Exception e) {System.out.println("Cannot close statement.");}
            if (con != null) try { con.close(); } catch(Exception e) {System.out.println("Cannot close connection.");}
        }
        return Optional.empty();
    }
}
