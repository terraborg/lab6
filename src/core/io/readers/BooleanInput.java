package core.io.readers;

import core.io.managers.IOManager;
import core.io.readers.structure.Input;
import core.io.readers.structure.Validator;
import core.io.readers.structure.WrongInputException;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.StringReader;

/**
 * Класс для ввода данных типа Boolean
 */
public class BooleanInput extends Input<Boolean> {
    public BooleanInput(IOManager io, boolean repeat,boolean nullable, String invMessage) {
        super(io, repeat, nullable, invMessage);
    }

    @Override
    public Boolean read(Validator<Boolean> validator) throws WrongInputException {
        return read();
    }

    @Override
    public Boolean read() throws WrongInputException {
        do {
            io.printInv(invMessage);
            var s = io.getIn().nextLine().trim();
            if(s.length()==0 && nullable)
                return null;

            if (s.equals("true") || s.equals("false"))
                return Boolean.parseBoolean(s);
            else {
                io.print("Значение может быть только true или false\n");
                if(nullable)
                    io.print("Для установления значения в null введите пустую строку\n");
            }
        }while (repeat);
        throw new WrongInputException();
    }
}
