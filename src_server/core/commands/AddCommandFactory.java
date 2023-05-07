package core.commands;

import core.commands.structure.*;
import core.database.ElementsLimitReachedException;
import core.managers.CallbackManager;
import core.managers.structure.CallbackConsumer;
import core.HumanBeing;
import core.managers.structure.ServerContext;

/**
 * Фабрика создающая экземпляры команды add
 */
public class AddCommandFactory extends CommandFactory {
    public AddCommandFactory(ServerContext server) {
        super(server, "add {element}", "добавить новый элемент в коллекцию");
    }
    private HumanBeing argument;

    @Override
    public boolean readArgs(CommandUnit unit) {
        argument = (HumanBeing) unit.getArgs()[0];
        return argument!=null;
    }

    @Override
    public Command newInstance(CallbackConsumer client) {
        var res = new ServerCommand(getName(),client,getServer()) {
            private HumanBeing argument;
            public void addArgument(HumanBeing value) {
                argument = value;
            }

            @Override
            public void execute() {
                HumanBeing new_e = argument;
                try {
                    getServer().getDataBaseHolder().add(new_e);
                    getClient().putCallback(new CallbackUnit(true,"Добавлен элемент с id = "+new_e.getId().toString()));
                    getServer().executeCommand(new CommandUnit("save"));
                } catch (ElementsLimitReachedException e) {
                    getClient().putCallback(new CallbackUnit(false,e.getMessage()));
                }
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
