package core.commands;

import core.commands.structure.*;
import core.managers.structure.ClientContext;
import core.managers.structure.ServerContext;

/**
 * Фабрика создающая экземпляры команды history_with_args
 */
public class HistoryWithArgsCommandFactory extends CommandFactory {
    public HistoryWithArgsCommandFactory(ClientContext client, ServerContext server) {
        super(client, server, "history_with_args", "вывести последние 14 команд (с их аргументами)");
    }

    @Override
    public Command newInstance() {
        return new ClientCommand(getName(),getClient()) {
            @Override
            public void execute() {
                getClient().putCallback(new CallbackUnit(true,"История выполненных команд:\n" + getClient().getHistoryWithArgs()));
            }
        };
    }
}
