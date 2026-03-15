package sk.adamkatrenic.logic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private Properties properties = new Properties();

    public ConfigLoader() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Ups, config.properties sa nenašiel. Používam predvolené hodnoty.");
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public double getDoubleProperty(String key, double defaultValue) {
        String value = properties.getProperty(key);
        return (value != null) ? Double.parseDouble(value) : defaultValue;
    }
}