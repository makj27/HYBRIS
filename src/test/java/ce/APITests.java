package ce;

import base.BaseTest;
import com.relevantcodes.extentreports.LogStatus;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;

import static utils.Listener.extentTest;

public class APITests extends BaseTest {

     //declaration of class variables



    //--------------------Below are the test methods implemented--------------------------------------------//

    /**
     * @Description Test method to validate GET request/response for API
     * @param element
     * @param endPoint
     */
    @Parameters({"element","endPoint"})
    @Test
    public void validateGETAPI(String element, String endPoint){
        try{

            extentTest.log(LogStatus.INFO,"Starting GET validation for element: "+element+", endpoint: "+endPoint);
            Reporter.log("<br>Starting GET validation for element: "+element+", endpoint: "+endPoint+"<br>");

            HashMap<String, String> apiStatus = apiOperation.sendGETRequest(element,endPoint);

            extentTest.log(LogStatus.INFO,"Status code =>  " + apiStatus.get("StatusCode"));
            Reporter.log("Status code =>  " + apiStatus.get("StatusCode")+"<br>");

            extentTest.log(LogStatus.INFO,"API Response is =>  " + apiStatus.get("Response"));
            Reporter.log("API Response is =>  " + apiStatus.get("Response")+"<br>");

            assertValue(apiStatus.get("StatusCode"),"200","Test: validateGETAPI");

            extentTest.log(LogStatus.PASS,"API for element: "+element+", endpoint: "+endPoint+" is working as expected!!!");
            Reporter.log("<br>API for element: "+element+", endpoint: "+endPoint+" is working as expected!!!<br>");


            log.info("Test: validateGETAPI(): PASSED");

        }catch (Exception ex){
            //log.error("ERROR in validateGETAPI(): Test Failed: "+ ex.getStackTrace().toString());
            //extentTest.log(LogStatus.PASS,"API for element: "+element+", endpoint: "+endPoint+" is working as expected!!!");
            //Reporter.log("<br>API for element: "+element+", endpoint: "+endPoint+" is working as expected!!!<br>");
            Assert.fail("testApiResponse(): Test Failed: "+ ex.getStackTrace().toString());
        }

    }

    /**
     * @Description Test method to validate POST request/response for API
     * @param element
     * @param endPoint
     */
    @Parameters({"element","endPoint"})
    @Test
    public void validatePOSTAPI(String element, String endPoint){
        try{

            extentTest.log(LogStatus.INFO,"Starting POST validation for element: "+element+", endpoint: "+endPoint);
            Reporter.log("<br>Starting POST validation for element: "+element+", endpoint: "+endPoint+"<br>");

            HashMap<String, String> apiStatus = apiOperation.sendPOSTRequest(element,endPoint);

            extentTest.log(LogStatus.INFO,"Status code =>  " + apiStatus.get("StatusCode"));
            Reporter.log("Status code =>  " + apiStatus.get("StatusCode")+"<br>");

            extentTest.log(LogStatus.INFO,"API Response is =>  " + apiStatus.get("Response"));
            Reporter.log("API Response is =>  " + apiStatus.get("Response")+"<br>");

            JSONAssert.assertEquals("POST API reponse does not matches with payload\n",apiStatus.get("payload"),apiStatus.get("Response"), JSONCompareMode.LENIENT);

            extentTest.log(LogStatus.PASS,"POST API reponse matches with payload");
            Reporter.log("<br>POST API reponse matches with payload<br>");

            log.info("Test: validatePOSTAPI(): PASSED");
        }
        catch (Exception ex){
            //log.error("validatePOSTAPI(): Test Failed: "+ ex.getStackTrace().toString());
            Assert.fail("validatePOSTAPI(): Test Failed: "+ ex.getStackTrace().toString());
        }
    }



}
