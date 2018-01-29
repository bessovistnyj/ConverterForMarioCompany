package ru.napadovskiu.settings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by program on 31.01.2017.
 */
public class Settings {

    /**
     * properties.
     */
    private final Properties prs = new Properties();

    /**
     * Method to load properties.
     * @param io input stream.
     */
    public void load(InputStream io) {
        try {
            this.prs.load(io);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method return value for key from properties.
     * @param key key for search
     * @return result value.
     */
    public String getValue(String key) {
        return this.prs.getProperty(key);
    }

}
