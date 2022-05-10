package config;

import Service.Service;
import TCP.TCPClient;
import TCP.TCPClientServiceCar;
import TCP.TCPClientServiceClient;
import TCP.TCPClientServiceDriveTest;
import Service.CarService;
import Service.ClientService;
import Service.DriveTestService;
import UX.Console;
import UX.IConsole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.remoting.rmi.RmiServiceExporter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig {

    @Bean
    ExecutorService executorBean(){ ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        return executorService;}

    @Bean
    TCPClient tcpClient(){
        return new TCPClient(Service.SERVICE_HOST,Service.SERVICE_PORT);
    }

    @Bean
    Console console(){
        return new Console(carService(),clientService(),driveTestService());
    }

    @Bean
    TCPClientServiceDriveTest driveTestService() {
        return new TCPClientServiceDriveTest(executorBean(),tcpClient());
    }

    @Bean
    TCPClientServiceClient clientService() {
        return new TCPClientServiceClient(executorBean(),tcpClient());
    }

    @Bean
    TCPClientServiceCar carService(){
        return new TCPClientServiceCar(executorBean(),tcpClient());
    }


}
