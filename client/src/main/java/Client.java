import Domain.Car;
import Domain.DriveTest;
import Domain.Employee;
import Domain.Sale;
import Service.Service;
import TCP.TCPClient;
import TCP.TCPClientServiceCar;
import TCP.TCPClientServiceClient;
import TCP.TCPClientServiceDriveTest;
import UX.Console;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {


    public static void main(String[] args) {

        //ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        //ExecutorService executorService2 = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        //ExecutorService executorService3 = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        //TCPClient tcpClient = new TCPClient(Service.SERVICE_HOST,Service.SERVICE_PORT);
        //TCPClient tcpClient2 = new TCPClient(Service.SERVICE_HOST,Service.SERVICE_PORT);
        //TCPClient tcpClient3 = new TCPClient(Service.SERVICE_HOST,Service.SERVICE_PORT);

        //TCPClientServiceCar serviceCar = new TCPClientServiceCar(executorService,tcpClient);
        //TCPClientServiceClient serviceClient = new TCPClientServiceClient(executorService,tcpClient);
        //TCPClientServiceDriveTest serviceDriveTest = new TCPClientServiceDriveTest(executorService,tcpClient);
        AnnotationConfigApplicationContext context = null;

        try{
            context = new AnnotationConfigApplicationContext("config");
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }


        System.out.println("Server started");
        Console console = (Console)  context.getBean(Console.class);
        console.runConsole();
        ExecutorService exS = (ExecutorService) context.getBean(ExecutorService.class);
        exS.shutdown();
    }

}
