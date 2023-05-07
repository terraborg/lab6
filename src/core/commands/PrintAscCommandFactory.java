package core.commands;

import core.commands.structure.*;
import core.managers.structure.ClientContext;
import core.managers.structure.ServerContext;

import java.util.Arrays;

/**
 * Фабрика создающая экземпляры команды print_ascending
 */
public class PrintAscCommandFactory extends CommandFactory {
    public PrintAscCommandFactory(ClientContext client, ServerContext server) {
        super(client, server, "print_ascending", "вывести элементы коллекции в порядке возрастания");
    }

    @Override
    public Command newInstance() {
        return new NoArgsCommand(getName(),getClient(),getServer());
    }
}
