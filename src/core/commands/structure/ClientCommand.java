package core.commands.structure;

import core.managers.structure.CallbackConsumer;
import core.managers.structure.ClientContext;

abstract public class ClientCommand extends Command{
    public ClientCommand(String name, ClientContext client) {
        super(name, client);
    }

    @Override
    protected ClientContext getClient() {
        return (ClientContext) super.getClient();
    }
}
