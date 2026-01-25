package utils;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Properties properties;

    static {
        try {
            InputStream input = Config.class.getClassLoader().getResourceAsStream("application.properties");
            properties = new Properties();
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load application.properties");
        }
    }
    
    /**
     * Get a configuration value.
     * Priority: System property (-D) > application.properties > defaultValue
     * This allows command-line overrides like: mvn test -Dheadless=true
     */
    public static String get(String key, String defaultValue) {
        // First check system properties (command line -D arguments)
        String systemValue = System.getProperty(key);
        if (systemValue != null) {
            return systemValue;
        }
        // Then check application.properties file
        return properties.getProperty(key, defaultValue);
    }
}