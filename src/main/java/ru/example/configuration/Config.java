package ru.example.configuration;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {
    //путь к нашему файлу конфигураций
    private static final String PATH_TO_PROPERTIES = "src/main/resources/application.properties";

    private Config() {
        throw new IllegalStateException("config class");
    }
    @SneakyThrows
    public static String getProperty(String key)  {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            Properties prop = new Properties();
            prop.load(fileInputStream);
            return prop.getProperty(key);
        } finally {
            fileInputStream.close();
        }
    }
}
