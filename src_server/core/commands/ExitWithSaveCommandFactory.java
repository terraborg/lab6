package core.commands;

import core.commands.structure.CommandUnit;
import core.managers.CallbackManager;
import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;
import core.commands.structure.Command;
import core.commands.structure.CommandFactory;

/**
 * Фабрика создающая экземпляры команды exit_with_save
 */
public class ExitWithSaveCommandFactory extends CommandFactory {
    public ExitWithSaveCommandFactory(ServerContext server) {
        super(server, "exit_with_save", "завершает работу программы с сохранением данных");
    }

    @Override
    public Command newInstance(CallbackConsumer client) {
        return new Command("exit_with_save",client) {
            @Override
            public void execute() {
                getServer().executeCommand(new CommandUnit("save"));
                System.exit(0);
            }
        };
    }
}
