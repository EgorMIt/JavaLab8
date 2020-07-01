package app.collection;


import java.io.Serializable;

public class Coordinates implements Serializable {
    private int     x; //Значение поля должно быть больше -847
    private float   y; //Максимальное значение поля: 704

    public Coordinates(int x, float y) {
        this.x =    x;
        assert x    >= -847 : "Поле не может иметь знаяение меньше -847";
        this.y =    y;
        assert y    <= 704 : "Поле не может иметь знаяение больше 704";
    }

    public void setY(float y) {
        this.y = y;

    }

    public int getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "X = " + x +
                ", Y = " + y;
    }
}

