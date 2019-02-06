package ce;

import base.BaseTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static utils.Listener.extentTest;

public class ExternalScriptTests extends BaseTest {

    //declartion of class objects/variables

    String fileOutputStream =null;
    String fileErrorStream =null;


    //---------Below are test methods implemented------------------------------------------------------//

    /**
     * @Description: Test method to validate external script execution and log results into html reports.
     * @param fileName
     * @param location
     */
    @Parameters({"file","location"})
    @Test
    public void runScript(String fileName, String location)
    {

        try {

            if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
                extentTest.log(LogStatus.INFO, "Starting Script: " + fileName);
                Process p = extScriptOps.runCommand(location, fileName);

                fileOutputStream = extScriptOps.getFileStream(location + "\\reports\\output.txt");
                Reporter.log("Script OutputStream: <br>" + fileOutputStream + "<br>", true);
                extentTest.log(LogStatus.INFO, "Script OutputStream: <br>" + fileOutputStream + "<br>");

                fileErrorStream = extScriptOps.getFileStream(location + "\\reports\\error.txt");
                extScriptOps.stopProcess(p);
                if(!fileErrorStream.isEmpty()) {
                    Reporter.log("Script ErrorStream: <br>" + fileErrorStream + "<br>", true);
                    extentTest.log(LogStatus.FAIL, "Error in executing batch file:"+fileName+"<br>Script ErrorStream: <br>" + fileErrorStream + "<br>");
                    Assert.fail("Error in executing Batch File:" +fileName);
                }
            }
        }catch(Exception ex){

            Assert.fail("Test: runScript(): FAILED: for File:" +fileName + ex.getStackTrace().toString());
        }
    }

}
