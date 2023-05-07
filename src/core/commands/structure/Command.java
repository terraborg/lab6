package core.commands.structure;

import core.managers.structure.CallbackConsumer;
import core.managers.structure.ClientContext;


import java.io.Serial;
import java.io.Serializable;

/**
 * @author Volovich Alexey
 * Абстрактный класс задающий поведение команд.
 */
public abstract class Command{

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private CallbackConsumer client;

    protected CallbackConsumer getClient() {
        return client;
    }

    public Command(String name, CallbackConsumer client) {
        this.name = name.trim().split(" ")[0];
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
