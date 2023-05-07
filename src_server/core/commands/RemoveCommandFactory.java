package core.commands;

import core.commands.structure.*;
import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;

/**
 * Фабрика создающая экземпляры команды remove_by_id
 */
public class RemoveCommandFactory extends CommandFactory {
    public RemoveCommandFactory(ServerContext server) {
        super(server,"remove_by_id id","удалить элемент из коллекции по его id");
    }
    private Long id;
    @Override
    public boolean readArgs(CommandUnit unit) {
        id = (Long) unit.getArgs()[0];
        return true;
    }

    @Override
    public Command newInstance(CallbackConsumer client) {
        var res = new ServerCommand(getName(),client,getServer()) {
            private Long id;

            public void addArgument(Long value) {
                id = value;
            }

            @Override
            public void execute() {
                if(!getServer().getDataBaseHolder().removeById(id))
                    getClient().putCallback(new CallbackUnit(false,"Такого элемента не существует"));
                else {
                    getClient().putCallback(new CallbackUnit(true));
                    getServer().executeCommand(new CommandUnit("save"));
                }
            }
            @Override
            public String toString() {
                return super.toString() + " " + id;
            }
        };
        res.addArgument(id);
        return res;
    }

}
