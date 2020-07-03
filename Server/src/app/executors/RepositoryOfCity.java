package app.executors;

import app.collection.City;
import app.collection.ComparatorClimate;
import app.collection.building.CityBuilder;
import app.collection.ui.IdGenerator;
import app.db.DataBase;
import app.treatment.SendToClient;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Класс, который хранит в себе коллекцию объектов и хранит реализациюю некоторых команд, связанных с коллекцией напрямую
 */

public class RepositoryOfCity {


    private final TreeMap<Integer, City> citiesCollection = new TreeMap<Integer, City>();
    private CityBuilder cityBuilder;
    private City city;
    private LocalDateTime birthdayOfTreemap;
    private DataBase dataBase;


    public RepositoryOfCity(DataBase dataBase) {

        try {
            this.cityBuilder = new CityBuilder();
            this.birthdayOfTreemap = LocalDateTime.now();
            this.dataBase = dataBase;
            dataBase.loadCollection(citiesCollection);
        } catch (IOException | SQLException e) {
            System.out.println("Чпуньк");
        }

    }

    /**
     * Реализация добавления объекта в коллекцию
     */


    public void setCityBuilderPath(Scanner inputStream) {
        cityBuilder.setInputPath(inputStream);
    }

    /**
     * Реализация получение объекта из коллекции по id
     *
     * @param id значение id объекта, который хотим получить
     * @return возвращает объект, если в коллекции он присутстывует; иначе - сообщение об его отсутствии
     */


    public City get(int id) {
        if (getCitiesCollection().get(id) != null) {
            return getCitiesCollection().get(id);
        } else {
            SendToClient.write("В коллекции нет объекта с заданным ключем!");
            return null;
        }

    }

    /**
     * Реализация удаления объекта из коллекции по заданному id
     *
     * @param id значение id элемента, который хотим удалить
     */


    public void remove(int id) {
        if (!getCitiesCollection().isEmpty()) {
            if (getCitiesCollection().get(id) != null) {
                SendToClient.write("Объект " + getCitiesCollection().get(id).getName() + " удален");
                IdGenerator.remove(id);
                getCitiesCollection().remove(id);
            } else {
                SendToClient.write("Не найден объект с данным id!");
            }
        } else {
            SendToClient.write("Коллекция пустая. Удалить объект невозможно!");
        }
    }

    /**
     * Метод реализует вывод количества элементов, находящихся в коллекции в данный момент
     */

    public void size() {
        SendToClient.write("В коллекции сейчас " + getCitiesCollection().size() + " объекта/ов");
    }

    /**
     * Реализация отчистки коллекции от всех объектов
     */

    public synchronized void clear() {
        if (!getCitiesCollection().isEmpty()) {
            try {
                dataBase.loadCollection(citiesCollection);
            } catch (SQLException e) {
                SendToClient.write("Коллекция обновлена =)");
            }
            SendToClient.write("Элементы, пренаждлежащие вам, успешно удалены!");
        } else {
            SendToClient.write("Коллекция пустая!");
        }
    }

    /**
     * Реализация вывода всех объектов коллекции
     */

    public void show() {

        for (Map.Entry<Integer, City> pair : getCitiesCollection().entrySet()) {
            City v = pair.getValue();
            SendToClient.write(v);
        }
    }

