package app.collection;

import exceptions.InvalidInputException;

import java.io.Serializable;

public enum Climate implements Comparable<Climate> , Serializable {


    TROPICAL_SAVANNA("Тропическая саванна"),
    MEDITERRANEAN(      "Средиземноморье"),
    POLAR_ICECAP(       "Полярный ледник");

    private final String     russianName;
    private int number = 0;
    private Climate     o;

    Climate(String russianName) {
        this.russianName =  russianName;
    }


    @Override
    public String toString() {
        return super.toString();
    }

    public static Climate printByOrdinal(int a) {
        for (Climate value : Climate.values()) {
            if (value.getNumber() == a) {
                return value;
            }
        }
        throw new InvalidInputException("Не найдет соответствубщий вид климата: " + a);
    }


    public int getNumber() {
        return number;
    }


    public String getRussianName() {
        try {
            return russianName;
        }catch (NullPointerException e){
            return null;
        }

    }


    public static void printAllClimateTypes() {
        System.out.println(TROPICAL_SAVANNA.getNumber() + " - " + TROPICAL_SAVANNA.getRussianName() + System.lineSeparator()
                + MEDITERRANEAN.getNumber() + " - " + MEDITERRANEAN.getRussianName() + System.lineSeparator()
                + POLAR_ICECAP.getNumber() + " - " + POLAR_ICECAP.getRussianName() + System.lineSeparator());
    }

    public static Climate StringNameToClimateObj(String name){
        if (name == null)                               return null;
        if (name.equals(TROPICAL_SAVANNA.russianName))  return TROPICAL_SAVANNA;
        if (name.equals(MEDITERRANEAN.russianName))     return MEDITERRANEAN;
        if (name.equals(POLAR_ICECAP.russianName))      return POLAR_ICECAP;
        else {
            return null;
        }
     }


}



