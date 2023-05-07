package core.io.readers;

import core.io.managers.IOManager;
import core.io.readers.structure.Input;
import core.io.readers.structure.Validator;
import core.io.readers.structure.WrongInputException;

public class PortInput extends Input<Integer>{
    /**
     * Конструктор
     *
     * @param io         - менеджер ввода-выводf
     * @param invMessage - сообщение приглашающее к вводу
     */
    public PortInput(IOManager io, String invMessage) {
        super(io, true, false, invMessage);
    }

    @Override
    public Integer read(Validator<Integer> validator) throws WrongInputException {
        return read();
    }

    @Override
    public Integer read() throws WrongInputException {
        do{
            io.printInv(invMessage);
            var s = io.getIn().nextLine().trim();
            if(s.length()==0)
                io.print("Это поле не может быть null\n");
            try{
                var res = Integer.parseInt(s);
                if(res>=8000 && res<=65535)
                    return res;
                else
                    io.print("Порт должен быть целым числом не менее 8000 и не более 65535\n");
            } catch (NumberFormatException e)
            {
                io.print("Порт должен быть целым числом не менее 8000 и не более 65535\n");
            }
        }while (repeat);
        throw new WrongInputException();
    }
}
