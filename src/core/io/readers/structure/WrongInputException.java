package core.io.readers.structure;

/**
 * Исключение выбрасываемое в случае неправильного формата введенных данных
 */
public class WrongInputException extends Exception{
    public WrongInputException()
    {
        super("Неверный формат ввода");
    }

    public WrongInputException(String message) {
        super(message);
    }
}
