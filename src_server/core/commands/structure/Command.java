package core.commands.structure;

import core.managers.structure.CallbackConsumer;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Volovich Alexey
 * Абстрактный класс задающий поведение команд.
 */
public abstract class Command implements Serializable {

    @Serial
    private static final long serialVersionUID = 4843046006849211136L;
    private final String name;
    private CallbackConsumer client;

    protected CallbackConsumer getClient() {
        return client;
    }

    public Command(String name, CallbackConsumer client) {
        this.name = name;
        this.client = client;
    }

    /**
     * @return Возвращает имя команды.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Применяет команду
     */
    abstract public void execute();

    /**
     * Этот метод следует использовать для предварительных действий с командой, таких как, например, передача на сервер
     */
    public void preExecute()
    {
        execute();
    }

    /**
     * Переопределяя данный метод можно добавить к команде аргументы нужного типа
     */
    public void addArgument(Object value) {

    }

    public void setClient(CallbackConsumer client) {
        this.client = client;
    }

    @Override
    public String toString()
    {
        return getName().trim().split(" ")[0];
    }
}
