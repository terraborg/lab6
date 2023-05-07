package core.commands;

import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;
import core.commands.structure.CallbackUnit;
import core.commands.structure.Command;
import core.commands.structure.CommandFactory;
import core.commands.structure.ServerCommand;

/**
 * Фабрика создающая экземпляры команды show_id_list
 */
public class ShowIdListCommandFactory extends CommandFactory {
    public ShowIdListCommandFactory(ServerContext server) {
        super(server, "show_id_list", "вывести в консоль список id всех элементов коллекции");
    }

    @Override
    public Command newInstance(CallbackConsumer client) {
        return new ServerCommand(getName(),client,getServer()) {
            @Override
            public void execute() {
                var res = getServer().getDataBaseHolder().getAllElements();
                var b = new StringBuilder();
                for(var e : res)
                    b.append(e.getId()).append(", ");
                b.deleteCharAt(b.length()-1);
                b.deleteCharAt(b.length()-1);
                getClient().putCallback(new CallbackUnit(true,"Список id элементов:\n"+b));
            }
        };
    }
}
