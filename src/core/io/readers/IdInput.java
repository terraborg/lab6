package core.io.readers;

import core.io.managers.IOManager;
import core.io.readers.structure.Input;
import core.io.readers.structure.Validator;
import core.io.readers.structure.WrongInputException;

/**
 * Класс для ввода id
 */
public class IdInput extends Input<Long> {
    public IdInput(IOManager io) {
        super(io,false,false,"");
    }

    @Override
    public Long read(Validator<Long> validator) throws WrongInputException {
        do
        {
            var s = io.getIn().nextLine().trim();
            if(s.length()==0) {
                io.print(validator.getWrongMessage());
                continue;
            }
            try{
                var res = Long.parseLong(s);
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
    public Long read()throws WrongInputException {
        return read(new IdValidator());
    }

    private static class IdValidator implements Validator<Long>{

        @Override
        public String getWrongMessage() {
            return "Id должно быть целым числом больше 0\n";
        }

        @Override
        public boolean test(Long aLong) {
            return aLong > 0;
        }
    }
}
