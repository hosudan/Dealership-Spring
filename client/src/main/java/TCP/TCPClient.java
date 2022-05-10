package TCP;

import Message.Message;
import Service.ServiceException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class TCPClient {
    private String serviceHost;
    private int servicePort;

    public TCPClient(String serviceHost, int servicePort) {
        this.serviceHost = serviceHost;
        this.servicePort = servicePort;
    }

    public Message sendAndReceive(Message request) throws ServiceException{
        ObjectOutputStream outputStream = null;
        ObjectInputStream inStream;
        System.out.println("Connecting to service");
        try (Socket socket = new Socket(serviceHost, servicePort)) {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(request);
            outputStream.flush();
            System.out.println("Request sent: " + request.toString());

            inStream  = new ObjectInputStream(socket.getInputStream());
            Message response = (Message)inStream.readObject();
            if (response.header().equalsIgnoreCase(Message.OK)) {
                System.out.println("Response OK: " + response.toString());
                return response;
            } else {
                System.out.println("Response ERROR: " + response.toString());
                throw new ServiceException(response.header());
            }
        } catch (SocketException se) {
            //se.printStackTrace();
            throw new ServiceException(se);
        } catch (IOException e) {
            //e.printStackTrace();
            throw new ServiceException(e);
        }catch (ClassNotFoundException cn) {
            //cn.printStackTrace();
            throw new ServiceException(cn);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
