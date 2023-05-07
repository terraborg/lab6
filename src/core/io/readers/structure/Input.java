package core.io.readers.structure;

import core.io.managers.IOManager;

/**
 * Абстрактный класс задающий структуру классов отвечающих за ввод и валидацию различных данных
 * @param <T> - Тип вводимых данных
 */
abstract public class Input<T> {
    protected IOManager io;
    protected boolean repeat;
    protected String invMessage;
    protected boolean nullable;

    /**
     * Конструктор
     * @param io - менеджер ввода-вывода
     * @param repeat - повторять ли попытки ввода
     * @param nullable - могут ли данные быть null
     * @param invMessage - сообщение приглашающее к вводу
     */
    public Input(IOManager io, boolean repeat, boolean nullable, String invMessage) {
        this.io = io;
        this.repeat = repeat;
        this.invMessage = invMessage;
        this.nullable = nullable;
    }

    /**
     * Считынвает данные
     * @param validator - Валидатор для данных
     * @return Возвращает введенное значение
     * @throws WrongInputException - Исключение связанное с неправильным форматом ввода
     */
    abstract public T read(Validator<T> validator) throws WrongInputException;

    /**
     * Считывает данных применяя валидатор по умолчанию
     */
    abstract public T read() throws WrongInputException;
}
