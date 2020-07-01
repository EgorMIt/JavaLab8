package app.collection.building;

/**
 * Интерфейс хранит в себе методы, которые нужны для создания города
 *
 * @param <T> параметром должен передаваться класс города, объект которого мы хотим создать
 */

public interface ICityBuilder<T> {


    void setName();

    void setCoordinates();

    void setArea();

    void setPopulation();

    void setMetersAboveSeaLevel();

    void setClimate();

    void setGovernment();

    void setStandartOfLiving();

    void setGovernor();

    T getResult();

    T create();
}
