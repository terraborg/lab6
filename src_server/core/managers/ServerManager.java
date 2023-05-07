package core.managers;

import core.commands.*;
import core.commands.structure.Command;
import core.commands.structure.CommandFactory;
import core.commands.structure.CommandUnit;
import core.commands.structure.ServerCommand;
import core.database.DataBaseHolder;
import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Volovich Alexey
 * Класс управляющий командами, связывает команды с базой данных и историей исполняемых команд
 * @see DataBaseHolder
 */
public class ServerManager implements ServerContext {
    private final DataBaseHolder dataBaseHolder;

    public ServerManager(DataBaseHolder dataBaseHolder) {
        this.dataBaseHolder = dataBaseHolder;
        factoryPool = new HashMap<>();
        fillCommandFactoryPool();
    }


    private final Map<String, CommandFactory> factoryPool;

    private void fillCommandFactoryPool()
    {
        factoryPool.put("add",new AddCommandFactory(this));
        factoryPool.put("info",new InfoCommandFactory(this));
        factoryPool.put("show",new ShowCommandFactory(this));
        factoryPool.put("save",new SaveCommandFactory(this));
        factoryPool.put("read",new ReadCommandFactory(this));
        factoryPool.put("add_if_max",new AddIfMaxCommandFactory(this));
        factoryPool.put("add_if_min",new AddIfMinCommandFactory(this));
        factoryPool.put("print_ascending",new PrintAscCommandFactory(this));
        factoryPool.put("remove_by_id",new RemoveCommandFactory(this));
        factoryPool.put("update",new UpdateCommandFactory(this));
        factoryPool.put("count_by_weapon_type",new CountByWeaponTypeCommandFactory(this));
        factoryPool.put("filter_greater_than_car",new GreaterThanCarCommandFactory(this));
        factoryPool.put("show_id_list",new ShowIdListCommandFactory(this));
        factoryPool.put("clear",new ClearCommandFactory(this));
        factoryPool.put("exit",new ExitWithSaveCommandFactory(this));
        factoryPool.put("rnd_fill", new RndFillCommandFactory(this));
        factoryPool.put("get",new GetCommandFactory(this));
        factoryPool.put("get_id",new GetIdCommandFactory(this));
    }

    /**
     * Выполняет команду command
     */
    @Override
    public void executeCommand(CommandUnit unit)
    {
        if(factoryPool.containsKey(unit.getName())) {
            var f = factoryPool.get(unit.getName());
            try {
                if (f.readArgs(unit)) {
                    f.newInstance(unit.getConsumer()).execute();
                } else {
                    System.out.println("Такой комманды не существует");
                }
            }catch (Exception e)
            {
                System.out.println("Такой комманды не существует");
            }
        }
        else
            System.out.println("Такой комманды не существует");
    }

    @Override
    public DataBaseHolder getDataBaseHolder() {
        return dataBaseHolder;
    }
}