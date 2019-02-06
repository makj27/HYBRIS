package utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;

public class LogFactory {

    //declaration of class variable
    static Logger logger;


    //Below are method implementations

    /**
     * @description method to instantiate Log4j logger and get logger object
     * @return
     */
    public static Logger getLogger() {

        logger = Logger.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());

        String log4jConfigFile = new StringBuilder().append(System.getProperty("user.dir")).append("\\src\\main\\resources").append(File.separator).append("log4j.properties").toString();
        PropertyConfigurator.configure(log4jConfigFile);

        logger.info("LOGGER Initialized Successfully!!");
        return logger;
    }

}
