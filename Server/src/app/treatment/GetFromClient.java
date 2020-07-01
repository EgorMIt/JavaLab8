package app.treatment;

import app.executors.Invoker;
import app.db.DataBase;
import io.Message;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class GetFromClient implements Runnable {

    private SocketChannel channel;
    private Invoker invoker;
    private DataBase dataBase;
    private Selector readerSelector;


    public GetFromClient(SocketChannel channel, Invoker invoker, DataBase dataBase) {
        this.channel = channel;
        this.invoker = invoker;
        this.dataBase = dataBase;
        try {
            readerSelector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            // хватаем канал коннекта
            ByteBuffer bb = ByteBuffer.allocate(5000); //буффер для channel.read()
            // селектором произвожу очередь на выоленение в этом потоке
            //регистрирую канал на чтение
            channel.register(readerSelector, SelectionKey.OP_READ);
            //немного поспим, на самом, больше подстраховка, нежели необходимость.
            System.out.println(channel.socket().getChannel() + " - Адресат");
            int N = readerSelector.select();
            // если количество оижадающих больше 0..
            if (N > 0) {
                int count = 0;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // сложные манипуляции по праавильному прочтению команды
                System.out.println("Считываем сообщение от клиента..");
                while ((count = channel.read(bb)) > 0) {
                    bb.flip();
                    baos.write(bb.array(), 0, count);
                    bb.clear();
                }
                System.out.println("Размер полученного сообщения: " + baos.size());
                ByteArrayInputStream bios = new ByteArrayInputStream(baos.toByteArray());

                ObjectInputStream ois = new ObjectInputStream(bios);
                Message message = (Message) ois.readObject();
                System.out.println("Поступил запрос на выполнение команды: " + message.getCommandName());

                // создается поток, который занимается инициализацией команды и выполеннием.
                Transaction transaction = new Transaction(message, invoker, channel);
                Thread commandTransaction = new Thread(transaction, "Transaction");
                commandTransaction.start();
            }


        } catch (IOException | ClassNotFoundException e) { // беда, коннект отпал внезапно...
            e.printStackTrace();
            System.out.println("-------------------");
            try {
                channel.close();
            } catch (IOException ex) {
                System.out.println("Произошла ошибка закрытия канала чтения. ");
            }
            System.out.println("Подключение закрылось на этапе чтения сообщения.");
        } catch (NullPointerException e) {
        }
    }

}
