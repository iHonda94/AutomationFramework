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
    public static String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}