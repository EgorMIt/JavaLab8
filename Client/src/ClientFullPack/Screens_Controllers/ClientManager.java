package ClientFullPack.Screens_Controllers;

import ClientFullPack.RunClient;
import ClientFullPack.connection.Network;
import exceptions.ArgumentException;
import io.Message;
import ClientFullPack.request.UserResponse;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.util.NoSuchElementException;

public class ClientManager {

    private static final String REGISTRATION_IS_SUCCESS = "110100011000000011010000101101011101000010110011";
    private static final String AUTHORIZATION_IS_SUCCESS = "110100001011000011010000101100101101000110000010";

    public void begin(){
        try {
            UserResponse userResponseValidator = new UserResponse();
            Network network;

            do {
                try {

                    Message message = userResponseValidator.validate();
                    // объекту класса Message были присвоены признаки new Message(..), которые возвращается метод validate()

                    if (userResponseValidator.getCommandName() != null) {
                        // если валидатор имеет команду.. а он обязан иметь её в любом случае..
                        // а именно .getCommandName() вернет 'Авторизация'
                        //network = new Network();
                        network = new Network(RunClient.ip_adress, RunClient.port);
                        network.write(message);
                        System.out.println("Соединение установлено!");
                        String responce = (String) network.read();

                        if (responce.contains("00101110010000010011001000111101")) {
                            // если код команды exit
                            System.exit(0);

                        } else if (responce.contains(REGISTRATION_IS_SUCCESS)) {
                            // если успешная регистрация..
                            System.out.println("Регистрация прошла успешно!");
                            continue;
                            // если успешная авторизация..
                        } else if (responce.contains(AUTHORIZATION_IS_SUCCESS)) {

                            System.out.println("Авторизация прошла успешно!");
                            UserResponse.setLoginIsSuccess(true);
                            System.out.println(MESSAGEAFTERSUTH);

                            continue;
                        }


                        System.out.println(responce);
                        // тут нужно закрывать INPUT/OUTPUT - streams

                    }
                } catch (EOFException | ConnectException e) {
                    e.printStackTrace();
                    System.out.println("Сервер недоступен в данный момент.. Проверьте правильность введенного адреса и порта подключения и попробуйте заново");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace(System.out);
                } catch (ArgumentException e) {
                    System.out.println(e.getMessage());
                    ;
                } catch (NumberFormatException e) {
                    System.out.println("Введенный вами порт имееет неприавльный формат!");
                    System.exit(0);
                }

            } while (true);

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Порт подключения не указан!");
        } catch (NoSuchElementException | IOException e) {
        } finally {
            System.exit(0);
        }
    }

    private static final String MAINMESSAGE = "      Добро пожаловать в консольное приложение \n" +
            "╔═══╗╔══╗╔════╗╔╗──╔╗╔══╗─╔╗─╔╗╔══╗╔╗───╔═══╗╔═══╗╔═══╗\n" +
            "║╔═╗║╚╣─╝║╔╗╔╗║║╚╗╔╝║║╔╗║─║║─║║╚╣─╝║║───╚╗╔╗║║╔══╝║╔═╗║\n" +
            "║║─╚╝─║║─╚╝║║╚╝╚╗╚╝╔╝║╚╝╚╗║║─║║─║║─║║────║║║║║╚══╗║╚═╝║\n" +
            "║║─╔╗─║║───║║───╚╗╔╝─║╔═╗║║║─║║─║║─║║─╔╗─║║║║║╔══╝║╔╗╔╝\n" +
            "║╚═╝║╔╣─╗──║║────║║──║╚═╝║║╚═╝║╔╣─╗║╚═╝║╔╝╚╝║║╚══╗║║║╚╗\n" +
            "╚═══╝╚══╝──╚╝────╚╝──╚═══╝╚═══╝╚══╝╚═══╝╚═══╝╚═══╝╚╝╚═╝\n" +
            "by Дзюбак Антон и Митрофанов Егор - P3114 \n" +
            "Чтобы использовать функционал - необходимо авториизироваться в системе.\n";


    private static final String MESSAGEAFTERSUTH = "Пожалуйста, введи 'help' в консоль, чтобы ознакомиться с функционалом приложения." + System.lineSeparator();

}