    public void removeGreater(City city) {
        if (!getCitiesCollection().isEmpty()) {
            try {
                dataBase.loadCollection(citiesCollection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            SendToClient.write("Удалены все элементы, большие чем введенный город: " + city.getName() + " с численностью населения:" + city.getPopulation());
        } else {
            SendToClient.write("Коллекция пустая!");
        }
    }

    public void removeGreater() {
        if (!getCitiesCollection().isEmpty()) {
            city = cityBuilder.create();
            if (dataBase.removeGreater(city)) {
                try {
                    dataBase.loadCollection(citiesCollection);
                    SendToClient.write("Удалены все элементы, большие чем введенный город: " + city.getName() + " с численностью населения:" + city.getPopulation());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            SendToClient.write("Коллекция пустая!");
        }
    }

    /**
     * Реализация команды удаления всех элементов, меньших чем заданный
     */

    public void removeLower(City city) {
        if (!getCitiesCollection().isEmpty()) {
            try {
                dataBase.loadCollection(citiesCollection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            SendToClient.write("Удалены все элементы, меньшие чем введенный город: " + city.getName() + "с численностью населения:" + city.getPopulation());
        } else {
            SendToClient.write("Коллекция пустая!");
        }

    }

    public void removeLower() {
        if (!getCitiesCollection().isEmpty()) {
            city = cityBuilder.create();
            if (dataBase.removeLower(city)) {
                try {
                    dataBase.loadCollection(citiesCollection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                SendToClient.write("Удалены все элементы, меньшие чем введенный город: " + city.getName() + "с численностью населения:" + city.getPopulation());
            }
        } else {
            SendToClient.write("Коллекция пустая!");
        }

    }

    public TreeMap<Integer, City> getCitiesCollection() {
        return citiesCollection;
    }

    /**
     * Реализация команды вывода информации о коллекции обхектов
     */
    public void info() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String collectionCreationDate = getBirthdayOfTreemap().format(formatter);
        SendToClient.write("Информация о коллекции: " + System.lineSeparator() +
                "- время инициализации коллекции: " + collectionCreationDate + System.lineSeparator() +
                "- типа коллекции: " + getCitiesCollection().getClass().getTypeName() + System.lineSeparator() +
                "- количество элементов коллекции: " + getCitiesCollection().size());
    }


    public LocalDateTime getBirthdayOfTreemap() {
        return birthdayOfTreemap;
    }

    /**
     * Реализация команды обновления элемента по заданному id
     *
     * @param id id элменета, который хотим обновить
     */


    public void updateId(City city, Integer id) {
        if (!getCitiesCollection().isEmpty()) {
            if (getCitiesCollection().get(id) == null) {
                SendToClient.write("Объекта с заданным id не существует" + System.lineSeparator() + "Обновление объекта невозможно!");
            } else {
                getCitiesCollection().remove(id, get(id));
                city.setId(id);
                city.setOwner(dataBase.getOwner());
                getCitiesCollection().put(id, city);
                SendToClient.write("Поле успешно обновлено!");
            }
        } else {
            SendToClient.write("Коллекция пустая!");
        }

    }

    public void updateId(Integer id) {
        if (!getCitiesCollection().isEmpty()) {
            if (getCitiesCollection().get(id) == null) {
                SendToClient.write("Объекта с заданным id не существует" + System.lineSeparator() + "Обновление объекта невозможно!");
            } else {
                getCitiesCollection().remove(id, get(id));
                city = cityBuilder.create();
                city.setId(id);
                city.setOwner(dataBase.getOwner());
                if (dataBase.updateId(id, city, dataBase.getOwner())) {
                    getCitiesCollection().put(id, city);
                    SendToClient.write("Поле успешно обновлено!");
                } else {
                    SendToClient.write("Не удалось обновить объект в базе данных.. ");
                }

            }
        } else {
            SendToClient.write("Коллекция пустая!");
        }

    }

    /**
     * Реализация метода, который выводит среднее значение поля высоты над уровнем моря всех объектов
     */

    public void averageMetersAboveSeaLevel() {
        long citymeterAboveSeaLevel = 0L;
        for (Map.Entry<Integer, City> pair : getCitiesCollection().entrySet()) {
            City v = pair.getValue();
            citymeterAboveSeaLevel = citymeterAboveSeaLevel + v.getMetersAboveSeaLevel();
        }
        try {
            citymeterAboveSeaLevel /= getCitiesCollection().size();
            SendToClient.write("Среднее значение поля 'высота над уровнем моря' элементов коллекции = " + citymeterAboveSeaLevel);
        } catch (ArithmeticException e) {
            SendToClient.write("Т.к. в коллекции нет элементов - среднеее значение = 0");
        }
    }

    /**
     * Реализация вывода элемента коллекции с минимальным значением поля Климат
     */


    public void minByClimate() {
        Comparator<Map.Entry<Integer, City>> comparatorClimate = new ComparatorClimate();
        if (!getCitiesCollection().isEmpty()) {
            SendToClient.write(citiesCollection.entrySet().stream().min(comparatorClimate).get().getValue().getName());
        } else {
            SendToClient.write("Коллекция пустая!");
        }

    }


    public void printDescending() {
        if (!getCitiesCollection().isEmpty()) {
            ArrayList<City> citiesArray = new ArrayList<>();
            for (Map.Entry<Integer, City> pair : getCitiesCollection().entrySet()) {
                City v = pair.getValue();
                citiesArray.add(v);
            }
            citiesArray.sort((o1, o2) -> -o1.compareTo(o2.getPopulation()));
            for (City city : citiesArray) {
                SendToClient.write(city.toString());
            }
        } else {
            SendToClient.write("Коллекция пустая!");
        }
    }
    //new ComparatorPopulation().reversed()


    /**
     * Реализация команды замены элемента по заданному id, если он больше, чем старый
     */

    public void replaceIfGreater() {
        if (!getCitiesCollection().isEmpty()) {
            try {
                dataBase.loadCollection(citiesCollection);
                SendToClient.write("Элемент успешно обновлен");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void replaceIfGreater(int key) {
        if (!getCitiesCollection().isEmpty()) {
            city = cityBuilder.create();
            city.setId(key);
            if (dataBase.replace_if_greater(key, city, dataBase.getOwner())) {
                try {
                    dataBase.loadCollection(citiesCollection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                SendToClient.write("Элемент успешно обновлен");
            } else {
                SendToClient.write("Значение нового элемента меньше старого!");
            }
        } else {
            SendToClient.write("Коллекция пустая!");

        }
    }


    public void add(City city, int id) {
        try {
            city.setId(id);
            city.setOwner(dataBase.getOwner());
            getCitiesCollection().put(city.getId(), city);
            SendToClient.write("Город '" + city.getName() + "'  добавлен в коллекцию");
        } catch (StackOverflowError e) {
            SendToClient.write("Коллекция переполнена!");
        }
    }

    public void add() {
        try {
            city = cityBuilder.create();
            int id = dataBase.addCityInCitiesDB(city);
            if (id > 0) {
                getCitiesCollection().put(id, city);
                SendToClient.write("Объект добавлен!");
            }
        } catch (StackOverflowError e) {
            SendToClient.write("Коллекция переполнена!");
        }
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(DataBase dataBase) {
        this.dataBase = dataBase;
    }
}

