package core.commands;

import core.commands.structure.Command;
import core.commands.structure.CommandFactory;
import core.commands.structure.CommandUnit;
import core.commands.structure.ServerCommand;
import core.managers.structure.ClientContext;
import core.managers.structure.ServerContext;

public class GetIdCommandFactory extends CommandFactory {
    public GetIdCommandFactory(ClientContext client, ServerContext server) {
        super(client, server, "get", "");
    }

    private int offset;
    private int length;
    public void setArgs(int offset, int length)
    {
        this.offset = offset;
        this.length = length;
    }

    @Override
    public Command newInstance() {
        var res = new ServerCommand(getName(),getClient(),getServer()) {
            private int offset;
            private int length;

            public void addArgument(int[] value) {
                offset = value[0];
                length = value[1];
            }

            @Override
            public CommandUnit getUnit() {
                return new CommandUnit("get_id",offset,length);
            }
        };
        res.addArgument(new int[]{offset,length});
        return res;
    }
}
