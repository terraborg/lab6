package core.commands;

import core.commands.structure.*;
import core.managers.structure.ClientContext;
import core.managers.structure.ServerContext;
import core.io.managers.IOManager;
import core.io.readers.IdInput;
import core.io.readers.structure.WrongInputException;

/**
 * Фабрика создающая экземпляры команды rnd_fill
 */
public class RndFillCommandFactory extends CommandFactory {
    public RndFillCommandFactory(ClientContext client, ServerContext server) {
        super(client, server, "rnd_fill count", "заполняет коллекцию случайными элементами");
    }
    private Long n;

    @Override
    public boolean readArgs(IOManager io) {
        try {
            n = new IdInput(io).read();
        } catch (WrongInputException e) {
            return false;
        }
        return true;
    }

    @Override
    public Command newInstance() {
        var res = new ServerCommand(getName(), getClient(), getServer()) {
            private Long n;


            public void addArgument(Long value) {
                n = value;
            }

            @Override
            public CommandUnit getUnit() {
                return new CommandUnit(getName(),n);
            }

            @Override
            public String toString() {
                return  super.toString() + " " + n;
            }
        };
        res.addArgument(n);
        return res;
    }

}
