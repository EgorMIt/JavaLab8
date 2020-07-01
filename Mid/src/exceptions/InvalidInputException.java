package exceptions;

/**
 * Обишка неправильного аргумента значения
 */

public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String message){
        super("Вы ввели аргумент неправильно");
    }
}
