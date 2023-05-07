package core.commands;

import core.commands.structure.*;
import core.managers.structure.ClientContext;
import core.managers.structure.ServerContext;

/**
 * Фабрика создающая экземпляры команды clear
 */
public class ClearCommandFactory extends CommandFactory {
    public ClearCommandFactory(ClientContext client, ServerContext server) {
        super(client, server, "clear", "очистить коллекцию");
    }

    @Override
    public Command newInstance() {
        return new NoArgsCommand(getName(),getClient(),getServer());
    }
}
