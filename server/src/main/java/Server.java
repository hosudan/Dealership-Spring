import Domain.*;
import Domain.Validators.*;
import Repository.*;
import Service.MainController;
import TCP.MessageHandler;
import TCP.TCPServer;
import Service.Service;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = null;

        try{
            context = new AnnotationConfigApplicationContext("Config");
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }


        System.out.println("Server started");
        TCPServer server = (TCPServer)  context.getBean(TCPServer.class);
        server.startServer();
        ExecutorService exS = (ExecutorService) context.getBean(ExecutorService.class);
        exS.shutdown();
        System.out.println("Server stopped");
    }
}
