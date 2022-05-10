package Message;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {
    public static final String OK = "OK";
    public static final String ERROR = "ERROR";

    /* private static String LINE_SEPARATOR = System.getProperty("line.separator"); */

    private String header;
    private String body;
    private List<String> MessageContainer;

    public void setMessageContainer(List<String> messageContainer) {
        MessageContainer = messageContainer;
    }


    public Message() {
    }

    public Message(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public String body() {
        return body;
    }

    public String header() {
        return header;
    }

    public List<String> MessageContainer() {
        return MessageContainer;
    }

    @Override
    public String toString() {
        return "Message.Message{" + "header='" + header + '\'' + ", body='" + body + '\'' + ", MessageContainer='" + MessageContainer + '\'' + " }";
    }
}
