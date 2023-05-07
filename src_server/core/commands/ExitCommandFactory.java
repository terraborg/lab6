package core.commands;

import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;
import core.commands.structure.CallbackUnit;
import core.commands.structure.Command;
import core.commands.structure.CommandFactory;

/**
 * Фабрика создающая экземпляры команды exit
 */
public class ExitCommandFactory extends CommandFactory {
    public ExitCommandFactory(ServerContext server) {
        super(server, "exit", "завершает работу программы (без сохранения)");
    }

    @Override
    public Command newInstance(CallbackConsumer client) {
        return new Command("exit",client) {
            @Override
            public void execute() {
                getClient().putCallback(new CallbackUnit(true, "Выполнение программы завершено\n"));
                System.exit(0);
            }
        };
    }
}
