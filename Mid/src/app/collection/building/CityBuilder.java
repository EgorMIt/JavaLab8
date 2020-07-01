package app.collection.building;

import app.collection.*;
import exceptions.MessageErrors;
import io.ConsoleReader;

import java.io.IOException;
import java.util.Scanner;


/**
 * Класс, который занимается создание объекта Город
 */


public class CityBuilder implements ICityBuilder<City>, MessageErrors {

    private String name = "Default";
    private Coordinates coordinates;
    private long area;
    private int population;
    private Long metersAboveSeaLevel;
    private Climate climate;
    private Government government;
    private StandardOfLiving standardOfLiving;
    private Human governor;
    private GovernorBuilder governorBuilder;
    private CityCoordinateBuilder cityCoordinateBuilder;
    private ConsoleReader input;

    public CityBuilder() throws IOException {
        input = new ConsoleReader();
        governorBuilder = new GovernorBuilder();
        cityCoordinateBuilder = new CityCoordinateBuilder();
    }

    /**
     * Метод позволяет принудительно изменить поток чтения данных
     *
     * @param inputPath объект класса Scanner
     */
    public void setInputPath(Scanner inputPath) {
        input.setInput(inputPath);
        governorBuilder.setInputPath(inputPath);
        cityCoordinateBuilder.setInputPath(inputPath);
    }

    /**
     * Метод задает название города
     */
    @Override
    public void setName() {

        System.out.println("Введите название города, значение типа 'String' [не менее 3-х букв и не более 20-ти]" + System.lineSeparator());

        input.getDefaultConsoleSymbol();
        String line = input.getScanner().nextLine().trim();
        try {
            if (line.length() > 3 && line.length() < 20) name = (line).trim();
            else {
                System.out.println(messageOfInputValueError);
                setName();
            }
        } catch (Exception e) {
            System.out.println(messageOfInputFormatError);
            setName();
        }
    }

    @Override
    public void setCoordinates() {
        coordinates = cityCoordinateBuilder.create();
    }

    /**
     * Метод задает площадь
     */
    @Override
    public void setArea() {
        System.out.println(System.lineSeparator() + "Введите площадь города, целочисленное значение " + "(не превосходит " + Integer.MAX_VALUE + ")" + System.lineSeparator());
        input.getDefaultConsoleSymbol();
        String line = input.getScanner().nextLine().trim();
        try {
            if (Long.parseLong(line) > 0 && Long.parseLong(line) < Integer.MAX_VALUE) area = Long.parseLong(line);
            else {
                System.out.println(messageOfInputValueError);
                setArea();
            }
        } catch (Exception e) {
            System.out.println(messageOfInputFormatError);
            setArea();
        }
    }

    /**
     * Метод задает численность населения
     */
    @Override
    public void setPopulation() {
        System.out.println(System.lineSeparator() + "Введите численность начеления города, целочисленное значение < " + Integer.MAX_VALUE + System.lineSeparator());
        input.getDefaultConsoleSymbol();
        String line = input.getScanner().nextLine().trim();
        try {
            if (Integer.parseInt(line) > 0 && Integer.parseInt(line) < Integer.MAX_VALUE) {
                population = Integer.parseInt(line);
            } else {
                System.out.println(messageOfInputValueError);
                setPopulation();
            }
        } catch (Exception e) {
            System.out.println(messageOfInputFormatError);
            setPopulation();
        }

    }

    /**
     * Метод задает высоту над уровнем моря
     */
    @Override
    public void setMetersAboveSeaLevel() {
        System.out.println(System.lineSeparator() + "Введите значение высоты над уровнем моря, целочисленное значение " + " (не превосходит " + Integer.MAX_VALUE + ")");
        input.getDefaultConsoleSymbol();
        String line = input.getScanner().nextLine().trim();
        try {
            if (Long.parseLong(line) > 0 && Long.parseLong(line) < Integer.MAX_VALUE) {
                metersAboveSeaLevel = Long.parseLong(line);
            } else {
                System.out.println(messageOfInputValueError + System.lineSeparator());
                setMetersAboveSeaLevel();
            }
        } catch (Exception e) {
            System.out.println(messageOfInputFormatError);
            setMetersAboveSeaLevel();
        }
    }

