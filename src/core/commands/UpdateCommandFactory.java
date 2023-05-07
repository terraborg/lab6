package core.commands;


import core.commands.structure.*;
import core.managers.structure.ClientContext;
import core.HumanBeing;
import core.managers.structure.ServerContext;
import core.io.managers.IOManager;
import core.io.readers.HumanBeingInput;
import core.io.readers.IdInput;
import core.io.readers.structure.WrongInputException;

/**
 * Фабрика создающая экземпляры команды update
 */
public class UpdateCommandFactory extends CommandFactory {

    public UpdateCommandFactory(ClientContext client, ServerContext server) {
        super(client, server, "update id {element}", "обновить значение элемента коллекции, id которого равен заданному");
    }

    private HumanBeing argument;
    private Long id;

    @Override
    public boolean readArgs(IOManager io) {
        try {
            id = new IdInput(io).read();
            argument = new HumanBeingInput(io).read();
        } catch (WrongInputException e) {
            return false;
        }
        return true;
    }

    @Override
    public Command newInstance() {
        var res = new ServerCommand(getName(),getClient(),getServer()) {
            @Override
            public CommandUnit getUnit() {
                return new CommandUnit(getName(),id,argument);
            }
            private HumanBeing argument;
            private Long id;
            public void addArgument(HumanBeing value) {
                argument = value;
            }
            public void addArgument(Long value){
                id = value;
            }

            @Override
            public String toString() {
                return  super.toString()+ " "+ id + " " + argument;
            }
        };
        res.addArgument(id);
        res.addArgument(argument);
        return res;
    }

}
