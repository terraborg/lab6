package core.io.readers;

import core.HumanBeing;
import core.io.managers.IOManager;
import core.io.readers.structure.Input;
import core.io.readers.structure.Validator;
import core.io.readers.structure.WrongInputException;

/**
 * Класс для ввода данных типа Car
 */
public class CarInput extends Input<HumanBeing.Car> {
    public CarInput(IOManager io) {
        super(io, false, true, "Car{\n");
    }

    @Override
    public HumanBeing.Car read(Validator<HumanBeing.Car> validator) throws WrongInputException {
        return read();
    }

    @Override
    public HumanBeing.Car read() throws WrongInputException {
        io.printInv(invMessage);
        String name = new NameInput(io,true,true).read();
        Boolean cool = new BooleanInput(io,true, true, "cool = ").read();
        io.printInv("}\n");
        if(cool == null && name == null)
            return null;
        return new HumanBeing.Car(name,cool);
    }
}
