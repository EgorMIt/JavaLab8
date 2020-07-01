package request;

import app.collection.building.CityBuilder;
import exceptions.ArgumentException;
import io.ConsoleReader;
import io.Message;
import io.User;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

public class UserResponse {


    private String          commandName;
    private ConsoleReader   consoleReader;
    private Console         console;

    private Message         message;
    private User            user;

    private String          login;
    private String          password;
    private boolean         isRegistration;

    private static boolean loginIsSuccess = false;

    public String getCommandName() {
        return commandName;
    }

    /*
    * Класс занимается валидацией команд и созданием объекта по вводимым пользователям данным:
    * SysIN -> new Message
     */

    public UserResponse() throws IOException {
        consoleReader = new ConsoleReader();
        console = System.console();
    }


    /*
    * Метод отвечает за создание объекта класса Message, который харнит в себе наименование _
    * _ консольной команды и параментры для её выполнения
     */

    public Message validate() throws IOException, NoSuchElementException {

        while (true) {

            if (!loginIsSuccess) {
                authorizationOrRegistration();
                return message;
            }

            consoleReader.getDefaultConsoleSymbol();
            String line = consoleReader.getScanner().nextLine().trim();

            if (line.isEmpty()) break;

            String[] request = line.split(" ");
            commandName = request[0].toLowerCase();

            switch (commandName) {
                case "remove":
                    if (request.length == 2) {
                        try {
                            new Integer(request[1].trim());
                        } catch (NumberFormatException e) {
                            System.out.println("У команда " + Commands.REMOVE.getCommandName() + " аргумент - целое число!");
                            break;
                        }
                        return new Message(Commands.REMOVE.getCommandName(), request[1], user);
                    } else throw new ArgumentException();

                case "average_of_meters_above_sea_level":

                    if (request.length < 2) {
                        return new Message(Commands.AVARAGEABOVESEALVL.getCommandName(), user);
                    } else throw new ArgumentException();

                case "clear":

                    if (request.length < 2) {
                        return new Message(Commands.CLEAR.getCommandName(), user);
                    } else throw new ArgumentException();

                case "execute_script":
                    if (request.length == 2) {
                        File file = new File(request[1]);

                        if (!file.exists()) System.out.println("Исполняемый файл не найден");
                        if (file.isDirectory())
                            System.out.println("Необходим обязательный аргумент: Полное имя файла данных, не директория");

                        if (!file.canRead()
                                &&
                                !file.canWrite())
                            System.out.println("Ошибка доступа к исполняемому файлу: нет прав на чтение и запись");
                        if (!file.canRead())
                            System.out.println("Ошибка доступа к исполняемому файлу: нет прав на чтение");

                        if (!file.canWrite())
                            System.out.println("Ошибка доступа к исполняемому файлу: нет прав на запись");

                        return new Message(Commands.EXECUTESCRIPT.getCommandName(), file, user);

                    } else throw new ArgumentException();

                case "exit":
                    if (request.length < 2) {
                        return new Message(Commands.EXIT.getCommandName(), user);
                    } else throw new ArgumentException();

                case "help":

                    if (request.length < 2) {
                        return new Message(Commands.HELP.getCommandName(), user);
                    } else throw new ArgumentException();

                case "add":
                    if (request.length < 2) {
                        return new Message(Commands.ADD.getCommandName(),
                                new CityBuilder().create(), user);
                    } else throw new ArgumentException();

                case "min_by_climate":
                    if (request.length < 2) {
                        return new Message(Commands.MINBYCLIMATE.getCommandName(), user);
                    } else throw new ArgumentException();

                case "print_descending":
                    if (request.length < 2) {
                        return new Message(Commands.PRINTDESCENDING.getCommandName(), user);
                    } else throw new ArgumentException();

                case "remove_greater":
                    if (request.length < 2) {
                        return new Message(Commands.REMOVEGREATER.getCommandName()
                                , new CityBuilder().create(), user);
                    } else throw new ArgumentException();

                case "remove_lower":
                    if (request.length < 2) {
                        return new Message(Commands.REMOVELOVER.getCommandName(), new CityBuilder().create(), user);
                    } else throw new ArgumentException();

                case "replace_if_greater":
                    if (request.length == 2) {
                        try {
                            new Integer(request[1].trim());
                        } catch (NumberFormatException e) {
                            System.out.println("У команда " + Commands.REPLACEIFGERATER.getCommandName() + " аргумент - целое число!");
                            break;
                        }
                        return new Message(Commands.REPLACEIFGERATER.getCommandName(), request[1],
                                new CityBuilder().create(), user);
                    } else throw new ArgumentException();

                case "show":
                    if (request.length < 2) {
                        return new Message(Commands.SHOW.getCommandName(), user);
                    } else throw new ArgumentException();

                case "update_id":
                    if (request.length == 2) {
                        try {
                            Integer arg = new Integer(request[1].trim());
                        } catch (NumberFormatException e) {
                            System.out.println("У команда " + Commands.UPDATEID.getCommandName() + " аргумент - целое число!");
                            break;
                        }
                        return new Message(Commands.UPDATEID.getCommandName(), request[1].trim(), new CityBuilder().create(),
                                user);

                    } else throw new ArgumentException();

                case "info":
                    if (request.length < 2) {
                        return new Message(Commands.INFO.getCommandName(), user);
                    } else throw new ArgumentException();

                case "^D":
                    System.exit(0);
                default:
                    System.out.println("Вы ввели непрвильную команду. Проверьте правильность введенной команды.");
                    break;
            }
        }

        return null;


    }


