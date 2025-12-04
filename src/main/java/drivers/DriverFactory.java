package drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.ConfigReader;

import java.time.Duration;

public class DriverFactory {
    private static WebDriver driver;

    // init driver using config values
    public static void initDriver() {
        if (driver == null) {
            String browser = ConfigReader.get("browser");
            boolean headless = ConfigReader.getBoolean("headless");

            if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (headless) options.addArguments("--headless=new");
                driver = new ChromeDriver(options);
            } else {
                throw new RuntimeException("Browser not supported: " + browser);
            }

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getInt("implicitWait")));
        }
    }

    public static WebDriver getDriver() {
        if (driver == null) initDriver();
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
