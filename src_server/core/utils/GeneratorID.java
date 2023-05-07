package core.utils;

import core.database.DataBaseHolder;

/**
 * @author Volovich Alexey
 * Класс отвечающий за генерацию id
 * @see core.HumanBeing
 */
public class GeneratorID {
    private static long id = 1;
    public static Long next(DataBaseHolder dataBaseHolder){
        if(id < Long.MAX_VALUE && id>0)
            return id++;
        long n_id =1;
        while(dataBaseHolder.contains(n_id))
            n_id++;
        id = n_id;
        return id++;
    }
    public static void setId(Long id)
    {
        GeneratorID.id = id;
    }
}
