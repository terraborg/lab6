package core.commands;

import core.HumanBeing;
import core.commands.structure.*;
import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;

import java.util.Arrays;

public class GetIdCommandFactory extends CommandFactory {
    public GetIdCommandFactory(ServerContext server) {
        super(server, "get_id", "");
    }

    private int offset;
    private int length;

    @Override
    public boolean readArgs(CommandUnit unit) {
        if(unit == null || unit.getArgs() == null || unit.getArgs().length < 2)
            return false;
        offset = (int) unit.getArgs()[0];
        length = (int) unit.getArgs()[1];
        return true;
    }

    @Override
    public Command newInstance(CallbackConsumer client) {
        var res = new ServerCommand(getName(),client,getServer()) {
            private int offset = -1;
            private int length = -1;

            public void addArgument(int[] value) {
                offset = value[0];
                length = value[1];
            }

            @Override
            public void execute() {
                if(offset>=getServer().getDataBaseHolder().getSize()) {
                    getClient().putCallback(new CallbackUnit(false,"",new Long[0]));
                    return;
                }
                var res1 = Arrays.stream(getServer().getDataBaseHolder().getAllElements(),offset,Math.min(offset+length,getServer().getDataBaseHolder().getSize())).map(HumanBeing::getId).toList();
                if(res1.size()!=0)
                    getClient().putCallback(new CallbackUnit(false, "",res1.toArray(Long[]::new)));
            }
        };
        res.addArgument(new int[]{offset,length});
        return res;
    }
}
