package core.io.readers;

import core.HumanBeing;
import core.io.managers.IOManager;
import core.io.readers.structure.Input;
import core.io.readers.structure.Validator;
import core.io.readers.structure.WrongInputException;

/**
 * Класс для ввода данных типа Coordinates
 */
public class CoordinatesInput extends Input<HumanBeing.Coordinates> {
    public CoordinatesInput(IOManager io) {
        super(io, false,false, "Coordinates{\n");
    }

    @Override
    public HumanBeing.Coordinates read(Validator<HumanBeing.Coordinates> validator) throws WrongInputException {
        return read();
    }

    @Override
    public HumanBeing.Coordinates read() throws WrongInputException {
        io.printInv(invMessage);
        float x = new FloatInput(io,true,"x = ").read(new Validator<>() {
            @Override
            public String getWrongMessage() {
                return "Это поле должно быть вещественным числом больше -927\n";
            }

            @Override
            public boolean test(Float a) {
                return a > -927;
            }
        });
        double y = new DoubleInput(io,true,"y = ").read(new Validator<Double>() {
            @Override
            public String getWrongMessage() {
                return "Это поле должно быть вещественным числом не превосходящим 329\n";
            }

            @Override
            public boolean test(Double aDouble) {
                return aDouble<=329;
            }
        });
        io.printInv("}\n");
        return new HumanBeing.Coordinates(x,y);
    }
}
