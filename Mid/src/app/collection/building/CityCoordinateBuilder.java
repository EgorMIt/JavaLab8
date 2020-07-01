package app.collection.building;

import app.collection.Coordinates;
import io.ConsoleReader;
import exceptions.MessageErrors;

import java.io.IOException;
import java.util.Scanner;

/**
 * Класс занимается инициализацией координат города
 */

public class CityCoordinateBuilder implements ICoordinateBuilder, MessageErrors {

    private ConsoleReader input;
    private int x;
    private float y;

    public CityCoordinateBuilder() throws IOException {
        input = new ConsoleReader();
    }


    /**
     * Метод инициализирует Х - координату города
     */
    @Override
    public void setX() {
        System.out.println("Введите значение координаты 'x', целое число" + " (не превосходит " + Long.MAX_VALUE + " и больше -847 )");
        input.getDefaultConsoleSymbol();
        String line = input.getScanner().nextLine().trim();
        try {
            if (Integer.parseInt(line) > -847) {
                x = Integer.parseInt(line);
            } else {
                System.out.println(messageOfInputValueError);
                setX();
            }
        } catch (Exception e) {
            System.out.println(messageOfInputFormatError);
            setX();
        }
    }

    public void setInputPath(Scanner inputPath) {
        input.setInput(inputPath);
    }

    /**
     * Метод инициализирует Y - координату города
     */
    @Override
    public void setY() {
        System.out.println("Введите значение координаты 'y', вещественное число" + " (значение меньше 704)");
        input.getDefaultConsoleSymbol();
        String line = input.getScanner().nextLine().trim();
        try {
            if (Float.parseFloat(line) < 704) {
                y = Float.parseFloat(line);
            } else {
                System.out.println(messageOfInputValueError);
                setY();
            }
        } catch (Exception e) {
            System.out.println(messageOfInputFormatError);
            setY();
        }
    }

    /**
     * Метод занимается созданием объекта Coordinates
     *
     * @return возвращает новый объект класса координат города
     * @see Coordinates
     */

    @Override
    public Coordinates create() {
        System.out.println(System.lineSeparator() + "Переходим к заданию координат города!");
        setX();
        setY();
        return new Coordinates(x, y);
    }

}
