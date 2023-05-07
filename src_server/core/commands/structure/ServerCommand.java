package core.commands.structure;

import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;

/**
 * Абстрактный класс для команд, которые необходимо передать на сервер.
 */
abstract public class ServerCommand extends Command{
    private ServerContext server;

    public ServerCommand(String name, CallbackConsumer client, ServerContext server) {
        super(name, client);
        this.server = server;
    }

    protected ServerContext getServer() {
        return server;
    }

    public void setServer(ServerContext server) {
        this.server = server;
    }
}
