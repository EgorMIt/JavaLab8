package ClientFullPack.Screens_Controllers;

import app.collection.*;

import java.time.LocalDateTime;

public class CityTable {
    private Integer                 id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String                  name; //Поле не может быть null, Строка не может быть пустой
    private int     x; //Значение поля должно быть больше -847
    private float   y; //Максимальное значение поля: 704
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long                    area; //Значение поля должно быть больше 0
    private int                     population; //Значение поля должно быть больше 0
    private Long                    metersAboveSeaLevel;
    private Climate climate; //Поле может быть null
    private Government government; //Поле может быть null
    private StandardOfLiving standardOfLiving; //Поле не может быть null
    private Integer                 age;
    private LocalDateTime dateOfBirthday;
    private String                  owner;

    CityTable(Integer id,String owner , String name, int x, float y, LocalDateTime creationDate, long area, int population,
              Long metersAboveSeaLevel,Climate climate,Government government,StandardOfLiving standardOfLiving,
              Integer age, LocalDateTime dateOfBirthday){
        this.id=id;
        this.owner=owner;
        this.name=name;
        this.x=x;
        this.y=y;
        this.creationDate = creationDate;
        this.area=area;
        this.population=population;
        this.metersAboveSeaLevel=metersAboveSeaLevel;
        this.climate=climate;
        this.government=government;
        this.standardOfLiving=standardOfLiving;
        this.age=age;
        this.dateOfBirthday=dateOfBirthday;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public float getY() {
        return y;
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
        return climate;
    }

    public Government getGovernment() {
        return government;
    }

    public StandardOfLiving getStandardOfLiving() {
        return standardOfLiving;
    }

    public Integer getAge() {
        return age;
    }

    public LocalDateTime getDateOfBirthday() {
        return dateOfBirthday;
    }

    public String getOwner() {
        return owner;
    }
}
