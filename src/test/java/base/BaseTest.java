package base;

import com.relevantcodes.extentreports.ExtentTest;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.APIOperations;
import utils.ExternalScriptOperations;
import utils.LogFactory;
import utils.PropertyReader;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static utils.Listener.extentTest;

public class BaseTest {

    //Declaration of class variables/objects
    public static Logger log;
    public static ExtentTest extentLogger;
    private static WebDriver driver;
    private static PropertyReader propertyReader;
    protected static Properties properties ;
    DesiredCapabilities capabilities;
    protected static APIOperations apiOperation;
    protected static ExternalScriptOperations extScriptOps;


    //---------------------TestNG methods implemented below------------------------------------//
    /**
     * @description method to setup basic configuration of the framework
     * @param browser
     */
    @Parameters({"browser"})
    @BeforeSuite
    public void setup(@Optional("No Browser") String browser){

        log = LogFactory.getLogger();
        extentLogger = extentTest;
        propertyReader = new PropertyReader();
        properties=propertyReader.getConfigProperties();
        apiOperation = new APIOperations();
        extScriptOps=new ExternalScriptOperations();

        if(!browser.equalsIgnoreCase("No Browser"))
            initializeWebDriverInstance(browser);
    }

    /**
     * Decriptions: Closes/quits all open driver instances.
     * @throws InterruptedException
     */
    @AfterSuite
    public void tearDown() throws InterruptedException {
        if(driver != null) {
            driver.close();
            driver.quit();
            driver=null;
        }
    }



    //-------------------Commom methods implemented below-------------------------------------//
    /**
     * Description: TO initialize WebDriver instance.
     * @param browserType
     */
    public void initializeWebDriverInstance(String browserType){
        try{

            System.out.println("Initializing WebDriver!!!");
            switch (browserType.toUpperCase()) {

                case "FIREFOX":

                    FirefoxOptions options = new FirefoxOptions();
                    options.setCapability("marionette", false);
                    System.setProperty("webdriver.gecko.driver", properties.getProperty("firefox.driverPath"));
                    driver = new FirefoxDriver(options);
                    break;

                case "CHROME":
                    System.setProperty("webdriver.chrome.driver",properties.getProperty("chrome.driverPath"));
                    driver = new ChromeDriver();
                    break;

                case "IE":
                    //-----------TO-DO: IE Not Launching-----------------------------//
                    capabilities = DesiredCapabilities.internetExplorer();
                    capabilities.setCapability(CapabilityType.BROWSER_NAME, "IE");
                    capabilities.setCapability(InternetExplorerDriver.
                            INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
                    System.setProperty("webdriver.ie.driver", properties.getProperty("ie.driverpath"));
                    driver = new InternetExplorerDriver();
                    break;

                default:
                    System.out.println("browser : " + browserType + " is invalid, Launching Chrome as browser of choice..");
                    driver = new ChromeDriver();
            }

            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            System.out.println("WebDriver Initialized!!!");

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    /**
     * Description: returns WebDriver Instance
     * @return
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * @description returns ExternalScriptOperations instance
     * @return
     */
    public ExternalScriptOperations getExtScriptOps() {
        return extScriptOps;
    }

    /**
     * Description: returns config properties.
     * @return
     */
    public Properties getProperties() {
        return properties;
    }


    /**
     * Description: Asserts actual and expected integer values. Return message on error.
     * @param actualValue
     * @param expectedValue
     * @param message
     * @throws Exception
     */
    public void assertValue(int actualValue, int expectedValue, String message) throws Exception {
        try{
            Assert.assertEquals(actualValue,expectedValue,message+" :FAILED:");
        }catch (Exception e){
            throw new Exception("Error in method:" +"assertValue()");
        }

    }

    /**
     * Description: Asserts actual and expected string values. Return message on error.
     * @param actualValue
     * @param expectedValue
     * @param message
     * @throws Exception
     */
    public void assertValue(String actualValue, String expectedValue, String message) throws Exception {
        try{
            Assert.assertEquals(actualValue,expectedValue,message+" :FAILED:");
        }catch (Exception e){
            throw new Exception("Error in method:" +"assertValue()");
        }

    }

    /**
     * Description: Asserts true boolean values. Return message on error.
     * @param bool
     * @param message
     * @throws Exception
     */
    public void assertTrue(Boolean bool, String message) throws Exception {
        try{
            Assert.assertTrue(bool, message+" :FAILED:");
        }catch (Exception e){
            throw new Exception("Error in method:" +"assertTrue()");
        }

    }

    /**
     * Description: Asserts false boolean values. Return message on error.
     * @param bool
     * @param message
     */
    public void assertFalse(Boolean bool, String message) {
        try{
            Assert.assertFalse(bool, message+ ":FAILED:");
        }catch (Exception e){
            //throw new Exception("Error in method:" +"assertTrue()");
            System.out.println("Error in assertFalse():" +e.getStackTrace());
        }

    }
}
