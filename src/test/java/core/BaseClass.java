package core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PlanCalculatorPage;

import java.time.Duration;

public class BaseClass {

    protected static final String URL = "https://axilion.z6.web.core.windows.net/#/";
    protected static final String X_WAY_PULSE_OPTION = "X Way Pulse";
    protected static final String X_WAY_PULSE_TWIN_OPTION = "X Way (Pulse + Twin)";
    protected static final String X_WAY_PULSE_TWIN_NEURAL_OPTION = "X Way (Pulse + Twin + Neural)";
    protected static final String METRIC_OPTION = "METRIC";
    protected static final String IMPERIAL_OPTION = "IMPERIAL";
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected PlanCalculatorPage calculatorPage;

    protected enum BrowserTypes {
        FIREFOX,
        CHROME,
        EDGE
    }

    protected static WebDriver startBrowser(BrowserTypes browserType) {
        switch (browserType) {
            case EDGE:
                EdgeOptions edgeOptions = new EdgeOptions();
                return new EdgeDriver(edgeOptions);
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                return new ChromeDriver(chromeOptions);
            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                return new FirefoxDriver(firefoxOptions);
        }
        return null;
    }

    @BeforeEach
    protected void setUp() {
        driver = startBrowser(BrowserTypes.CHROME);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(URL);
        calculatorPage = new PlanCalculatorPage(driver);
    }

    @AfterEach
    protected void tearDown() {
        driver.close();
    }
}

