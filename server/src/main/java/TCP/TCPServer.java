package TCP;

import Message.Message;
import Service.ServiceException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.UnaryOperator;

public class TCPServer {
    private ExecutorService executorService;
    private String serveHost;
    private int serverPort;

    private Map<String, UnaryOperator<Message>> methodHandlers = new HashMap<>();

    public TCPServer(ExecutorService executorService, String serveHost, int serverPort) {
        this.executorService = executorService;
        this.serveHost = serveHost;
        this.serverPort = serverPort;
    }

    public void addHandler(String methodName, UnaryOperator<Message> methodHandler) {
        methodHandlers.put(methodName, methodHandler);
    }

    public void startServer() {
        System.out.println("Starting server");
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.out.println("Server socket created");
            while (true) {
                System.out.println("Waiting for clients");
                Socket socket = serverSocket.accept();
                System.out.println("Client accepted");
                executorService.submit(new ClientHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (ObjectInputStream inStream = new  ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
                Message request = (Message)inStream.readObject();
                System.out.println("received request: " + request);

                UnaryOperator<Message> methodHandler = methodHandlers.get(request.header());
                Message response = methodHandler.apply(request);
                System.out.println("computed response: " + response);

                outputStream.writeObject(response);
                outputStream.flush();
                System.out.println("response written to outputStream");
            } catch (IOException e) {
                e.printStackTrace();
                throw new ServiceException(e);
            }catch (ClassNotFoundException cn) {
                //cn.printStackTrace();
                throw new ServiceException(cn);
            } finally {
                try {
                    if (socket != null) {
                        socket.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }
    }
}
