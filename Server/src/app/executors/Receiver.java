package app.executors;


import app.collection.City;
import app.commands.Command;
import app.db.DataBase;
import app.treatment.SendToClient;
import exceptions.ArgumentException;
import exceptions.MessageErrors;
import io.ConsoleReader;

import java.io.*;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Pattern;

;


/**
 * Класс, который обеспечивает выполнение команд
 */

public class Receiver implements MessageErrors {

    ConsoleReader input;
    Set<String> fileNames = new HashSet<>();
    RepositoryOfCity repositoryOfCity;
    ReadWriteLock lock;
    DataBase dataBase;


    public Receiver(RepositoryOfCity repositoryOfCity, DataBase dataBase) {
        this.repositoryOfCity = repositoryOfCity;
        input = new ConsoleReader();
        lock = new ReentrantReadWriteLock();
        this.dataBase = dataBase;
    }

    public DataBase getDataBase() {
        return dataBase;
    }


    /**
     * Реализация команды help, выводит все справку по командам консоли
     * @param commandTreeMap карта, хранящая все команды
     */

    public void help(TreeMap<String, Command> commandTreeMap) {
        lock.readLock().lock();
        try {
            SendToClient.write(System.lineSeparator() + "СПРАВКА ПО КОМАНДАМ:" + System.lineSeparator());
            for (Map.Entry<String, Command> entry : commandTreeMap.entrySet()) {
                SendToClient.write(entry.getKey() + " -" + entry.getValue().getArgs() + " " + entry.getValue().getDescription() + System.lineSeparator());
            }
            SendToClient.write(System.lineSeparator() + "*: если в описании команды имеется '[key]', значит у неё есть аругмент. " + System.lineSeparator() +
                    " : команда 'execute_script' имеет аргумент [file name] - название файла.");
        } finally {
            lock.readLock().unlock();
        }

    }

    /**
     * Обеспечивает удаление города из коллекции
     * @param id уникальный индификатор города
     * @see RepositoryOfCity#remove(int) реализация команды
     */

    public void remove(Integer id) {
        lock.writeLock().lock();
        try {
            if (dataBase.removeById(id,dataBase.getOwner())){
                repositoryOfCity.remove(id);
            }else {
                SendToClient.write("У вас нет прав на удление этого объекта. Или его не существует.");
            }
        } finally {
            lock.writeLock().unlock();
        }
    }


    /**
     * Обеспечивает вывод информации о коллекции
     *
     * @see RepositoryOfCity#info() реализация команды
     */
    public void info() {
        lock.readLock().lock();
        try {
            repositoryOfCity.info();
        } finally {
            lock.readLock().unlock();
        }
    }


    /**
     * Обеспечивает вывод всех объектов коллекции
     *
     * @see RepositoryOfCity#show() реализация команды
     */
    public void show() {
        lock.readLock().lock();
        try {
            repositoryOfCity.show();
        } finally {
            lock.readLock().unlock();
        }
    }


    /**
     * Обеспечивает очистку коллекции объектов
     */

    public void clear() {
        lock.writeLock().lock();
        try {
            if (dataBase.clearCities(dataBase.getOwner())){
                repositoryOfCity.clear();
            }else {
                SendToClient.write("Кажется, у вас нет объектов..");
            }
        } finally {
            lock.writeLock().unlock();
        }
    }


    /**
     * Обеспечивает вывод объекта с минимальным полем Climate
     *
     * @see RepositoryOfCity#minByClimate() реализация команды
     */

