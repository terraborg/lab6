package core.commands;

import core.commands.structure.*;
import core.managers.structure.ClientContext;
import core.HumanBeing;
import core.managers.structure.ServerContext;
import core.io.managers.IOManager;
import core.io.readers.CarInput;
import core.io.readers.structure.WrongInputException;

/**
 * Фабрика создающая экземпляры команды filter_greater_than_car
 */
public class GreaterThanCarCommandFactory extends CommandFactory {
    public GreaterThanCarCommandFactory(ClientContext client, ServerContext server) {
        super(client, server, "filter_greater_than_car car", "вывести элементы, значение поля car которых больше заданного");
    }

    HumanBeing.Car argument;

    @Override
    public boolean readArgs(IOManager io) {
        try {
            io.getIn().nextLine();
            argument = new CarInput(io).read();
        } catch (WrongInputException e) {
            return false;
        }
        return true;
    }

    @Override
    public Command newInstance() {
        var res = new ServerCommand(getName(),getClient(),getServer()) {
            HumanBeing.Car argument;


            public void addArgument(HumanBeing.Car value) {
                argument = value;
            }

            @Override
            public CommandUnit getUnit() {
                return new CommandUnit(getName(),argument);
            }

            @Override
            public String toString() {
                return  super.toString() + " " + argument;
            }
        };
        res.addArgument(argument);
        return res;
    }

}
