package core.managers;

import core.commands.*;
import core.commands.structure.*;
import core.io.managers.IOManager;
import core.managers.structure.ClientContext;
import core.managers.structure.ServerContext;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс связывающий воедино все модули клиентской части приложения
 */
public class ClientManager implements ClientContext {

    private final Map<String, CommandFactory> factoryPool;
    private ServerContext serverManager;
    private final HistoryManager historyManager;
    private final ScriptExecuter scriptExecuter;
    private void fillCommandFactoryPool()
    {
        factoryPool.put("help",new HelpCommandFactory(this,serverManager));
        factoryPool.put("add",new AddCommandFactory(this,serverManager));
        factoryPool.put("info",new InfoCommandFactory(this,serverManager));
        factoryPool.put("show",new ShowCommandFactory(this,serverManager));
        factoryPool.put("add_if_max",new AddIfMaxCommandFactory(this,serverManager));
        factoryPool.put("add_if_min",new AddIfMinCommandFactory(this,serverManager));
        factoryPool.put("print_ascending",new PrintAscCommandFactory(this,serverManager));
        factoryPool.put("remove_by_id",new RemoveCommandFactory(this,serverManager));
        factoryPool.put("history",new HistoryCommandFactory(this,serverManager));
        factoryPool.put("history_with_args",new HistoryWithArgsCommandFactory(this,serverManager));
        factoryPool.put("update",new UpdateCommandFactory(this,serverManager));
        factoryPool.put("count_by_weapon_type",new CountByWeaponTypeCommandFactory(this,serverManager));
        factoryPool.put("filter_greater_than_car",new GreaterThanCarCommandFactory(this,serverManager));
        factoryPool.put("show_id_list",new ShowIdListCommandFactory(this,serverManager));
        factoryPool.put("clear",new ClearCommandFactory(this,serverManager));
        factoryPool.put("exit",new ExitCommandFactory(this,serverManager));
        factoryPool.put("rnd_fill", new RndFillCommandFactory(this,serverManager));
        factoryPool.put("execute_script", new ExecuteScriptCommandFactory(this,serverManager));
    }
    public ClientManager(int port) {
        scriptExecuter = new ScriptExecuter(this);
        factoryPool = new HashMap<>();
        historyManager = new HistoryManager();
        try {
            serverManager = new ConnectionManager(InetAddress.getLocalHost(),port,this);
        } catch (IOException e) {
            System.out.println("Такого адреса не существует");
        }
        fillCommandFactoryPool();
    }

    public void execute(String command, IOManager io)
    {
        if(factoryPool.containsKey(command)) {
            var factory = factoryPool.get(command);
            if(factory.readArgs(io)) {
                Command com = factory.newInstance();
                com.execute();
                historyManager.addCommand(com);
            }
        } else
            System.out.println("Такой команды не существует");
    }

    @Override
    public boolean executeScript(String path) {
        return scriptExecuter.execute(path);
    }

    @Override
    public void putCallback(CallbackUnit callbackUnit) {
        if(callbackUnit.isSuccess()) {
            System.out.println("Команда выполнена успешно!");
        }
        if(callbackUnit.hasMessage())
            System.out.println(callbackUnit.getMessage().trim());
    }


    @Override
    public void exit(boolean save) {
        if(save)
            execute("save",null);
        System.exit(0);
    }

    @Override
    public String getCommandList() {
        var b = new StringBuilder();
        var arr = factoryPool.keySet().toArray(new String[0]);
        Arrays.sort(arr);
        for(var e : arr) {
            b.append(factoryPool.get(e).getName()).append(": ").append(factoryPool.get(e).getDescription()).append('\n');
        }
        return b.toString();
    }

    @Override
    public String getHistory() {
        var b = new StringBuilder();
        for(var e : historyManager.getHistory())
        {
            b.append(e.getName().trim().split(" ")[0]).append("\n");
        }
        return b.toString();
    }

    @Override
    public String getHistoryWithArgs() {
        var b = new StringBuilder();
        for(var e : historyManager.getHistory())
        {
            b.append(e).append("\n");
        }
        return b.toString();
    }
}
