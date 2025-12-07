package drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.ConfigReader;

import java.time.Duration;

/**
 * Factory class for WebDriver initialization and management.
 * Supports Chrome, Firefox, and Edge browsers with configurable options.
 */
public class DriverFactory {
    private static final Logger logger = LogManager.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverFactory() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Initializes WebDriver based on configuration settings
     */
    public static void initDriver() {
        if (driver.get() != null) {
            logger.warn("Driver already initialized for this thread");
            return;
        }

        String browser = ConfigReader.get("browser", "chrome").toLowerCase();
        boolean headless = ConfigReader.getBoolean("headless", false);

        logger.info("Initializing {} driver (headless: {})", browser, headless);

        try {
            WebDriver webDriver = createDriver(browser, headless);
            driver.set(webDriver);

            configureDriver();
        } catch (Exception e) {
            logger.error("Failed to initialize driver: {}", e.getMessage(), e);
            throw new RuntimeException("Driver initialization failed", e);
        }
    }

    /**
     * Creates WebDriver instance based on browser type
     */
    private static WebDriver createDriver(String browser, boolean headless) {
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(getChromeOptions(headless));

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver(getFirefoxOptions(headless));

            case "edge":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver(getEdgeOptions(headless));

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    /**
     * Configures Chrome options
     */
    private static ChromeOptions getChromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        return options;
    }

    /**
     * Configures Firefox options
     */
    private static FirefoxOptions getFirefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("--headless");
        }
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        return options;
    }

    /**
     * Configures Edge options
     */
    private static EdgeOptions getEdgeOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        if (headless) {
            options.addArguments("--headless");
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        return options;
    }

    /**
     * Applies common driver configurations
     */
    private static void configureDriver() {
        WebDriver webDriver = driver.get();
        webDriver.manage().window().maximize();

        int implicitWait = ConfigReader.getInt("implicitWait", 10);
        int pageLoadTimeout = ConfigReader.getInt("pageLoadTimeout", 30);

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
    }

    /**
     * Returns the WebDriver instance for current thread
     */
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            initDriver();
        }
        return driver.get();
    }

    /**
     * Quits the driver and removes it from ThreadLocal
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            try {
                driver.get().quit();
                logger.info("Driver quit successfully");
            } catch (Exception e) {
                logger.error("Error while quitting driver: {}", e.getMessage(), e);
            } finally {
                driver.remove();
            }
        }
    }
}