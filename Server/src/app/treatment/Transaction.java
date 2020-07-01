package app.treatment;

import app.executors.Invoker;
import app.db.DataBase;
import io.Message;
import org.postgresql.util.PSQLException;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;

public class Transaction implements Runnable {

    private Message message;
    private Invoker invoker;
    private SocketChannel channel;
    private DataBase dataBase;

    public Transaction(Message message, Invoker invoker, SocketChannel channel) {
        try {
            this.message = message;
            this.invoker = invoker;
            this.channel = channel;
        } catch (NullPointerException e) {
            System.out.println("В transaction нет канала.");
        }
        this.dataBase = invoker.getReceiver().getDataBase();


    }

    @Override
    public void run() {

        System.out.println("Запрос передан в блок выполнения команды");

        String command = message.getCommandName();

        if (message.getCommandName().equals("REGISTRATION") && message.isRegistration()) {
            // производится проверка, не регистрация ли это..
            try {
                dataBase.addUserToTheBase(message.getUser().getLogin(), message.getUser().getPassword());
            } catch (SQLException e) {
                SendToClient.write("Во время регистрации произошла ошибка. Попробуйте еще раз!");
            }
        } else if (message.getCommandName().equals("AUTHORIZATION") && !message.isRegistration()) {
            // производится проверка, не авторизация ли это..
            try {
                dataBase.authorizationUser(message.getUser().getLogin(), message.getUser().getPassword());
            } catch (SQLException e) {
                SendToClient.write("Во время авторизации произошла ошибка. Скорее всего отвалилось соединение с БД. Попробуйте в другой раз!");
            }

        } else {
            try {
                if (dataBase.authForExecuteCommand(message.getUser().getLogin(), message.getUser().getPassword())) {
                // если переданные с командой логин и пароль не изменились остались валидными и не изменились, то..

                    dataBase.setOwner(message.getUser().getLogin());
                    // устанавливается владелец всех изменений, которые будут выполнены этим запросом

                    if (message.getArgs() != null && message.getCity() == null) {

                        invoker.execute(command, invoker.getCommandMap(), message.getArgs());

                    } else if (message.getArgs() == null && message.getCity() == null && message.getExecutionFile() == null) {

                        invoker.execute(command, invoker.getCommandMap());

                    } else if (message.getArgs() == null && message.getCity() != null) {

                        invoker.execute(command, invoker.getCommandMap(), message.getCity());

                    } else if (message.getArgs() != null && message.getCity() != null) {

                        invoker.execute(command, invoker.getCommandMap(), message.getCity(), message.getArgs());

                    } else if (message.getExecutionFile() != null && message.getCity() == null && message.getArgs() == null) {

                        invoker.execute(command, invoker.getCommandMap(), message.getExecutionFile());

                    }
                    // произведена выборка перегруженного execute(..) объекта инвокера, который отправляет заказ на выполнение команды.

                    System.out.println("Команда выполнена '" + command + "' выполнениа, результат выполения в блок отправки.");

                } else {
                    // если все-таки логин и пароль оказались невалидными..
                    SendToClient.write("Ваш запрос не может быть обработан, так как логин и пароль были изменены во время запроса.");

                }

            } catch (IOException e) {
                System.out.println("Ошибка ввода-вывода. Обратитесь в поддержку или попробуйте еще раз");
                try {
                    channel.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);


            } catch (NullPointerException e) {
                SendToClient.write("Упала ошибка NPE, что - то пошло не по плану..");
                // пару раз возникала.. вроде закрыты все хвосты.
            }
        }
        Thread thread = new Thread(new SendToClient(channel));
        thread.start();


    }
}

