package Dealership;


public class Main {

    public static void main(String args[]) {
        System.out.println("Hello");

       // CarValidator carValidator = new CarValidator();
        //Repository<Long, Car> carRepository = new InMemoryRepository<>(carValidator);
        //Repository<Long, Car> carRepository = new CarTxtFileRepository(carValidator);
        //Repository<Long, Car> carRepository = new CarXMLFileRepository(carValidator);
       // Repository<Long,Car> carRepository = new DBRepositoryCar<>(carValidator);



        //ClientValidator clientValidator = new ClientValidator();
        //Repository<Long, Client> clientRepository = new InMemoryRepository<>(clientValidator);
        //Repository<Long, Client> clientRepository = new ClientTxtFileRepository(clientValidator);
        //Repository<Long, Client> clientRepository = new ClientXMLFileRepository(clientValidator);
        //Repository<Long,Client> clientRepository = new DBRepositoryClient<>(clientValidator);


        //DriveTestValidator driveTestValidator = new DriveTestValidator();
        //Repository<Long, DriveTest> driveTestRepository = new DriveTestTxtFileRepository(driveTestValidator);
        //Repository<Long, DriveTest> driveTestRepository = new InMemoryRepository<>(driveTestValidator);
        //Repository<Long, DriveTest> driveTestRepository = new DriveTestXMLFileRepository(driveTestValidator);
       // Repository<Long,DriveTest> driveTestRepository = new DBRepositoryDriveTest<>(driveTestValidator);


        //EmployeeValidator employeeValidator = new EmployeeValidator();
        //Repository<Long, Employee> employeeRepository = new EmployeeTxtFileRepository(employeeValidator);
        //Repository<Long, Employee> employeeRepository = new InMemoryRepository<>(employeeValidator);
        //Repository<Long, Employee> employeeRepository = new EmployeeXMLFileRepository(employeeValidator);

       // SaleValidator saleValidator = new SaleValidator();
        //Repository<Long, Sale> saleRepository = new InMemoryRepository<>(saleValidator);
        //Repository<Long, Sale> saleRepository = new SaleTxtFileRepository(saleValidator);
        //Repository<Long, Sale> saleRepository = new SaleXMLFileRepository(saleValidator);



        //TODO - implement server-client using sockets

        //TODO - implement threads


       // Service service = new Service(carRepository,clientRepository,driveTestRepository,employeeRepository,saleRepository);


       // Console ui = new Console(service);
       // ui.runConsole();

    }
}
