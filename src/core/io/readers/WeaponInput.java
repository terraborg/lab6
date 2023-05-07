package core.io.readers;

import core.HumanBeing;
import core.io.managers.IOManager;
import core.io.readers.structure.Input;
import core.io.readers.structure.Validator;
import core.io.readers.structure.WrongInputException;

/**
 * Класс для ввода данных типа Weapon
 */
public class WeaponInput extends Input<HumanBeing.WeaponType> {
    public WeaponInput(IOManager io, boolean repeat, String invMessage) {
        super(io, repeat,true,invMessage);
    }

    @Override
    public HumanBeing.WeaponType read(Validator<HumanBeing.WeaponType> validator)throws WrongInputException {
        return read();
    }
    private String wrm = "Аргумент может принимать только значения из списка: AXE, SHOTGUN, RIFLE, KNIFE\nПоле может быть null\n";
    @Override
    public HumanBeing.WeaponType read() throws WrongInputException {
        if(invMessage.trim().length()!=0)
        {
            io.printInv(wrm);
        }
        do
        {
            io.printInv(invMessage);
            var res = io.getIn().nextLine().trim().toUpperCase();
            if(nullable && res.length() == 0)
                return null;
            try{
                return HumanBeing.WeaponType.valueOf(res);
            }catch (IllegalArgumentException e){
                io.print(wrm);
            }
        }while(repeat);
        throw new WrongInputException();
    }
}
