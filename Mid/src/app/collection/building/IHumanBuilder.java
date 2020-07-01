package app.collection.building;

import exceptions.InvalidInputException;

public interface IHumanBuilder {

    void setAge() throws InvalidInputException;

    void setPosition();

    void setBirthday();

    Object create();
}
