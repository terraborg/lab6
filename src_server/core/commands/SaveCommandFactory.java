package core.commands;

import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;
import core.commands.structure.CallbackUnit;
import core.commands.structure.Command;
import core.commands.structure.CommandFactory;
import core.commands.structure.ServerCommand;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

/**
 * Фабрика создающая экземпляры команды save
 */
public class SaveCommandFactory extends CommandFactory {
    public SaveCommandFactory(ServerContext server) {
        super(server, "save", "сохранить коллекцию в XML файл");
    }

    @Override
    public Command newInstance(CallbackConsumer client) {
        return new ServerCommand(getName(),client,getServer()) {
            @Override
            public void execute() {
                try {
                    var dataBase = getServer().getDataBaseHolder();
                    dataBase.getFileOut().writeCollection(dataBase);
                    getClient().putCallback(new CallbackUnit(true));
                } catch (NullPointerException e) {
                    getClient().putCallback(new CallbackUnit(false,"Переменная окружения не определена"));
                } catch (XMLStreamException e) {
                    getClient().putCallback(new CallbackUnit(false,"Файл поврежден"));
                } catch (AccessDeniedException e){
                    getClient().putCallback(new CallbackUnit(false,"Файл недоступен"));
                } catch (IOException e) {
                    getClient().putCallback(new CallbackUnit(false,"Файл не найден"));
                }
            }
        };
    }
}
