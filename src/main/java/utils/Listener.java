package utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Listener implements ITestListener, ISuiteListener, IInvokedMethodListener {

    //declaration of class variable
    protected static ExtentReports reports;
    public static ExtentTest extentTest;

    /**
     * @description method returns extentTest object
     * @return
     */
    public ExtentTest getExtentTest(){
        return extentTest;
    }


    /**
     * @description This method belongs to ISuiteListener and will execute before the Suite start
     * @param arg0
     */
    public void onStart(ISuite arg0) {


        Reporter.log("\nBegin executing Suite **" + arg0.getName()+"**\n", true);

        //Extent Report//
        System.out.println("on start");
        reports = new ExtentReports("./extentreports/"+new SimpleDateFormat("yyyy-MM-dd hh-mm-ss-ms").format(new Date()) +"-"+arg0.getName()+"-ExtReports.html");

    }


    /**
     * @description This method belongs to ISuiteListener and will execute, once the Suite is finished
     * @param arg0
     */
    public void onFinish(ISuite arg0) {

        Reporter.log("End executing Suite **" + arg0.getName()+"**\n", true);
        //Extent Report//
        System.out.println("on finish");
        reports.endTest(extentTest);
        reports.flush();

    }

    /**
     * @description This method belongs to ITestListener and will execute before starting of Test set/batch
     * @param arg0
     */
    public void onStart(ITestContext arg0) {

        Reporter.log("Begin executing Test Set *" + arg0.getName()+"*\n", true);

    }

    /**
     * @description This method belongs to ITestListener and will execute, once the Test set/batch is finished
     * @param arg0
     */
    public void onFinish(ITestContext arg0) {

        Reporter.log("Completed executing Test Set *" + arg0.getName()+"*\n", true);

    }

     /**
     * @description This belongs to ITestListener and will execute only when the Test is pass
     * @param result
     */
    public void onTestSuccess(ITestResult result) {

        // This is calling the printTestResults method

        printTestResults(result);
        //Extent Reports//
        System.out.println("on Test success");
        //extentTest.log(LogStatus.PASS, result.getMethod().getMethodName() + "(): Test Method is passed");

    }

    /**
     * @description This method belongs to ITestListener and will execute only on the event of fail test
     * @param result
     */
    public void onTestFailure(ITestResult result) {

        // This is calling the printTestResults method
        printTestResults(result);
        //Extent Reports//
        System.out.println("on Test failure");
        //extentTest.log(LogStatus.FAIL, result.getMethod().getMethodName() + "(): Test Method is failed");

    }

     /**
     * @description This method belongs to ITestListener and will execute before the main test start (@Test)
     * @param result
     */
    public void onTestStart(ITestResult result) {

        Reporter.log("--------------------------------------------------------------------------<br>", true);
        Reporter.log("Starting Test: " + result.getName()+"<br>", true);

        //Extent Reports//
        System.out.println("on Test start");
        extentTest = reports.startTest(result.getMethod().getMethodName());
        extentTest.log(LogStatus.INFO, "Starting Test Method: "+result.getMethod().getMethodName()+"()");

    }

    /**
     * @Description This belongs to ITestListener and will execute only if any of the main Test(@Test) get skipped
     * @param result
     */
    public void onTestSkipped(ITestResult result) {

        printTestResults(result);
        //Extent Reports//
        System.out.println("on Test skipped");
        //extentTest.log(LogStatus.SKIP, result.getMethod().getMethodName() + "() Test Method is skipped");
    }


    /**
     * @description This is the method which will be executed in case of Test pass or fail
     *              This will provide the information on the Test
     * @param result
     */
    private void printTestResults(ITestResult result) {

        //Reporter.log("Test Method in " + result.getTestClass().getName()+"::"+result.getName(), true);

        if (result.getParameters().length != 0) {

            String params = "";

            for (Object parameter : result.getParameters()) {

                params += parameter.toString() + ",";

            }

            Reporter.log("Test Method had the following parameters : " + params, true);
            extentTest.log(LogStatus.INFO, "Test Method had the following parameters : " + params);

        }

        String status = null;

        switch (result.getStatus()) {

            case ITestResult.SUCCESS:

                status = "PASSED";
                extentTest.log(LogStatus.PASS, "Test Status::: "+ result.getTestClass().getName()+":: "+result.getName()+"(): " + status);
                break;

            case ITestResult.FAILURE:

                status = "FAILED";
                extentTest.log(LogStatus.FAIL, "Test Status::: "+ result.getTestClass().getName()+":: "+result.getName()+"(): " + status);

                break;

            case ITestResult.SKIP:

                status = "SKIPPED";
                extentTest.log(LogStatus.SKIP, "Test Status::: "+ result.getTestClass().getName()+":: "+result.getName()+"(): " + status);

        }

        Reporter.log("<br>Test Status:::"+ result.getTestClass().getName()+"::"+result.getName()+":" + status, true);
        Reporter.log("<br>--------------------------------------------------------------------------", true);



    }

    /**
     * @description This will return method names to the calling function
     * @param method
     * @return
     */
    private String returnMethodName(ITestNGMethod method) {

        return method.getRealClass().getSimpleName() + "." + method.getMethodName();

    }







}