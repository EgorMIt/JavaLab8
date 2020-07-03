package ClientFullPack.connection;

import java.io.*;
import java.net.Socket;

public class Network {

    private String address;
    private int port;
    private Socket socket;

    private static boolean connection;


    public Network(String address, int port) throws IOException {

        socket = new Socket(address,port);
        this.connection = true;
    }

    public void write(Object obj) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream send = new ObjectOutputStream(baos);
        send.writeObject(obj);
        byte[] outcoming = baos.toByteArray();
        socket.getOutputStream().write(outcoming);
        send.flush();
        baos.flush();


    }
    public Object read() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Object o = ois.readObject();
        return o;
    }


    public Socket getSocket() {
        return socket;
    }



    public void closeStream() throws IOException {
        socket.close();

        connection = false;
    }

    public void setConnection(boolean connection) {
        this.connection = connection;
    }

    public static boolean isConnection() {
        return connection;
    }
}
