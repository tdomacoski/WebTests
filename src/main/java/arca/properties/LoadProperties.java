package arca.properties;

import java.io.FileReader;
import java.util.Properties;

public class LoadProperties {

    public static final String loggerPath = "logger_path";
    private static final Properties properties = new Properties();

    static {
        try {
            final FileReader reader = new FileReader("configurations.properties");
            properties.load(reader);
        } catch (final Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    public static String get(final String key){
        return properties.getProperty(key);
    }

}
