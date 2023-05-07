package core.commands;

import core.commands.structure.*;
import core.database.DataBaseInfo;
import core.managers.structure.CallbackConsumer;
import core.managers.structure.ClientContext;
import core.managers.structure.ServerContext;

/**
 * Фабрика создающая экземпляры команды show_id_list
 */
public class ShowIdListCommandFactory extends CommandFactory {
    public ShowIdListCommandFactory(ClientContext client, ServerContext server) {
        super(client, server, "show_id_list", "вывести в консоль список id всех элементов коллекции");
    }

    @Override
    public Command newInstance() {
        return new ShowIdListCommand(getName(), getClient(), getServer());
    }

    private static class ShowIdListCommand extends ServerCommand implements CallbackConsumer {
        public ShowIdListCommand(String name, CallbackConsumer client, ServerContext server) {
            super(name, client, server);
            factory = new GetIdCommandFactory((ClientContext) client,server);
        }

        @Override
        public CommandUnit getUnit() {
            return null;
        }

        private final GetIdCommandFactory factory;

        private DataBaseInfo info;

        @Override
        public void execute() {
            new ServerCommand("info", this, getServer()) {
                @Override
                public CommandUnit getUnit() {
                    return new CommandUnit(getName(),getClient(),1);
                }
            }.execute();
            var size = info.getSize();
            if(size == 0) {
                getClient().putCallback(new CallbackUnit(true,"Коллекция пуста :3"));
                return;
            }
            int batch = 1000;
            for(var i = 0; i < size;i+=batch)
            {
                factory.setArgs(i,batch);
                factory.newInstance().execute();
            }
        }

        @Override
        public void putCallback(CallbackUnit callbackUnit) {
            try {
                info = (DataBaseInfo) callbackUnit.getArgs()[0];
            }catch (Exception e)
            {
                e.printStackTrace();
                getClient().putCallback(new CallbackUnit(false,"Сервер недоступен"));
            }
        }
    }
}
