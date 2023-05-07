package core.commands;

import core.commands.structure.*;
import core.managers.structure.ClientContext;
import core.HumanBeing;
import core.managers.structure.ServerContext;
import core.io.managers.IOManager;
import core.io.readers.HumanBeingInput;
import core.io.readers.structure.WrongInputException;

/**
 * Фабрика создающая экземпляры команды add_if_max
 */
public class AddIfMaxCommandFactory extends CommandFactory {
    public AddIfMaxCommandFactory(ClientContext client, ServerContext server) {
        super(client, server, "add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
    }

    private HumanBeing argument;

    @Override
    public boolean readArgs(IOManager io) {
        try {
            io.getIn().nextLine();
            argument = new HumanBeingInput(io).read();
        } catch (WrongInputException e) {
            return false;
        }
        return argument!=null;
    }

    @Override
    public Command newInstance() {
        var res = new HBCommand(getName(),getClient(),getServer());
        res.addArgument(argument);
        return res;
    }

}
