package core.commands.structure;

import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;

/**
 * Абстрактный класс фабрик команд.
 */
abstract public class CommandFactory {
    private final ServerContext server;



    private final String name;
    private final String description;

    public CommandFactory(ServerContext server, String name, String description) {
        this.server = server;
        this.name = name;
        this.description = description;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Предполагается что с вызовом этой функции фабрика считает с IOManager io все нужные данные аргументов
     * @return Возвращает true если считывание успешно и false в обратном случае
     */
    public boolean readArgs(CommandUnit unit)
    {
        return true;
    }
    abstract public Command newInstance(CallbackConsumer client);

    public ServerContext getServer() {
        return server;
    }

}
