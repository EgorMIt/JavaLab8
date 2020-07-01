package app;

import app.executors.Invoker;
import app.executors.Receiver;
import app.executors.RepositoryOfCity;
import app.db.DataBase;
import app.treatment.GetFromClient;
import exceptions.MessageErrors;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Класс, в котором присходят инициализация объектов, отвечающих за работу приложения
 * Он имеет главный метод initialisation(), который создает эти объекты
 *
 * @see ApplicationManager
 */
public class ApplicationManager extends Thread implements MessageErrors {


    private Invoker invoker;
    private Receiver receiver;
    private RepositoryOfCity repositoryOfCity;

    private Selector selector;
    private DataBase dataBase;

    {
        try {
            dataBase = new DataBase();
        } catch (SQLException e) {
            System.out.println("Не удалось подключиться к DataBase. Обратитесь в поддержку.");
        }
    }


    public  Selector getSelector() {
        return selector;
    }

    public ApplicationManager() {
        ///В констукторе нужно принимать номер порта прослушивания!

    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel(); // (serverSocketChannel == ssc, кстати)
        SocketChannel channel = serverSocketChannel.accept(); // так как точно известно, что ожидает коннект - тут мы без остановки
        channel.configureBlocking(false); // отключаем режим блокирования
        //SelectionKey keys = sc.register(selector, SelectionKey.OP_READ);
        System.out.println("Канал зарегистрирован на чтение");

        GetFromClient fromClient = new GetFromClient(channel, invoker, dataBase);
        Thread thread = new Thread(fromClient, "Read");
        thread.start();

    }public void begin() throws IOException {
        //repositoryOfCity.getCitiesCollection().putAll(csvReaderClass.parseCSV());
        // инициализация коллекции из файла

        repositoryOfCity = new RepositoryOfCity(dataBase);
        receiver = new Receiver(repositoryOfCity, dataBase);
        invoker = new Invoker(receiver);


        final ServerSocketChannel ssc = ServerSocketChannel.open(); // создаем серверСокет канал
        ssc.configureBlocking(false); // отключаем режим блокирования в ожидании
        ssc.socket().bind(new InetSocketAddress(RunServer.getPORT())); // получаем обычный серверсокет, который биндиться на нужный порт
        selector = Selector.open(); // создаем селектор прослушки
        ssc.register(selector, SelectionKey.OP_ACCEPT); // регистрируемся на селекторе на сервер-канал.

        System.out.println("Сервер запущен, чувсвует себя прекрасно.");

        while (true) {
            // проверяем подсистему ввода-вывода.
            if (selector.isOpen()) {
                try {
                    int keyCount = selector.selectNow();
                    if (keyCount > 0) {
                        // есть сообщения на коннект/прием/отправку.
                        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                        // перебираю этих ребят
                        while (iterator.hasNext()) {
                            SelectionKey sk = iterator.next();
                            //  забираю одного из них
                            iterator.remove();
                            //  удаляю его из списка запросов
                            if (!sk.isValid()) continue;
                            //  если невалидный - пропускаем
                            //if (sk.channel() == ssc) accept(sk);
                            if (sk.isAcceptable()) {
                                accept(sk);
                                //sk.channel().register(selector, SelectionKey.OP_CONNECT);
                                // если к нам новый коннект...
                            }
                        }

                    }
                } catch (IOException e) {
                    System.out.println("Серверу сткло плохо. IOException");
                    break;
                }
            } else {
                break; // выходим из цикла
            }
        }
    }
}







