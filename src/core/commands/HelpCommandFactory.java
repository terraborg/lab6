package core.commands;

import core.commands.structure.ClientCommand;
import core.managers.structure.ClientContext;
import core.managers.structure.ServerContext;
import core.commands.structure.CallbackUnit;
import core.commands.structure.Command;
import core.commands.structure.CommandFactory;

/**
 * Фабрика создающая экземпляры команды help
 */
public class HelpCommandFactory extends CommandFactory {
    public HelpCommandFactory(ClientContext client, ServerContext server) {
        super(client, server, "help", "вывести справку по доступным командам");
    }

    @Override
    public Command newInstance() {
        return new ClientCommand(getName(),getClient()) {
            @Override
            public void execute() {
                getClient().putCallback(new CallbackUnit(true,"Список всех доступных команд:\n"+getClient().getCommandList()));
            }
        };
    }
}