    /*
    * Нейминг так себе.
    * Метод возвращает объект класса Message, в котором явно указана одна из двух команды _
    * _ Регистрация или Авторизация.
     */


    private void authorizationOrRegistration() {
        System.out.println(
                "Введите заглавную английскую букву 'А', чтобы войти в свой аккаунт." + System.lineSeparator() +
                        "или введите 'R', чтобы зарегистрировать новый аккаунт, если он отсутствует.");

        consoleReader.getDefaultConsoleSymbol();

        String line = consoleReader.getScanner().nextLine().trim();

        if (line.equals("R")) {
            isRegistration = true;
            commandName = "REGISTRATION";
            userInitialization();
        } else if (line.equals("A")) {
            isRegistration = false;
            commandName = "AUTHORIZATION";
            userInitialization();
        } else {
            System.out.println("Вам нужно выбрать: 'A' или 'R'");
            authorizationOrRegistration();
        }
        message = new Message(commandName, user, isRegistration);
    }

    /*
    * Метод инициализирует логин и пароль пользователя
     */

    private void userInitialization() {
        try {
            //ogin = console.readLine("Введите логин: ");
            System.out.print("Login : ");
            login = consoleReader.getScanner().nextLine().trim();

            if (login.trim().isEmpty()) {
                System.out.println("Логин не может быть пустым или состоять из пробелов");
                userInitialization();
            }

            System.out.print("Pass : ");
            MessageDigest md = MessageDigest.getInstance("MD5");
            //password = new BigInteger(1, md.digest(new String(console.readPassword("Введите пароль: "))
            //.getBytes(StandardCharsets.UTF_8)))
            //.toString(16);
            password = consoleReader.getScanner().nextLine().trim();

            password = new BigInteger(1, md.digest(password.getBytes())).toString();
            user = new User(login, password);

        } catch (NullPointerException e) {
            System.out.println("Пересмотрите формат ввода логина или пароля. Помните, что они не могут быть пустыми!");
            userInitialization();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Возникла проблема алгоритма шифрования пароля. Пожалуйста, попробуйте заново");
            userInitialization();

        }
    }

    public static void setLoginIsSuccess(boolean loginIsSuccess) {
        UserResponse.loginIsSuccess = loginIsSuccess;
    }
}
