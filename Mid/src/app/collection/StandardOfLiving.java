package app.collection;

import exceptions.ArgumentException;
import exceptions.InvalidInputException;

import java.io.Serializable;

public enum StandardOfLiving implements Comparable<StandardOfLiving> , Serializable {
    ULTRA_HIGH(     "Очень высокий",    1),
    MEDIUM(         "Средний",          2),
    VERY_LOW(       "Низкий",           3),
    NIGHTMARE(      "Кошмарный",        4);

    private String russianName;
    final private int number;

    StandardOfLiving(String russianName, int number) {
        this.russianName =  russianName;
        this.number =       number;
    }

    public static StandardOfLiving printByOrdinal(int a) {
        for (StandardOfLiving value : StandardOfLiving.values()) {
            if (value.getNumber() == a) {
                return value;
            }
        }
        throw new InvalidInputException("Не найдена категория уровня жизни: " + a);
    }

    public int getNumber() {
        return number;
    }

    public String getRussianName() {
        return russianName;
    }

    public static void printAllStrndartOfLiving() {
        System.out.println(ULTRA_HIGH.getNumber() + " - " + ULTRA_HIGH.getRussianName() + System.lineSeparator()
                + MEDIUM.getNumber() + " - " + MEDIUM.getRussianName() + System.lineSeparator()
                + VERY_LOW.getNumber() + " - " + VERY_LOW.getRussianName() + System.lineSeparator()
                + NIGHTMARE.getNumber() + " - " + NIGHTMARE.getRussianName() + System.lineSeparator());
    }
    public static StandardOfLiving StringNameToStandardOfLivingObj(String name){
        if (name.equals(ULTRA_HIGH.russianName))  return ULTRA_HIGH;
        if (name.equals(MEDIUM.russianName))     return MEDIUM;
        if (name.equals(VERY_LOW.russianName))      return VERY_LOW;
        if (name.equals(NIGHTMARE.russianName))      return NIGHTMARE;
        else {
            throw new ArgumentException();
        }
    }


}
