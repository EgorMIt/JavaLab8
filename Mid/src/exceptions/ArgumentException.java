package exceptions;

/**
 * Ошибка неккоректного значения аргмента команды
 */

public class ArgumentException extends RuntimeException {
    public ArgumentException(){
        super("Вы ввели неккоректные аргументы для команды! " + System.lineSeparator() + "Попробуйте заново!");

    }
}

