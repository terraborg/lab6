package core.io.readers.structure;

import java.util.function.Predicate;

/**
 * Интерфейс отвечающий за валидацию данных
 * @param <T> - тип данных
 */
public interface Validator<T> extends Predicate<T> {
    String getWrongMessage();
}
