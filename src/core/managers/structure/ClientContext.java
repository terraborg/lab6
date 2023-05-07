package core.managers.structure;

/**
 * Интерфейс задающий класс связи с клиентом, в будующем планируется подставлять вместо него класс передачи данных на клиент
 */
public interface ClientContext extends HasHistory, CanExecute, CallbackConsumer {
    void exit(boolean save);
}
