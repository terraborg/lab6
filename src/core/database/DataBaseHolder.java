package core.database;

import core.HumanBeing;
import core.commands.structure.Command;
import core.file.FileIn;
import core.file.FileOut;

import java.util.function.Predicate;

/**
 * @author Volovich Alexey
 * Интерфейс задающий поведение баз данных.
 * @see Command
 * @see ServerManager
 */
public interface DataBaseHolder{
    /**
     * @return Команда возвращает общую информацию о базе данных в виде объекта класса DataBaseInfo
     * @see DataBaseInfo
     */
    DataBaseInfo getInfo();

    /**
     * Удаляет из базы данных все элементы.
     */
    void clear();

    /**
     * Удаляет из базы данных один элемент.
     * @param id - индекс удаляемого элемента.
     */
    boolean removeById(Long id);

    boolean contains(Long id);

    /**
     * @return Возвращет все элементы базы данных в виде массива
     */
    HumanBeing[] getAllElements();

    int getSize();

    /**
     * Добавляет элемент в коллекцию
     * @param e - элемент который будет добавлен.
     */
    void add(HumanBeing e);

    /**
     * Заменяет все элементы на новые.
     * @param e - массив новых элементов
     */
    void setAllElements(HumanBeing[] e);

    /**
     * Сравнивает элемент с минимальным элементом коллекции.
     * @param e - элемент который нужно сравнить
     * @return Возвращаемое значение аналогично возвращаемым значением функции compareTo интерфейса Comparable
     */
    int compareToMin(HumanBeing e);
    /**
     * Сравнивает элемент с максимальным элементом коллекции.
     * @param e - элемент который нужно сравнить
     * @return Возвращаемое значение аналогично возвращаемым значением функции compareTo интерфейса Comparable
     */
    int compareToMax(HumanBeing e);

    /**
     * Считает элементы проходящие фильтр
     * @param filter - фильтр элементов
     * @return Возвращает количество подсчитанных элементов
     */
    int countByFilter(Predicate<HumanBeing> filter);
    /**
     * Возвращает элементы проходящие фильтр
     * @param filter - фильтр элементов
     * @return Возвращает массив элементов.
     */
    HumanBeing[] getByFilter(Predicate<HumanBeing> filter);

    /**
     * Обновляет элемент базы данных.
     * @param id - индекс обновляемого элемента
     * @param e - новое значение элемента
     * @return Возвращает false, если нужного элемента нет в базе данных, и true в противном случае.
     */
    boolean updateById(Long id, HumanBeing e);

    /**
     * @return Возвращает класс отвечающий за запись данных в файл
     * @see FileOut
     */
    FileOut getFileOut();
    /**
     * @return Возвращает класс отвечающий получение данных из файл
     * @see FileIn
     */
    FileIn getFileIn();
}