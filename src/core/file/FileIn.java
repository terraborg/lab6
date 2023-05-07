package core.file;

import core.HumanBeing;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;

/**
 * @author Volovich Alexey
 * Интерфейс задающий взаимодействие с классами считывания данных из файла
 */
public interface FileIn {
    /**
     * Команда чтения данных из файла. Возвращает массив полученных элементов.
     * @return HumanBeing[]
     * @throws FileNotFoundException
     * @throws XMLStreamException
     */
    HumanBeing[] readFile() throws FileNotFoundException, XMLStreamException, AccessDeniedException;
}
