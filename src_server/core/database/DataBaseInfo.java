package core.database;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Volovich Alexey
 * Класс хранящий общую информацию о базе данных
 * @see DataBaseHolder
 */
public class DataBaseInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 76L;
    private final String type;
    private final java.time.LocalDateTime creationTime;
    private final int size;

    private final int maxSize;

    public DataBaseInfo(String type, LocalDateTime creationTime, int size,int maxSize) {
        this.type = type;
        this.creationTime = creationTime;
        this.size = size;
        this.maxSize = maxSize;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Информация о коллекции:\n" +
                "Тип: " + type + "\n" +
                "Дата создания: " + creationTime.toLocalDate() + "\n" +
                "Время создания: " + creationTime.toLocalTime().withNano(0) + "\n" +
                "Количество элементов: " + size + "\n"+
                "Максимальное количество элементов: "+maxSize+"\n";
    }

    public int getMaxSize() {
        return maxSize;
    }
}
