package core.io.readers;

import core.io.managers.IOManager;
import core.io.readers.structure.Input;
import core.io.readers.structure.Validator;
import core.io.readers.structure.WrongInputException;

public class FloatInput extends Input<Float> {
    public FloatInput(IOManager io, boolean repeat, String invMessage) {
        super(io, repeat,false, invMessage);
    }

    @Override
    public Float read(Validator<Float> validator) throws WrongInputException {
        do
        {
            io.printInv(invMessage);
            var s = io.getIn().nextLine().trim();
            if(s.length()==0){
                io.print("Это поле не может быть null\n");
            }
            if(s.contains(".") && s.trim().split("\\.")[1].length()>7)
            {
                io.print("Количество знаков после запятой должно быть не больше 7\n");
                continue;
            }
            try{
                var res = Float.parseFloat(s);
                if(validator.test(res))
                    return res;
                else
                    io.print(validator.getWrongMessage());
            }catch (NumberFormatException e)
            {
                io.print(validator.getWrongMessage());
            }
        }while (repeat);
        throw new WrongInputException();
    }

    @Override
    public Float read() {
        return io.getIn().nextFloat();
    }
}
