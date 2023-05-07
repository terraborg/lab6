package core.commands.structure;

import core.managers.structure.ClientContext;
import core.managers.structure.ServerContext;

public class NoArgsCommand extends ServerCommand{
    public NoArgsCommand(String name, ClientContext client, ServerContext server) {
        super(name, client, server);
    }

    @Override
    public CommandUnit getUnit() {
        return new CommandUnit(getName());
    }
}