    /**
     * Метод задает климат
     */
    @Override
    public void setClimate() {
        System.out.println(System.lineSeparator() + "Выберите тип климата из перечисленного списка:" + System.lineSeparator());
        Climate.printAllClimateTypes();
        notIndicatePossibleAnswerNotIndicate();
        System.out.println("Для этого введите соответствующий ему порядковый номер" + System.lineSeparator());
        input.getDefaultConsoleSymbol();
        String line = input.getScanner().nextLine().trim();
        try {
            if (Integer.parseInt(line.trim()) == 0) {
                System.out.println("Ок, пропускаем ввод поля климата" + System.lineSeparator());
            } else {
                if (Integer.parseInt(line.trim()) == 0) {
                    System.out.println();
                } else if (Integer.parseInt(line.trim()) == Climate.TROPICAL_SAVANNA.getNumber()) {
                    climate = Climate.TROPICAL_SAVANNA;
                } else if (Integer.parseInt(line.trim()) == Climate.MEDITERRANEAN.getNumber()) {
                    climate = Climate.MEDITERRANEAN;
                } else if (Integer.parseInt(line.trim()) == Climate.POLAR_ICECAP.getNumber()) {
                    climate = Climate.POLAR_ICECAP;
                } else {
                    System.out.println(messageOfInputFormatError +
                            " Ты должен ввести только порядковый номер! ");
                    setClimate();
                }

            }
        } catch (Exception e) {
            System.out.println(messageOfInputFormatError);
            setClimate();
        }

    }

    /**
     * Метод задает правительственный строй
     */
    @Override
    public void setGovernment() {
        System.out.println(System.lineSeparator() + "Выберите тип правительственного строя из перечисленного списка:");
        Government.printAllGovernent();
        notIndicatePossibleAnswerNotIndicate();
        System.out.println("Для этого введите соответствующий ему порядковый номер");
        input.getDefaultConsoleSymbol();
        String line = input.getScanner().nextLine().trim();
        try {
            if (Integer.parseInt(line.trim()) == 0) {
                System.out.println("Ок, пропускаем ввод поля климата");
            } else {
                if (Integer.parseInt(line.trim()) == Government.KRITARCHY.getNumber()) {
                    government = Government.KRITARCHY;
                } else if (Integer.parseInt(line.trim()) == Government.OLIGARCHY.getNumber()) {
                    government = Government.OLIGARCHY;
                } else if (Integer.parseInt(line.trim()) == Government.TIMOCRACY.getNumber()) {
                    government = Government.TIMOCRACY;
                } else if (Integer.parseInt(line.trim()) == Government.TOTALITARIANISM.getNumber()) {
                    government = Government.TOTALITARIANISM;
                } else {
                    System.out.println(messageOfInputFormatError + System.lineSeparator() +
                            " Ты должен ввести только порядковый номер! ");
                    setGovernment();
                }

            }
        } catch (Exception e) {
            System.out.println(messageOfInputFormatError);
            setGovernment();
        }
    }

    /**
     * Метод задает уровень жизни
     */
    @Override
    public void setStandartOfLiving() {
        System.out.println(System.lineSeparator() + "Выберите уровень жизни в городе из перечисленного списка:");
        StandardOfLiving.printAllStrndartOfLiving();
        System.out.println("Для этого введите соответствующий ему порядковый номер");
        input.getDefaultConsoleSymbol();
        String line = input.getScanner().nextLine().trim();
        try {
            if (Integer.parseInt(line.trim()) == StandardOfLiving.ULTRA_HIGH.getNumber()) {
                standardOfLiving = StandardOfLiving.ULTRA_HIGH;
            } else if (Integer.parseInt(line.trim()) == StandardOfLiving.MEDIUM.getNumber()) {
                standardOfLiving = StandardOfLiving.MEDIUM;
            } else if (Integer.parseInt(line.trim()) == StandardOfLiving.VERY_LOW.getNumber()) {
                standardOfLiving = StandardOfLiving.VERY_LOW;
            } else if (Integer.parseInt(line.trim()) == StandardOfLiving.NIGHTMARE.getNumber()) {
                standardOfLiving = StandardOfLiving.NIGHTMARE;
            } else {
                System.out.println(messageOfInputFormatError + System.lineSeparator() +
                        " Ты должен ввести только порядковый номер! ");
                setStandartOfLiving();
            }
        } catch (Exception e) {
            System.out.println(messageOfInputFormatError);
            setStandartOfLiving();
        }

    }

    @Override
    public void setGovernor() {
        this.governor = governorBuilder.create();
    }

    /**
     * Сборка и создание объекта класса City
     *
     * @return новый объект класса City
     * @see City
     */
    @Override
    public City create() {
        System.out.println(System.lineSeparator() + "Начнем инициализацию города!");
        setName();
        setCoordinates();
        setArea();
        setPopulation();
        setMetersAboveSeaLevel();
        setClimate();
        setGovernment();
        setStandartOfLiving();
        setGovernor();
        return new City(name, coordinates, area, population, metersAboveSeaLevel, climate, government, standardOfLiving, governor);
    }

    @Override
    public City getResult() {
        return new City(name, coordinates, area, population, metersAboveSeaLevel, climate, government, standardOfLiving, governor);
    }


    public void notIndicatePossibleAnswerNotIndicate() {
        System.out.println(0 + " - " + "Не указывать" + System.lineSeparator());
    }

}

