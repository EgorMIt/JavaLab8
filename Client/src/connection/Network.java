package connection;

import java.io.*;
import java.net.Socket;

public class Network {

    private String address;
    private int port;
    private Socket socket;
//    private ObjectOutputStream oos;
//    private ObjectInputStream ois;
    private static boolean connection;


    public Network(String address, int port) throws IOException {
//      this.address = address;
//        this.port = port;
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

//        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//        oos.writeObject(obj);
//        System.out.println("Otpr");
//        oos.flush();
    }
    public Object read() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Object o = ois.readObject();
        return o;
    }

//    public ObjectInputStream getOis() {
//        return ois;
//    }

    public Socket getSocket() {
        return socket;
    }

//    public ObjectOutputStream getOos() {
//        return oos;
//    }


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
