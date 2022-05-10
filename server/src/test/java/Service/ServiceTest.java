package Service;


import Domain.Car;
import Domain.Client;
import Domain.DriveTest;
import Domain.Validators.*;
import Repository.InMemoryRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ExecutionException;

public class ServiceTest {

    public String str_gen(Integer length){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    @Test
    public void filterDriveTestsByRating() throws ExecutionException, InterruptedException {
        /*InMemoryRepository carRepository = new InMemoryRepository(new CarValidator());
        InMemoryRepository clientRepository = new InMemoryRepository<>(new ClientValidator());
        InMemoryRepository driveTestRepository = new InMemoryRepository<>(new DriveTestValidator());
        InMemoryRepository employeeRepository = new InMemoryRepository<>(new EmployeeValidator());
        InMemoryRepository saleRepository = new InMemoryRepository<>(new SaleValidator());
        MainController service = new MainController(carRepository,clientRepository,driveTestRepository,employeeRepository,saleRepository);
        Car c = new Car(str_gen(17),str_gen(5),str_gen(5));
        c.setId(1L);
        Client cl = new Client(str_gen(13),str_gen(5),str_gen(5),10);
        cl.setId(1L);
        DriveTest dt = new DriveTest(1L,1L,str_gen(13),str_gen(17),10);
        dt.setId(1L);
        DriveTest dt2 = new DriveTest(1L,1L,str_gen(13),str_gen(17),9);
        dt.setId(1L);
        service.add(c);
        service.add(cl);
        service.add(dt);
        service.add(dt2);
        Assert.assertTrue(service.filterDriveTestsByRating(10).get().stream().allMatch(f->f.getRating()==10));*/
    }

    @Test
    public void filterDriveTestsByCnp() throws ExecutionException, InterruptedException {
       /* InMemoryRepository carRepository = new InMemoryRepository(new CarValidator());
        InMemoryRepository clientRepository = new InMemoryRepository<>(new ClientValidator());
        InMemoryRepository driveTestRepository = new InMemoryRepository<>(new DriveTestValidator());
        InMemoryRepository employeeRepository = new InMemoryRepository<>(new EmployeeValidator());
        InMemoryRepository saleRepository = new InMemoryRepository<>(new SaleValidator());
        MainController service = new MainController(carRepository,clientRepository,driveTestRepository,employeeRepository,saleRepository);
        Car c = new Car(str_gen(17),str_gen(5),str_gen(5));
        c.setId(1L);
        Client cl = new Client(str_gen(13),str_gen(5),str_gen(5),10);
        cl.setId(1L);
        String cnp = str_gen(13);
        DriveTest dt = new DriveTest(1L,1L,cnp,str_gen(17),10);
        dt.setId(1L);
        DriveTest dt2 = new DriveTest(1L,1L,cnp,str_gen(17),9);
        dt.setId(1L);
        dt2.setId(2L);
        service.add(c);
        service.add(cl);
        service.add(dt);
        service.add(dt2);
        Assert.assertTrue(service.filterDriveTestsByCnp(cnp).get().stream().allMatch(f->f.getCnp().equals(cnp)));*/
    }

    @Test
    public void filterCarsByManufacturer() throws ExecutionException, InterruptedException {
       /* InMemoryRepository carRepository = new InMemoryRepository(new CarValidator());
        InMemoryRepository clientRepository = new InMemoryRepository<>(new ClientValidator());
        InMemoryRepository driveTestRepository = new InMemoryRepository<>(new DriveTestValidator());
        InMemoryRepository employeeRepository = new InMemoryRepository<>(new EmployeeValidator());
        InMemoryRepository saleRepository = new InMemoryRepository<>(new SaleValidator());
        MainController service = new MainController(carRepository,clientRepository,driveTestRepository,employeeRepository,saleRepository);
        String manufacturer = "dacia";
        Car c = new Car(str_gen(17),str_gen(5),manufacturer);
        c.setId(1L);
        Car c2 = new Car(str_gen(17),str_gen(5),str_gen(5));
        c2.setId(1L);
        service.add(c);service.add(c2);
        Assert.assertTrue(service.filterCarsByManufacturer(manufacturer).get().stream().allMatch(f->f.getManufacturer().equals(manufacturer)));*/
    }

    @Test
    public void filterclientsfn() throws ExecutionException, InterruptedException {
        /*InMemoryRepository carRepository = new InMemoryRepository(new CarValidator());
        InMemoryRepository clientRepository = new InMemoryRepository<>(new ClientValidator());
        InMemoryRepository driveTestRepository = new InMemoryRepository<>(new DriveTestValidator());
        InMemoryRepository employeeRepository = new InMemoryRepository<>(new EmployeeValidator());
        InMemoryRepository saleRepository = new InMemoryRepository<>(new SaleValidator());
        MainController service = new MainController(carRepository,clientRepository,driveTestRepository,employeeRepository,saleRepository);
        Client cl = new Client(str_gen(13),"bbb",str_gen(5),10);
        cl.setId(1L);
        Client cl2 = new Client(str_gen(13),"aaa",str_gen(5),10);
        cl2.setId(2L);
        service.add(cl);service.add(cl2);
        Client test = (Client) service.filterclientsfn().get().toArray()[0];
        Assert.assertTrue(test.getFirstName().equals("aaa"));*/
    }

    @Test
    public void filterCarsByModel() throws ExecutionException, InterruptedException {
       /* InMemoryRepository carRepository = new InMemoryRepository(new CarValidator());
        InMemoryRepository clientRepository = new InMemoryRepository<>(new ClientValidator());
        InMemoryRepository driveTestRepository = new InMemoryRepository<>(new DriveTestValidator());
        InMemoryRepository employeeRepository = new InMemoryRepository<>(new EmployeeValidator());
        InMemoryRepository saleRepository = new InMemoryRepository<>(new SaleValidator());
        MainController service = new MainController(carRepository,clientRepository,driveTestRepository,employeeRepository,saleRepository);
        String model = "test";
        Car c = new Car(str_gen(17),model,str_gen(5));
        c.setId(1L);
        Car c2 = new Car(str_gen(17),str_gen(5),str_gen(5));
        c2.setId(2L);
        service.add(c);service.add(c2);
        Assert.assertTrue(service.filterCarsByModel(model).get().stream().allMatch(f->f.getModel().equals(model)));*/
    }

    @Test
    public void reportMostTestedCars() {
        /*InMemoryRepository carRepository = new InMemoryRepository(new CarValidator());
        InMemoryRepository clientRepository = new InMemoryRepository<>(new ClientValidator());
        InMemoryRepository driveTestRepository = new InMemoryRepository<>(new DriveTestValidator());
        InMemoryRepository employeeRepository = new InMemoryRepository<>(new EmployeeValidator());
        InMemoryRepository saleRepository = new InMemoryRepository<>(new SaleValidator());
        MainController service = new MainController(carRepository,clientRepository,driveTestRepository,employeeRepository,saleRepository);
        String vin = str_gen(17);
        Car c = new Car(vin,str_gen(5),str_gen(5));
        c.setId(1L);
        String vin2 = str_gen(17);
        Car c2 = new Car(vin2,str_gen(5),str_gen(5));
        c2.setId(2L);
        Client cl = new Client(str_gen(13),str_gen(5),str_gen(5),10);
        cl.setId(1L);
        Client cl2 = new Client(str_gen(13),str_gen(5),str_gen(5),10);
        cl2.setId(2L);
        String cnp = str_gen(13);
        DriveTest dt = new DriveTest(1L,1L,cnp,vin,10); dt.setId(1L);
        DriveTest dt2 = new DriveTest(2L,1L,cnp,vin,9); dt2.setId(2L);
        DriveTest dt3 = new DriveTest(2L,2L,cnp,vin2,8); dt3.setId(3L);
      /*  service.add(c);
        service.add(c2);
        service.add(cl);
        service.add(cl2);
        service.add(dt);
        service.add(dt2);
        service.add(dt3);
        Assert.assertEquals(service.ReportMostTestedCars(), c); */
    }

    @Test
    public void FilterDriveTestsByVin() throws ExecutionException, InterruptedException {
        /*InMemoryRepository carRepository = new InMemoryRepository(new CarValidator());
        InMemoryRepository clientRepository = new InMemoryRepository<>(new ClientValidator());
        InMemoryRepository driveTestRepository = new InMemoryRepository<>(new DriveTestValidator());
        InMemoryRepository employeeRepository = new InMemoryRepository<>(new EmployeeValidator());
        InMemoryRepository saleRepository = new InMemoryRepository<>(new SaleValidator());
        MainController service = new MainController(carRepository,clientRepository,driveTestRepository,employeeRepository,saleRepository);
        Car c = new Car(str_gen(17),str_gen(5),str_gen(5));
        c.setId(1L);
        Client cl = new Client(str_gen(13),str_gen(5),str_gen(5),10);
        cl.setId(1L);
        String cnp = str_gen(13);
        String vin = str_gen(17);
        DriveTest dt = new DriveTest(1L,1L,cnp,vin,10);
        dt.setId(1L);
        DriveTest dt2 = new DriveTest(1L,1L,cnp,str_gen(17),9);
        dt.setId(1L);
        dt2.setId(2L);
        service.add(c);
        service.add(cl);
        service.add(dt);
        service.add(dt2);
        Assert.assertTrue(service.filterDriveTestsByVin(vin).get().stream().allMatch(f->f.getVINNumber().equals(vin)));*/
    }
}