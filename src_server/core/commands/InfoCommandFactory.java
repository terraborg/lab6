package core.commands;

import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;
import core.commands.structure.CallbackUnit;
import core.commands.structure.Command;
import core.commands.structure.CommandFactory;
import core.commands.structure.ServerCommand;

/**
 * Фабрика создающая экземпляры команды info
 */
public class InfoCommandFactory extends CommandFactory {
    public InfoCommandFactory(ServerContext server) {
        super(server,"info" , "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
    }

    @Override
    public Command newInstance(CallbackConsumer client) {
        return new ServerCommand(getName(),client,getServer()) {
            @Override
            public void execute() {
                var info = getServer().getDataBaseHolder().getInfo();
                getClient().putCallback(new CallbackUnit(true,"",info));
            }
        };
    }
}
