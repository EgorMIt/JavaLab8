package exceptions;

/**
 * Интерфейс, хранящий в себе стандартные сообщения при возникновении ошибок
 */

public interface MessageErrors {
    String messageOfInputValueError = "Введенное значение не входит допутимый диапазон!"
            + System.lineSeparator()
            + "Повторите ввод!" + System.lineSeparator();
    String messageOfInputFormatError = "Данные введены некорректно!" + System.lineSeparator()
            + "Повторите ввод!" + System.lineSeparator();
}
