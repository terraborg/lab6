package core.io.readers;

import core.HumanBeing;
import core.io.managers.IOManager;
import core.io.readers.structure.Input;
import core.io.readers.structure.Validator;
import core.io.readers.structure.WrongInputException;

/**
 * Класс для ввода данных типа Mood
 */
public class MoodInput extends Input<HumanBeing.Mood> {
    public MoodInput(IOManager io, boolean repeat, String invMessage) {
        super(io, repeat,false, invMessage);
    }

    @Override
    public HumanBeing.Mood read(Validator<HumanBeing.Mood> validator) throws WrongInputException {
        return read();
    }
    String wrm = "Это поле может принимать лишь значения из списка: \n" +
            "    SADNESS\n" +
            "    SORROW\n" +
            "    LONGING\n" +
            "    GLOOM\n" +
            "    APATHY\n";
    @Override
    public HumanBeing.Mood read() throws WrongInputException {
        io.printInv(wrm);
        do
        {
            io.printInv(invMessage);
            var res = io.getIn().nextLine().trim().toUpperCase();
            if(res.length()==0){
                io.print("Это поле не может быть null\n");
                continue;
            }
            try{
                return HumanBeing.Mood.valueOf(res);
            }catch (IllegalArgumentException e){
                io.print(wrm);
            }
        }while(repeat);
        throw new WrongInputException();
    }
}
