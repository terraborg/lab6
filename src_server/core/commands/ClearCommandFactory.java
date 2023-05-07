package core.commands;

import core.commands.structure.*;
import core.managers.CallbackManager;
import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;

/**
 * Фабрика создающая экземпляры команды clear
 */
public class ClearCommandFactory extends CommandFactory {
    public ClearCommandFactory(ServerContext server) {
        super(server, "clear", "очистить коллекцию");
    }

    @Override
    public Command newInstance(CallbackConsumer client) {
        return new ServerCommand(getName(),client,getServer()) {
            @Override
            public void execute() {
                getServer().getDataBaseHolder().clear();
                getClient().putCallback(new CallbackUnit(true));
                getServer().executeCommand(new CommandUnit("save"));
            }
        };
    }
}
