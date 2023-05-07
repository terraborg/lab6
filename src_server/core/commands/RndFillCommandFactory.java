package core.commands;

import core.HumanBeing;
import core.commands.structure.*;
import core.database.DataBaseHolder;
import core.database.ElementsLimitReachedException;
import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;
import core.utils.GeneratorID;

/**
 * Фабрика создающая экземпляры команды rnd_fill
 */
public class RndFillCommandFactory extends CommandFactory {
    public RndFillCommandFactory(ServerContext server) {
        super(server, "rnd_fill count", "заполняет коллекцию случайными элементами");
    }
    private Long n;

    @Override
    public boolean readArgs(CommandUnit unit) {
        n = (Long) unit.getArgs()[0];
        return true;
    }

    @Override
    public Command newInstance(CallbackConsumer client) {
        var res = new ServerCommand(getName(),client, getServer()) {
            private Long n;


            public void addArgument(Long value) {
                n = value;
            }
            @Override
            public void execute() {
                try {
                    for (var i = 0; i < n; i++) {
                        getServer().getDataBaseHolder().add(RandomHumanBeingFiller.getRandomHuman(getServer().getDataBaseHolder()));
                    }
                    getClient().putCallback(new CallbackUnit(true));
                    getServer().executeCommand(new CommandUnit("save"));
                }catch (ElementsLimitReachedException e) {
                    getClient().putCallback(new CallbackUnit(false,e.getMessage()));
                }
            }
            @Override
            public String toString() {
                return  super.toString() + " " + n;
            }

            static class RandomHumanBeingFiller {
                private static int name = 1;
                public static HumanBeing getRandomHuman(DataBaseHolder dataBaseHolder)
                {
                    String name = String.valueOf(RandomHumanBeingFiller.name);
                    RandomHumanBeingFiller.name++;
                    HumanBeing.Coordinates coordinates = new HumanBeing.Coordinates((float) Math.random()*1000,Math.random()*1000);
                    Boolean realHero = Math.random() > 0.5;
                    Boolean hasToothpick = Math.random() > 0.5;
                    float impactSpeed = (float)(Math.random()*5000 - 508);
                    HumanBeing.WeaponType weaponType = HumanBeing.WeaponType.values()[(int)(Math.random()*4)];
                    HumanBeing.Mood mood = HumanBeing.Mood.values()[(int)(Math.random()*5)];
                    HumanBeing.Car car = new HumanBeing.Car(name, Math.random() > 0.5);
                    return new HumanBeing(name,coordinates, realHero, hasToothpick, impactSpeed, weaponType, mood, car, dataBaseHolder);
                }
            }
        };
        res.addArgument(n);
        return res;
    }

}
