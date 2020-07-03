package app.treatment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class SendToClient implements Runnable {

    private static ArrayList<Object> arrayList = new ArrayList<>();

    private SocketChannel channel;

    public SendToClient(SocketChannel channel) {
        this.channel = channel;
    }

    private static String messageToServer = "";

    /*public static void write(String s) {
        messageToServer += s;
    }*/

    public static void write(Object object){
        arrayList.add(object);
    }


    @Override
    public void run() {
        try {
            Selector selector = null;
            try {
                selector = Selector.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
            channel.register(selector, SelectionKey.OP_WRITE);
            selector.select();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            System.out.println(arrayList.size() + " " + arrayList.isEmpty());
            oos.writeObject(arrayList);
            byte[] outcoming = baos.toByteArray();
            ByteBuffer bb = ByteBuffer.wrap(outcoming);
            int m = 0;
            while ((m = channel.write(bb)) > 0) ;
            bb.clear();
            baos.flush();
            oos.flush();
            /*if (bb.array().length > 0) {
                try {
                    channel.write(bb);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
            arrayList.clear();

        } catch (IOException e) {
            System.out.println("Произошла ошибка отправки сообщения клиенту. Возможно, он отключился");
        } finally {
            try {
               channel.close();
            } catch (IOException e) {
                System.out.println("Не удалось закрыть канал.");
            }
        }
    }

}
