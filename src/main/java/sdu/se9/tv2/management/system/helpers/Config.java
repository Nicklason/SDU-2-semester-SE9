package sdu.se9.tv2.management.system.helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Properties properties;

    public static void load () {
        // https://medium.com/@sonaldwivedi/how-to-read-config-properties-file-in-java-6a501dc96b25
        Properties prop = new Properties();

        FileInputStream fileStream = null;
        try {
            fileStream = new FileInputStream("config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            prop.load(fileStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        properties = prop;
    }

    public static Object get (String key) {
        return properties.get(key);
    }
}
