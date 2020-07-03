package app.collection;

import exceptions.InvalidInputException;

import java.io.Serializable;

public enum Climate implements Comparable<Climate> , Serializable {


    TROPICAL_SAVANNA("Тропическая саванна", 1),
    MEDITERRANEAN("Средиземноморье", 2),
    POLAR_ICECAP("Полярный ледник ", 3);

    private String russianName;
    final private int number;
    private Climate o;

    Climate(String russianName, int number) {
        this.russianName = russianName;
        this.number = number;
    }
    @Override
    public String toString() {
        return super.toString();
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