    public void minByClimate() {
        lock.readLock().lock();
        try {
            repositoryOfCity.minByClimate();
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Обеспечивает удаление всех элементов, больших чем заданный
     * @param city объект, с которым производится сравнение
     * @see RepositoryOfCity реализация команды
     */

    public void removeGreater(City city) {
        lock.writeLock().lock();
        try {
            if (dataBase.removeGreater(city)){
                repositoryOfCity.removeGreater(city);
            }else {
                SendToClient.write("У вас нет объектов, больших по численности населения чем заданный");
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void removeGreater() {
        lock.writeLock().lock();
        try {
            repositoryOfCity.removeGreater();
        } finally {
            lock.writeLock().unlock();
        }
    }


    /**
     * Обеспечивает удаление всех элементов, меньших чем заданный
     * @param city объект, с которым производится сравнение
     * @see RepositoryOfCity реализация команды
     */

    public void removeLower(City city) {
        lock.writeLock().lock();
        try {
            if (dataBase.removeLower(city)){
                repositoryOfCity.removeLower(city);
            }else{
                SendToClient.write("У вас нет объектов, меньших по численности населения, чем заданный");
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void removeLower() {
        lock.writeLock().lock();
        try {
            repositoryOfCity.removeLower();
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Обеспечивает вывод коллекции в порядке убывания
     *
     * @see RepositoryOfCity#printDescending()  реализация команды
     */

    public void printDescending() {
        lock.readLock().lock();
        try {
            repositoryOfCity.printDescending();
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Обеспечивает обновление элемента по заданному id
     * @param id уникальный номер элемента коллекции
     * @see RepositoryOfCity реализация команды
     */

    public void updateId(City city, Integer id) {
        lock.writeLock().lock();
        try {
            if (dataBase.updateId(id,city,dataBase.getOwner())){
                repositoryOfCity.updateId(city, id);
            }else {
                SendToClient.write("У вас нет прав на изменение этого объекта. Или его не существует.");
            }

        } finally {
            lock.writeLock().unlock();
        }
    }

    public void updateId(Integer id) {
        lock.writeLock().lock();
        try {
            repositoryOfCity.updateId(id);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Обеспечивает вывод среднего значения поля климат всех объектов
     *
     * @see RepositoryOfCity#averageMetersAboveSeaLevel() реализация команды
     */
    public void averageMetersAboveSeaLevel() {
        lock.readLock().lock();
        try {
            repositoryOfCity.averageMetersAboveSeaLevel();
        } finally {
            lock.readLock().unlock();
        }
    }


    /**
     * Обеспечивает замену элемента на новый, если новый больше старого
     *
     * @param id уникальный номер элемента коллекции, который хотим проверить
     */
    public void replaceIfGreater(City city, int id) {
        lock.writeLock().lock();
        try {
            if(dataBase.replace_if_greater(id,city,dataBase.getOwner())){
                repositoryOfCity.replaceIfGreater();
            }
            else {
                SendToClient.write("Объект не был изменен.");
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void replaceIfGreater(int id) {
        lock.writeLock().lock();
        try {
            repositoryOfCity.replaceIfGreater(id);
        } finally {
            lock.writeLock().unlock();
        }
    }


    /**
     * Обеспечивает завершение приложения
     */
    public void exit() {
        final String EXITWORD = "00101110010000010011001000111101";
        SendToClient.write(EXITWORD);
    }


    /**
     * Обеспечивает добавление элемента с заданным id
     *
     *            * @see RepositoryOfCity#insert(int)
     */
    public void add(City city) {
        lock.writeLock().lock();
        int id = dataBase.addCityInCitiesDB(city);
        try {
            if(id > 0){
                repositoryOfCity.add(city, id);
            }else{
                SendToClient.write("Возникли проблемы с добавлением элемента БД.");
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void add() {
        lock.writeLock().lock();
        try {
            repositoryOfCity.add();
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Обеспечиание сохранение коллекции в файл
     *
     * @param repositoryOfCity коллекция, которая хранит в себе города
     * @throws IOException возможная ошибка вывода в файл
     * @see CSVWriter#write(TreeMap)
     */


    /**
     * Занимается выполнением исполняемого файла
     *
     * @param invoker инвокер, который хранит в себе все команды
     */
    public void executeScript(File file, Invoker invoker) {
        lock.writeLock().lock();
        try {
            InputStream inputStream = new FileInputStream(file);
            Scanner scanner = new Scanner(inputStream);
            repositoryOfCity.setCityBuilderPath(scanner);


            String line = "";
            int lineCounter = 0;
            String regex = "\\s*\\w*\\_*\\s*\\w*\\s*\\.*\\w*\\s*";
            if (file.isDirectory()) {
                SendToClient.write("Необходим обязательный аргумент: Полное имя файла данных, не директория");
            }
            if (!file.exists()) {
                SendToClient.write("Исполняемый файл не найден");
            }
            if (!file.canRead() && !file.canWrite()) {
                SendToClient.write("Ошибка доступа к исполняемому файлу: нет прав на чтение и запись");
            }
            if (!file.canRead()) {
                SendToClient.write("Ошибка доступа к исполняемому файлу: нет прав на чтение");
            }
            if (!file.canWrite()) {
                SendToClient.write("Ошибка доступа к исполняемому файлу: нет прав на запись");
            }
            if (fileNames.contains(file.getName())) {
                SendToClient.write(System.lineSeparator() + "Ошибка рекурсии:" + System.lineSeparator() + "Не нужно меня ломать:(");
            } else {
                fileNames.add(file.getName());
                while (!line.trim().equals("exit") && scanner.hasNextLine()) {
                    lineCounter += 1;
                    if (Pattern.matches(regex.trim(), line)) {
                        try {
                            line = scanner.nextLine();
                            if (line.trim().equals("")) {
                                break;
                            } else {
                                String[] lines = line.split("\\s*\\s{1}\\s*");
                                String command = lines[0];
                                if (lines.length > 1) {
                                    String arg = lines[1];
                                    try {
                                        invoker.execute(command, invoker.getCommandMap(), arg);
                                    } catch (IllegalStateException e) {
                                        SendToClient.write("Команда не найдена. Проверьте правильность её написания. " + "Строка №" + lineCounter);
                                    } catch (ArgumentException | IOException e) {
                                        SendToClient.write(e.getMessage());
                                    }
                                } else {
                                    try {
                                        invoker.execute(command, invoker.getCommandMap());
                                    } catch (IllegalStateException e) {
                                        SendToClient.write("Команда не найдена. Проверьте правильность её написания. " +
                                                System.lineSeparator() + "Строка №" + lineCounter);
                                    } catch (ArgumentException e) {
                                        SendToClient.write(e.getMessage());
                                    } catch (NoSuchElementException e) {
                                        SendToClient.write("Неккоректное измените значение в файле и попробуйте заново!");
                                    }
                                }
                            }
                        } catch (ArrayIndexOutOfBoundsException | StackOverflowError e) {
                            SendToClient.write(e.getMessage());
                        }
                    } else {
                        scanner.nextLine();
                        SendToClient.write("Строка №" + lineCounter + " имеет неправильный формат." + System.lineSeparator() +
                                "Испрвьте ошибку и попробуйте еще раз.");
                    }
                }

            }
            fileNames.clear();

        } catch (FileNotFoundException e) {
            SendToClient.write(file.getPath());
            SendToClient.write("Файл для выполнения не найден");
        } catch (NoSuchElementException ignored) {
        } finally {
            lock.writeLock().unlock();
        }
    }


}
