package core.io.readers;

import core.HumanBeing;
import core.database.DataBaseHolder;
import core.io.managers.IOManager;
import core.io.readers.structure.Input;
import core.io.readers.structure.Validator;
import core.io.readers.structure.WrongInputException;

/**
 * Класс для ввода данных типа HumanBeing
 */
public class HumanBeingInput extends Input<HumanBeing> {
    public HumanBeingInput(IOManager io) {
        super(io, false,false, "HumanBeing{\n");
    }

    @Override
    public HumanBeing read(Validator<HumanBeing> validator) throws WrongInputException {
        return read();
    }

    @Override
    public HumanBeing read() throws WrongInputException {
        io.printInv(invMessage);

        String name = new NameInput(io,true,false).read();
        HumanBeing.Coordinates coordinates = new CoordinatesInput(io).read();
        Boolean realHero = new BooleanInput(io,true,false,"realHero = ").read();
        Boolean hasToothPick = new BooleanInput(io,true,false,"hasToothPick = ").read();
        float impactSpeed = new FloatInput(io,true,"impactSpeed = ").read(new Validator<>() {
            @Override
            public String getWrongMessage() {
                return "Значение этого поля должно быть больше -509\n";
            }

            @Override
            public boolean test(Float aFloat) {
                return aFloat>-509;
            }
        });
        HumanBeing.WeaponType weaponType = new WeaponInput(io,true,"weaponType = ").read();
        HumanBeing.Mood mood = new MoodInput(io,true,"mood = ").read();
        HumanBeing.Car car = new CarInput(io).read();
        return new HumanBeing(name,coordinates,realHero,hasToothPick,impactSpeed,weaponType,mood,car);
    }
}
