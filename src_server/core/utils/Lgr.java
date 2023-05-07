package core.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class Lgr {
    private static final Logger logger = LogManager.getRootLogger();
    static public Logger getLogger()
    {
        Configurator.setRootLevel(Level.ALL);
        return logger;
    }
}
