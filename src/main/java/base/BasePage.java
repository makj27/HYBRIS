package base;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utils.LogFactory;

public class BasePage {

    //Declaration of class variables
    public static Logger log = null;
    protected static WebDriver driver;


    //Constructor
    public BasePage(WebDriver driver){

        this.driver=driver;
        log = LogFactory.getLogger();
        PageFactory.initElements(driver, this);

    }

    /**
     * @description Method to open url
     * @param url
     * @throws Exception
     */
    public void openURL(String url) throws Exception {
        try{
            driver.get(url);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new Exception("Exception thrown by BasePage.openURL() method!!!");
        }
    }

}
