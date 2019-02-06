package ce;

import base.BaseTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.FBLoginPage;

import static utils.Listener.extentTest;

public class UITests extends BaseTest {

    //Variable/Objects declarations

    FBLoginPage fbLoginPage;
    WebDriver driver;


    //-------Below are the test methods implemented----------------------------------------------------//

    /**
     * Description: Test Method to validate login functionality
     * @param url
     */
    @Parameters({"url"})
    @Test
    public void validateLoginFn(String url){

        try{
            fbLoginPage = new FBLoginPage(getDriver());

            fbLoginPage.doLogin(url,properties.getProperty("facebook.username"),properties.getProperty("facebook.password"));
            extentTest.log(LogStatus.PASS,"Login done successfully!!!!");
            Reporter.log("<br>Login done successfully!!!!<br>");
            log.info("Test: validateLoginFn(): PASSED");

        }catch (Exception ex){
            log.error("validateLoginFn(): Test Failed: "+ ex.getStackTrace().toString());
            Assert.fail("Error in validateLoginFn()");
        }
    }


}
