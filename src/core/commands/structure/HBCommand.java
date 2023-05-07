package core.commands.structure;

import core.HumanBeing;
import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;

public class HBCommand extends ServerCommand{
    public HBCommand(String name, CallbackConsumer client, ServerContext server) {
        super(name, client, server);
    }

    private HumanBeing argument;
    public void addArgument(HumanBeing value) {
        argument = value;
    }

    @Override
    public CommandUnit getUnit() {
        return new CommandUnit(getName(),argument);
    }
}
