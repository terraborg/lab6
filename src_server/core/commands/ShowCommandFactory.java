package core.commands;

import core.HumanBeing;
import core.commands.structure.*;
import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Фабрика создающая экземпляры команды show
 */
public class ShowCommandFactory extends CommandFactory {
    public ShowCommandFactory(ServerContext server) {
        super(server, "show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
    }

    @Override
    public Command newInstance(CallbackConsumer client) {
        return new ShowCommand(getName(),client,getServer());
    }

    private static class ShowCommand extends ServerCommand {
        public ShowCommand(String name, CallbackConsumer client, ServerContext server) {
            super(name, client, server);
        }

        @Override
        public void execute() {
            if(getServer().getDataBaseHolder().getSize()<5000)
                getClient().putCallback(new CallbackUnit(true, "Элементы коллекции:\n",getServer().getDataBaseHolder().getAllElements()));
            else
            {
                int batch = 1000;
                var offset = 0;
                for(int i = 0; i < getServer().getDataBaseHolder().getSize(); i+=batch) {
                    offset = i;
                    if(offset>=getServer().getDataBaseHolder().getSize()) {
                        getClient().putCallback(new CallbackUnit(false,true,"",new HumanBeing[0]));
                        return;
                    }
                    var res1 = Arrays.stream(getServer().getDataBaseHolder().getAllElements(),offset,Math.min(offset+batch,getServer().getDataBaseHolder().getSize())).toList();
                    if(res1.size()!=0) {
                        var isLast = i+batch>=getServer().getDataBaseHolder().getSize();
                        getClient().putCallback(new CallbackUnit(false,isLast, "",res1.toArray(HumanBeing[]::new)));
                    }
                }
            }
        }
    }
}
