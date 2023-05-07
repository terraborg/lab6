package core.commands;

import core.commands.structure.*;
import core.HumanBeing;
import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;

/**
 * Фабрика создающая экземпляры команды filter_greater_than_car
 */
public class GreaterThanCarCommandFactory extends CommandFactory {
    public GreaterThanCarCommandFactory(ServerContext server) {
        super(server, "filter_greater_than_car car", "вывести элементы, значение поля car которых больше заданного");
    }

    HumanBeing.Car argument;

    @Override
    public boolean readArgs(CommandUnit unit) {
        argument = (HumanBeing.Car) unit.getArgs()[0];
        return true;
    }

    @Override
    public Command newInstance(CallbackConsumer client) {
        var res = new ServerCommand(getName(),client,getServer()) {
            HumanBeing.Car argument;


            public void addArgument(HumanBeing.Car value) {
                argument = value;
            }

            @Override
            public void execute() {
                var res = getServer().getDataBaseHolder().getByFilter(e -> new HumanBeing.Car("",null).compare(e.getCar(),argument) > 0);
                if(res.length==0) {
                    getClient().putCallback(new CallbackUnit(false,"Таких элементов не найдено"));
                    return;
                }
                var b = new StringBuilder();
                for(var e : res)
                    b.append(e).append("\n");
                getClient().putCallback(new CallbackUnit(true,"Резудьтат поиска:\n"+b));
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
