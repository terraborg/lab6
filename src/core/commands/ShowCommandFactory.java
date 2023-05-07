package core.commands;

import core.commands.structure.*;
import core.database.DataBaseInfo;
import core.managers.structure.CallbackConsumer;
import core.managers.structure.ClientContext;
import core.managers.structure.ServerContext;

import java.util.Arrays;

/**
 * Фабрика создающая экземпляры команды show
 */
public class ShowCommandFactory extends CommandFactory {
    public ShowCommandFactory(ClientContext client, ServerContext server) {
        super(client, server, "show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
    }

    @Override
    public Command newInstance() {
        //return new NoArgsCommand(getName(),getClient(),getServer());
        return new ShowCommand(getName(), getClient(), getServer());
    }

    private static class ShowCommand extends ServerCommand implements CallbackConsumer {
        public ShowCommand(String name, CallbackConsumer client, ServerContext server) {
            super(name, client, server);
            factory = new GetCommandFactory((ClientContext) client,server);
        }

        @Override
        public CommandUnit getUnit() {
            return null;
        }

        private final GetCommandFactory factory;

        private DataBaseInfo info;

        private boolean stop = false;

        @Override
        public void execute() {
            stop = false;
            var infComm = new ServerCommand("info", this, getServer()) {
                @Override
                public CommandUnit getUnit() {
                    return new CommandUnit(getName(),getClient(),1);
                }
            };
            infComm.execute();
            if(info == null)
                return;
            if(info.getSize() == 0) {
                getClient().putCallback(new CallbackUnit(true,"Коллекция пуста :3"));
                return;
            }
            int batch = 250;
            for(var i = 0; i < info.getSize();i+=batch)
            {
                infComm.execute();
                if(info == null)
                    return;
                if(info.getSize() == 0) {
                    getClient().putCallback(new CallbackUnit(true,"Коллекция была изменена другим пользователем :_("));
                    return;
                }
                factory.setArgs(i,batch);
                factory.newInstance().execute();
            }
        }

        @Override
        public void putCallback(CallbackUnit callbackUnit) {
            try {
                info = (DataBaseInfo) callbackUnit.getArgs()[0];
            }
            catch (Exception e)
            {
                getClient().putCallback(new CallbackUnit(false,"Сервер недоступен"));
            }
        }
    }
}
