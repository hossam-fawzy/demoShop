package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for reading configuration properties from config.properties file.
 * Provides type-safe methods to retrieve configuration values.
 * Thread-safe singleton implementation.
 *
 * @author QA Team
 * @version 1.0
 */
public class ConfigReader {
    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static Properties props = new Properties();
    private static final String CONFIG_FILE = "config.properties";

    static {
        loadProperties();
    }

    /**
     * Private constructor to prevent instantiation
     */
    private ConfigReader() {
        throw new IllegalStateException("Utility class - cannot be instantiated");
    }

    /**
     * Loads properties from config.properties file
     */
    private static void loadProperties() {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                logger.fatal("Configuration file '{}' not found in classpath", CONFIG_FILE);
                throw new RuntimeException("config.properties not found in src/main/resources");
            }
            props.load(input);
            logger.info("Configuration file '{}' loaded successfully with {} properties",
                    CONFIG_FILE, props.size());
        } catch (IOException e) {
            logger.fatal("Failed to load configuration file: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.fatal("Unexpected error while loading configuration: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error loading config.properties: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves a string property value by key
     *
     * @param key the property key
     * @return the property value, or null if not found
     */
    public static String get(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            logger.warn("Property key '{}' not found in configuration", key);
        }
        return value;
    }

    /**
     * Retrieves a string property value by key with a default value
     *
     * @param key the property key
     * @param defaultValue the default value if key not found
     * @return the property value, or defaultValue if not found
     */
    public static String get(String key, String defaultValue) {
        String value = props.getProperty(key, defaultValue);
        if (!props.containsKey(key)) {
            logger.warn("Property key '{}' not found, using default value: '{}'", key, defaultValue);
        }
        return value;
    }

    /**
     * Retrieves an integer property value by key
     *
     * @param key the property key
     * @return the integer value
     * @throws NumberFormatException if the property value cannot be parsed as an integer
     * @throws NullPointerException if the property key is not found
     */
    public static int getInt(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            logger.error("Property key '{}' not found in configuration", key);
            throw new NullPointerException("Property '" + key + "' not found in configuration");
        }
        try {
            int intValue = Integer.parseInt(value.trim());
            return intValue;
        } catch (NumberFormatException e) {
            logger.error("Failed to parse property '{}' with value '{}' as integer", key, value, e);
            throw new NumberFormatException("Property '" + key + "' value '" + value + "' is not a valid integer");
        }
    }

    /**
     * Retrieves an integer property value by key with a default value
     *
     * @param key the property key
     * @param defaultValue the default value if key not found or parsing fails
     * @return the integer value, or defaultValue if not found or invalid
     */
    public static int getInt(String key, int defaultValue) {
        String value = props.getProperty(key);
        if (value == null) {
            logger.warn("Property key '{}' not found, using default value: {}", key, defaultValue);
            return defaultValue;
        }
        try {
            int intValue = Integer.parseInt(value.trim());
            return intValue;
        } catch (NumberFormatException e) {
            logger.warn("Failed to parse property '{}' with value '{}' as integer, using default: {}",
                    key, value, defaultValue, e);
            return defaultValue;
        }
    }

    /**
     * Retrieves a boolean property value by key
     *
     * @param key the property key
     * @return true if the property value is "true" (case-insensitive), false otherwise
     */
    public static boolean getBoolean(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            logger.warn("Property key '{}' not found, returning false", key);
            return false;
        }
        boolean boolValue = Boolean.parseBoolean(value.trim());
        logger.debug("Retrieved boolean property '{}' = {}", key, boolValue);
        return boolValue;
    }

    /**
     * Retrieves a boolean property value by key with a default value
     *
     * @param key the property key
     * @param defaultValue the default value if key not found
     * @return the boolean value, or defaultValue if not found
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = props.getProperty(key);
        if (value == null) {
            logger.warn("Property key '{}' not found, using default value: {}", key, defaultValue);
            return defaultValue;
        }
        boolean boolValue = Boolean.parseBoolean(value.trim());
        logger.debug("Retrieved boolean property '{}' = {}", key, boolValue);
        return boolValue;
    }

    /**
     * Retrieves a long property value by key
     *
     * @param key the property key
     * @return the long value
     * @throws NumberFormatException if the property value cannot be parsed as a long
     * @throws NullPointerException if the property key is not found
     */
    public static long getLong(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            logger.error("Property key '{}' not found in configuration", key);
            throw new NullPointerException("Property '" + key + "' not found in configuration");
        }
        try {
            long longValue = Long.parseLong(value.trim());
            logger.debug("Retrieved long property '{}' = {}", key, longValue);
            return longValue;
        } catch (NumberFormatException e) {
            logger.error("Failed to parse property '{}' with value '{}' as long", key, value, e);
            throw new NumberFormatException("Property '" + key + "' value '" + value + "' is not a valid long");
        }
    }

    /**
     * Checks if a property key exists in the configuration
     *
     * @param key the property key to check
     * @return true if the key exists, false otherwise
     */
    public static boolean hasProperty(String key) {
        boolean exists = props.containsKey(key);
        logger.debug("Property '{}' exists: {}", key, exists);
        return exists;
    }

    /**
     * Reloads the properties from the configuration file
     * Useful for dynamic configuration updates during test execution
     */
    public static synchronized void reload() {
        logger.info("Reloading configuration file...");
        props.clear();
        loadProperties();
    }

    /**
     * Returns the total number of properties loaded
     *
     * @return number of properties
     */
    public static int getPropertyCount() {
        return props.size();
    }
}