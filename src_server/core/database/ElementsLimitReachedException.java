package core.database;

public class ElementsLimitReachedException extends Exception{
    public ElementsLimitReachedException()
    {
        super("Достигнут предел добавления элементов");
    }

    public ElementsLimitReachedException(String message)
    {
        super("Достигнут предел добавления элементов "+message);
    }
}
