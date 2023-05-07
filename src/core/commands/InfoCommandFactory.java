package core.commands;

import core.commands.structure.*;
import core.managers.structure.ClientContext;
import core.managers.structure.ServerContext;

/**
 * Фабрика создающая экземпляры команды info
 */
public class InfoCommandFactory extends CommandFactory {
    public InfoCommandFactory(ClientContext client, ServerContext server) {
        super(client, server,"info" , "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
    }

    @Override
    public Command newInstance() {
        return new NoArgsCommand(getName(),getClient(),getServer());
    }
}
