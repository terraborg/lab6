package core.commands;

import core.commands.structure.*;
import core.HumanBeing;
import core.database.ElementsLimitReachedException;
import core.managers.CallbackManager;
import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;

/**
 * Фабрика создающая экземпляры команды add_if_min
 */
public class AddIfMinCommandFactory extends CommandFactory {
    public AddIfMinCommandFactory( ServerContext server) {
        super( server, "add_if_max {element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
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
                if(getServer().getDataBaseHolder().compareToMin(argument)==-1){
                    try {
                        getServer().getDataBaseHolder().add(argument);
                        getClient().putCallback(new CallbackUnit(true, "Добавлен элемент с id = " + argument.getId().toString()));
                        getServer().executeCommand(new CommandUnit("save"));
                    }catch (ElementsLimitReachedException e) {
                        getClient().putCallback(new CallbackUnit(false,e.getMessage()));
                    }
                }
                else
                    getClient().putCallback(new CallbackUnit(false,"Элемент больше минимального"));
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