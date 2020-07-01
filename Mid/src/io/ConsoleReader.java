package io;


import java.util.Scanner;

/**
 * Класс, который хравнит в себе объект класса Сканер и стандартный символ командной строки
 */
public class ConsoleReader {
    public ConsoleReader() {
        scanner = new Scanner(System.in);
        }

    private Scanner     scanner;
    private String      defaultConsoleSymbol = ">> ";

    /**
     * Требудется перенос defailtConsoleSymbol на клиента
     */

    public void getDefaultConsoleSymbol() {
        System.out.print(defaultConsoleSymbol);
    }

    public void setDefaultConsoleSymbol(String defaultConsoleSymbol) {
        this.defaultConsoleSymbol = defaultConsoleSymbol;
    }


    public void setInput(Scanner scanner) {
        this.scanner = scanner;
    }

    public Scanner getScanner() {
        return scanner;
    }


}
