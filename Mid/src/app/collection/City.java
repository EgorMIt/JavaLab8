package app.collection;

import app.collection.ui.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Класс City
 */

public class City implements Comparable<Integer>, Serializable {

    private Integer                 id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String                  name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates             coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long                    area; //Значение поля должно быть больше 0
    private int                     population; //Значение поля должно быть больше 0
    private Long                    metersAboveSeaLevel;
    private Climate                 climate; //Поле может быть null
    private Government              government; //Поле может быть null
    private StandardOfLiving        standardOfLiving; //Поле не может быть null
    private Human                   governor; //Поле не может быть null
    private String                  owner;


    public City(String name, Coordinates coordinates, long area, int population, Long metersAboveSeaLevel,
                Climate climate, Government government, StandardOfLiving standardOfLiving, Human governor) {

        this.name =         name;
        assert name         != null : "Поле 'Имя' не может быть null";
        assert !"".equals(name) : "Поле 'Имя' не может быть строкой";
        this.coordinates =  coordinates;
        assert coordinates  != null : "Поле 'Координаты' не может быть null";
        this.area =         area;
        assert area         > 0 : "Поле 'Площадь' не может быть отрицательной";
        this.population =   population;
        assert population   > 0 : "Поле 'Население' не может быть отрицательным";
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        assert metersAboveSeaLevel < 8848 && metersAboveSeaLevel > 0 : "Поле 'Высота над уровнем моря' имеет недопустимное значение ";
        this.climate =      climate;
        this.government =   government;
        this.standardOfLiving = standardOfLiving;
        assert standardOfLiving != null : " Поле 'Уровень жизни' не может быть null";
        this.governor =     governor;
        setCreationDate();

        System.out.println("Город '" + name + "' инициализирован!");
    }


    /**
     * Перегруженный конструктор для созданния объекта с вводом в ручную id
     * @param id
     * @param name
     * @param coordinates
     * @param creationDate
     * @param area
     * @param population
     * @param metersAboveSeaLevel
     * @param climate
     * @param government
     * @param standardOfLiving
     * @param governor
     */
    public City(Integer id,String owner, String name,
                Coordinates coordinates,
                LocalDateTime creationDate,
                long area, int population,
                Long metersAboveSeaLevel,
                Climate climate,
                Government government,
                StandardOfLiving standardOfLiving,
                Human governor) {
        this.id =               id;
        this.owner =            owner;
        this.name =             name;
        this.coordinates =      coordinates;
        this.creationDate =     creationDate;
        this.area =             area;
        this.population =       population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.climate =          climate;
        this.government =       government;
        this.standardOfLiving = standardOfLiving;
        this.governor =         governor;

        System.out.println("Город '" + name + "' успешно инициализирован!");
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public long getArea() {
        return area;
    }

    public int getPopulation() {
        return population;
    }

    public Long getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public Climate getClimate() {
        try {
            return climate;
        }catch (NullPointerException e){
            return null;
        }
        /*if (climate != null) return climate;
        else return null;*/
    }

    public Government getGovernment() {
        if (government != null) return government;
        else return null;
    }

    public StandardOfLiving getStandardOfLiving() {
        return standardOfLiving;
    }

    public Human getGovernor() {
        return governor;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate() {
        this.creationDate = LocalDateTime.now();
    }

    public void setArea(long area) {
        this.area = area;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setMetersAboveSeaLevel(Long metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public void setClimate(Climate climate) {
        this.climate = climate;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public void setStandardOfLiving(StandardOfLiving standardOfLiving) {
        this.standardOfLiving = standardOfLiving;
    }

    public void setGovernor(Human governor) {
        this.governor = governor;
    }

    public void setId(Integer id){
        this.id = id;
    }

    private void generateId() {
        this.id = IdGenerator.generate();

    }

    public void initialisationsAutoGene(){
        generateId();
        setCreationDate();
    }

    @Override
    public String toString() {
        if (getClimate() == null) {
            if (getGovernment() == null) {
                return "id: " + id + System.lineSeparator() +
                        "владелец " + owner + System.lineSeparator() +
                        "название: " + name + System.lineSeparator() +
                        "координаты: " + coordinates + System.lineSeparator() +
                        "дата создания: " + creationDate + System.lineSeparator() +
                        "площадь: " + area + System.lineSeparator() +
                        "численность населения: " + population + System.lineSeparator() +
                        "высота над уровнем моря: " + metersAboveSeaLevel + System.lineSeparator() +
                        "климат: " + climate + System.lineSeparator() +
                        "тип правления: " + government + System.lineSeparator() +
                        "уровень жизни: " + standardOfLiving.getRussianName() + System.lineSeparator() +
                        "губернатор: " + governor + System.lineSeparator();
            } else {
                return "id: " + id + System.lineSeparator() +
                        "владелец " + owner + System.lineSeparator() +
                        "название: " + name + System.lineSeparator() +
                        "координаты: " + coordinates + System.lineSeparator() +
                        "дата создания: " + creationDate + System.lineSeparator() +
                        "площадь: " + area + System.lineSeparator() +
                        "численность населения: " + population + System.lineSeparator() +
                        "высота над уровнем моря: " + metersAboveSeaLevel + System.lineSeparator() +
                        "климат: " + climate + System.lineSeparator() +
                        "тип правления: " + government.getRussianName() + System.lineSeparator() +
                        "уровень жизни: " + standardOfLiving.getRussianName() + System.lineSeparator() +
                        "губернатор: " + governor + System.lineSeparator();
            }
        } else {
            if (getGovernment() == null) {
                return "id: " + id + System.lineSeparator() +
                        "владелец " + owner + System.lineSeparator() +
                        "название: " + name + System.lineSeparator() +
                        "координаты: " + coordinates + System.lineSeparator() +
                        "дата создания: " + creationDate + System.lineSeparator() +
                        "площадь: " + area + System.lineSeparator() +
                        "численность населения: " + population + System.lineSeparator() +
                        "высота над уровнем моря: " + metersAboveSeaLevel + System.lineSeparator() +
                        "климат: " + climate.getRussianName() + System.lineSeparator() +
                        "тип правления: " + government + System.lineSeparator() +
                        "уровень жизни: " + standardOfLiving.getRussianName() + System.lineSeparator() +
                        "губернатор: " + governor + System.lineSeparator();
            } else {
                return "id: " + id + System.lineSeparator() +
                        "владелец " + owner + System.lineSeparator() +
                        "название: " + name + System.lineSeparator() +
                        "координаты: " + coordinates + System.lineSeparator() +
                        "дата создания: " + creationDate + System.lineSeparator() +
                        "площадь: " + area + System.lineSeparator() +
                        "численность населения: " + population + System.lineSeparator() +
                        "высота над уровнем моря: " + metersAboveSeaLevel + System.lineSeparator() +
                        "климат: " + climate.getRussianName() + System.lineSeparator() +
                        "тип правления: " + government.getRussianName() + System.lineSeparator() +
                        "уровень жизни: " + standardOfLiving.getRussianName() + System.lineSeparator() +
                        "губернатор: " + governor + System.lineSeparator();
            }
        }
    }


    @Override
    public int compareTo(Integer o) {
        if (population < o) {
            return -1;
        }
        if (population > o) {
            return 1;
        } else {
            return 0;
        }
    }
}


