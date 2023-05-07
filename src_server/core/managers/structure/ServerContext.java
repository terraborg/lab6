package core.managers.structure;

import core.commands.structure.Command;
import core.commands.structure.CommandUnit;
import core.commands.structure.ServerCommand;
import core.database.DataBaseHolder;

/**
 * Интерфейс задающий объект взаймодействия с сервером
 */
public interface ServerContext {
    DataBaseHolder getDataBaseHolder();
    void executeCommand(CommandUnit unit);
}
