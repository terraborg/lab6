package core.commands;


import core.HumanBeing;
import core.commands.structure.*;
import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;

/**
 * Фабрика создающая экземпляры команды update
 */
public class UpdateCommandFactory extends CommandFactory {

    public UpdateCommandFactory(ServerContext server) {
        super(server, "update id {element}", "обновить значение элемента коллекции, id которого равен заданному");
    }

    private HumanBeing argument;
    private Long id;

    @Override
    public boolean readArgs(CommandUnit unit) {
        id = (Long) unit.getArgs()[0];
        argument = (HumanBeing) unit.getArgs()[1];
        return true;
    }

    @Override
    public Command newInstance(CallbackConsumer client) {
        var res = new ServerCommand(getName(),client,getServer()) {
            private HumanBeing argument;
            private Long id;
            public void addArgument(HumanBeing value) {
                argument = value;
            }
            public void addArgument(Long value){
                id = value;
            }
            @Override
            public void execute() {
                if(getServer().getDataBaseHolder().updateById(id,argument)) {
                    getClient().putCallback(new CallbackUnit(true));
                    getServer().executeCommand(new CommandUnit("save"));
                } else
                    getClient().putCallback(new CallbackUnit(false,"Такого элемента не существует"));
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
