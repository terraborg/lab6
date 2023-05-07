package core.commands;

import core.commands.structure.*;
import core.managers.structure.ClientContext;
import core.managers.structure.ServerContext;

/**
 * Фабрика создающая экземпляры команды history
 */
public class HistoryCommandFactory extends CommandFactory {
    public HistoryCommandFactory(ClientContext client, ServerContext server) {
        super(client, server, "history", "вывести последние 14 команд (без их аргументов)");
    }

    @Override
    public Command newInstance() {
        return new ClientCommand(getName(),getClient()) {
            @Override
            public void execute() {
                getClient().putCallback(new CallbackUnit(true,"История выполненных команд:\n" + getClient().getHistory()));
            }
        };
    }
}
