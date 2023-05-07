package core.commands;

import core.commands.structure.*;
import core.HumanBeing;
import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;

/**
 * Фабрика создающая экземпляры команды count_by_weapon_type
 */
public class CountByWeaponTypeCommandFactory extends CommandFactory {
    public CountByWeaponTypeCommandFactory(ServerContext server) {
        super(server, "count_by_weapon_type weaponType","вывести количество элементов, значение поля weaponType которых равно заданному");
    }
    HumanBeing.WeaponType argument;

    @Override
    public boolean readArgs(CommandUnit unit) {
        argument = (HumanBeing.WeaponType) unit.getArgs()[0];
        return true;
    }

    @Override
    public Command newInstance(CallbackConsumer client) {
        var res = new ServerCommand(getName(),client,getServer()) {
            private HumanBeing.WeaponType argument;

            public void addArgument(HumanBeing.WeaponType value) {
                argument = value;
            }

            @Override
            public void execute() {
                int res = getServer().getDataBaseHolder().countByFilter(e -> e.getWeaponType() == argument);
                getClient().putCallback(new CallbackUnit(true,"Количество посчитанных элементов равно "+res));
            }
            @Override
            public String toString() {
                return  super.toString() + " " + argument;
            }
        };
        res.addArgument(argument);
        return res;
    }

}
