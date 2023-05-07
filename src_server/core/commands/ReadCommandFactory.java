package core.commands;

import core.database.ElementsLimitReachedException;
import core.managers.structure.CallbackConsumer;
import core.managers.structure.ServerContext;
import core.commands.structure.CallbackUnit;
import core.commands.structure.Command;
import core.commands.structure.CommandFactory;
import core.commands.structure.ServerCommand;
import core.database.DataBaseHolder;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;

/**
 * Фабрика создающая экземпляры команды read
 */
public class ReadCommandFactory extends CommandFactory {

    public ReadCommandFactory(ServerContext server) {
        super(server, "read", "считать коллекцию из XML файла");
    }

    @Override
    public Command newInstance(CallbackConsumer client) {
        return new ServerCommand(getName(),client,getServer()) {
            @Override
            public void execute() {
                DataBaseHolder dataBase = getServer().getDataBaseHolder();
                try {
                    dataBase.setAllElements(dataBase.getFileIn().readFile());
                    if (dataBase.getSize()==0)
                    {
                        getClient().putCallback(new CallbackUnit(true, "Файл пуст :3"));
                        return;
                    }
                    var b = new StringBuilder();
                    int i = 0;
                    for(var x : dataBase.getAllElements())
                    {
                        b.append(x.getId().toString()).append(", ");
                        i++;
                    }
                    b.deleteCharAt(b.length()-1);
                    b.deleteCharAt(b.length()-1);
                    getClient().putCallback(new CallbackUnit(true,"В коллекцию считаны элементы с id:\n"+b));
                } catch (ElementsLimitReachedException e) {
                    getClient().putCallback(new CallbackUnit(false,e.getMessage()));
                } catch (FileNotFoundException e) {
                    //e.printStackTrace();
                    getClient().putCallback(new CallbackUnit(false,"Файл не найден"));
                } catch (XMLStreamException e) {
                    //e.printStackTrace();
                    getClient().putCallback(new CallbackUnit(false,"Файл поврежден или не является XML"));
                } catch (AccessDeniedException e){
                    getClient().putCallback(new CallbackUnit(false, "Файл недоступен"));
                } catch (NullPointerException e){
                    getClient().putCallback(new CallbackUnit(false,"Переменная среды не определена"));
                }
            }
        };
    }
}
