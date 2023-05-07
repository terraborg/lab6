package core.io.readers;

import core.io.managers.IOManager;
import core.io.readers.structure.Input;
import core.io.readers.structure.Validator;
import core.io.readers.structure.WrongInputException;

/**
 * Класс для ввода имен
 */
public class NameInput extends Input<String> {
    public NameInput(IOManager io, boolean repeat, boolean nullable) {
        super(io, repeat,nullable, "name = ");
    }

    public NameInput(IOManager io, boolean repeat,boolean nullable, String invMessage) {
        super(io,repeat,nullable,invMessage);
    }

    @Override
    public String read(Validator<String> validator) throws WrongInputException {
        do
        {
            io.printInv(invMessage);
            var s = io.getIn().nextLine().trim();
            if(s.length()==0 && nullable)
                return null;
            if(!validator.test(s))
            {
                io.print(validator.getWrongMessage());
            }
            else
                return s;
        }while(repeat);
        throw new WrongInputException();
    }

    @Override
    public String read() throws WrongInputException {
        return read(new NameValidator());
    }

    private static class NameValidator implements Validator<String> {
        @Override
        public String getWrongMessage() {
            return "Имя должно быть непустой строкой длиной не более 250 символов\n";
        }

        @Override
        public boolean test(String s) {
            return s.length()>0 && s.length()<=250;
        }
    }

}
