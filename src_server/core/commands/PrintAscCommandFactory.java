package core.commands;

import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;
import core.commands.structure.CallbackUnit;
import core.commands.structure.Command;
import core.commands.structure.CommandFactory;
import core.commands.structure.ServerCommand;

import java.util.Arrays;

/**
 * Фабрика создающая экземпляры команды print_ascending
 */
public class PrintAscCommandFactory extends CommandFactory {
    public PrintAscCommandFactory(ServerContext server) {
        super(server, "print_ascending", "вывести элементы коллекции в порядке возрастания");
    }

    @Override
    public Command newInstance(CallbackConsumer client) {
        return new ServerCommand(getName(),client,getServer()) {
            @Override
            public void execute() {
                var res = getServer().getDataBaseHolder().getAllElements();
                Arrays.sort(res);
                var b = new StringBuilder();
                for(var e : res)
                    b.append(e).append("\n");
                getClient().putCallback(new CallbackUnit(true,"Элементы коллекции:\n"+b));
            }
        };
    }
}
