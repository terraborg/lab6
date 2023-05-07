package core;

import core.commands.structure.CommandUnit;
import core.database.TreeSetHolder;
import core.file.FromObjectToXML;
import core.file.FromXMLToObject;
import core.managers.CallbackManager;
import core.managers.ConnectionAcceptManager;
import core.managers.ServerManager;
import core.utils.Lgr;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.simple.SimpleLogger;

import java.io.IOException;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) {
        var logger = Lgr.getLogger();
        logger.info("Запуск программы");
        var connection = new ConnectionAcceptManager(8000);
        var manager = new ServerManager(new TreeSetHolder(new FromXMLToObject(System.getenv("lab5")),
                new FromObjectToXML(System.getenv("lab5"))));
        var read = new CommandUnit("read");
        read.setConsumer(CallbackManager.console());
        manager.executeCommand(read);
        logger.info("Чтение из файла завершено");
        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                connection.check(manager);
                if(System.in.available()!=0) {
                    var com = new CommandUnit(in.next());
                    com.setConsumer(CallbackManager.console());
                    manager.executeCommand(com);
                }
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }


}
