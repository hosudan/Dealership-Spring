package Config;

import Domain.Car;
import Domain.Client;
import Domain.DriveTest;
import Repository.Repository;
import Repository.jdbcRepositories.RepositoryCar;
import Repository.jdbcRepositories.RepositoryClient;
import Repository.jdbcRepositories.RepositoryDriveTest;
import Service.*;
import TCP.MessageHandler;
import TCP.TCPServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig {

    @Bean
    CarService carService(){
        return new CarService(carRepository());
    }

    @Bean
    Repository<Long, Car> carRepository(){
        return new RepositoryCar();
    }


    @Bean
    ClientService clientService(){
        return new ClientService(clientRepository());
    }

    @Bean
    Repository<Long, Client> clientRepository(){
        return new RepositoryClient();
    }


    @Bean
    DriveTestService driveTestService(){return new DriveTestService(driveTestRepository());}

    @Bean
    ExecutorService executorBean(){ ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    return executorService;}

    @Bean
    MainController mainController() {
        MainController mc = new MainController(carService(), clientService(), driveTestService(), executorBean());
        return mc;
    }

    @Bean
    TCPServer tcpServer(){
        TCPServer server = new TCPServer(executorBean(), Service.SERVICE_HOST,Service.SERVICE_PORT);
        server.addHandler(Service.addCar, messageHandler()::addCar);
        server.addHandler(Service.deleteCar, messageHandler()::deleteCar);
        server.addHandler(Service.updateCar, messageHandler()::updateCar);
        server.addHandler(Service.getAllCars, messageHandler()::getAllCars);
        server.addHandler(Service.filterCarsByManufacturer, messageHandler()::filterCarsByManufacturer);
        server.addHandler(Service.filterCarsByModel, messageHandler()::filterCarsByModel);
        server.addHandler(Service.ReportMostTestedCars, messageHandler()::reportMostTestedCar);
        server.addHandler(Service.populateCars, messageHandler()::populateCars);


        //Client
        server.addHandler(Service.addClient, messageHandler()::addClient);
        server.addHandler(Service.deleteClient, messageHandler()::deleteClient);
        server.addHandler(Service.updateClient, messageHandler()::updateClient);
        server.addHandler(Service.getAllClients, messageHandler()::getAllClient);
        server.addHandler(Service.sortClientsByFirstName, messageHandler()::sortClientsByFirstName);
        server.addHandler(Service.ReportBiggestTester, messageHandler()::reportBiggestTester);
        server.addHandler(Service.populateClients, messageHandler()::populateClients);


        //DriveTest
        server.addHandler(Service.addDriveTest, messageHandler()::addDriveTest);
        server.addHandler(Service.deleteDriveTest, messageHandler()::deleteDriveTest);
        server.addHandler(Service.updateDriveTest, messageHandler()::updateDriveTest);
        server.addHandler(Service.getAllDriveTests,messageHandler()::getAllDriveTests);
        server.addHandler(Service.filterDriveTestByRating, messageHandler()::filterDriveTestsByRating);
        server.addHandler(Service.filterDriveTestsByCNP, messageHandler()::filterDriveTestsByCNP);
        server.addHandler(Service.filterDriveTestsByVin, messageHandler()::filterDriveTestsByVIN);
        server.addHandler(Service.populateDriveTests, messageHandler()::populateDriveTests);
        return server;
    }

    @Bean
    MessageHandler messageHandler(){
        MessageHandler messageHandler = new MessageHandler(mainController());
        return messageHandler;
    }

    @Bean
    Repository<Long, DriveTest> driveTestRepository(){
        return new RepositoryDriveTest();
    }
}
