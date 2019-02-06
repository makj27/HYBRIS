package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    //Declaration of class variables
    private Properties properties;
    private final String propertyFilePath= "src\\main\\resources\\config.properties";

    /**
     * Constructor: Instantiating and loading properties from config.properties file.
     */
    public PropertyReader(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("config.properties not found at " + propertyFilePath);
        }
    }

    /**
     * @description method to get properties object
     * @return
     */
    public Properties getConfigProperties(){
        return properties;
    }
}
