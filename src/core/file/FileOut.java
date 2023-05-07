package core.file;

import core.HumanBeing;
import core.database.DataBaseHolder;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * @author Volovich Alexey
 * Интерфейс задающий взаимодействие с классами записи данных в файл.
 */
public interface FileOut{
    /**
     * Записывает данные из базы данных collection в файл
     * @param collection
     * @throws IOException
     * @throws XMLStreamException
     */
    void writeCollection(DataBaseHolder collection) throws IOException, XMLStreamException;
}
