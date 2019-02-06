//Developed by Mayank Jain (GSLAB)//

package utils;

import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Reporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static utils.Listener.extentTest;
import static utils.JsonUtil.getJSONObject;
import static utils.JsonUtil.parseJSONFileToJSONArray;
import static utils.JsonUtil.parseJSONFileToJSONObject;

public class APIOperations {

    //declaration of class variables

    String statusCode=null;
    String apiResponse=null;
    String baseURI=null;
    String payloadFile=null;

    HashMap<String,String> apiRespInfo =null;
    List<Header> headerList=null;
    Headers headers = null;

    PropertyReader propertyReader =null;
    Properties properties = null;

    /**
     * Constructor: Instantiating config properties and header list
     */
    public APIOperations(){
        propertyReader = new PropertyReader();
        properties=propertyReader.getConfigProperties();
        headerList = new ArrayList<Header>();
    }

    //-----Below are the methods implemented----------------------------------------------//

    /**
     * @description Method to reset api configurations
     */
    public void resetConfig(){
        statusCode=null;
        apiResponse=null;
        baseURI=null;
        payloadFile=null;
        apiRespInfo =null;
        headerList.clear();
        headers = null;
    }

    /**
     * @description method to set configuration for api
     * @param element
     * @throws IOException
     */
    public void setConfig(String element) throws IOException {

        resetConfig();
        JSONArray jsonArray = parseJSONFileToJSONArray(properties.getProperty("API.configFile"));

        extentTest.log(LogStatus.INFO,"<---------Fetching properties for element: "+element+"---------->");

        Reporter.log("<br><---------Fetching properties for element: "+element+"----------><br>");

        JSONObject jsonObject = getJSONObject(jsonArray,"element",element);
        baseURI=jsonObject.getString("baseUrl");

        extentTest.log(LogStatus.INFO,"baseURI: "+baseURI);
        Reporter.log("<br>baseURI: "+baseURI+"<br>");

        JSONObject headerObj = jsonObject.getJSONObject("headers");
        for(String key : headerObj.keySet()){
            headerList.add(new Header(key,headerObj.getString(key)));
        }
        headers = new Headers(headerList);

        extentTest.log(LogStatus.INFO,"Headers: \n"+headerList.toString());
        Reporter.log("<br>Headers: <br>"+headerList.toString()+"<br>");

        extentTest.log(LogStatus.INFO,"<--------------------------------------------------------------->");
        Reporter.log("<br><---------------------------------------------------------------><br>");

        payloadFile=jsonObject.getString("payloads");
    }

    /**
     * @description method to send GET request for an api
     * @param element
     * @param endpoint
     * @return
     * @throws Exception
     */
    public HashMap<String, String> sendGETRequest(String element, String endpoint)throws Exception{

        setConfig(element);
        apiRespInfo = new HashMap<String,String>();

        RestAssured.baseURI = baseURI;

        extentTest.log(LogStatus.INFO,"Sending GET Request for URI =>  " + baseURI+endpoint);
        Reporter.log("Sending GET Request for URI =>  " + baseURI+endpoint+"<br>");

        Response response = given().headers(headers)
                .when().get(baseURI+endpoint)
                .then().extract().response();
        apiResponse =response.asString();
        statusCode=Integer.toString(response.getStatusCode());

        apiRespInfo.put("StatusCode",statusCode);
        apiRespInfo.put("Response",apiResponse);
        return apiRespInfo;
    }

    /**
     * @description method to send POST request for an api
     * @param element
     * @param endpoint
     * @return
     * @throws Exception
     */
    public HashMap<String, String> sendPOSTRequest(String element, String endpoint)throws Exception{

        setConfig(element);
        apiRespInfo = new HashMap<String,String>();

        extentTest.log(LogStatus.INFO,"Fetching payload for URI: "+baseURI+endpoint+"------->");
        Reporter.log("<br>Fetching payload for URI: "+baseURI+endpoint+"-------><br>");

        JSONObject jsonObjectExp = parseJSONFileToJSONObject(payloadFile);

        extentTest.log(LogStatus.INFO,"Payload: \n"+jsonObjectExp.toString());
        Reporter.log("<br>Payload: <br>"+jsonObjectExp.toString()+"<br>");

        RestAssured.baseURI = baseURI;

        extentTest.log(LogStatus.INFO,"Sending POST Request for URI =>  " + baseURI+endpoint);
        Reporter.log("Sending POST Request for URI =>  " + baseURI+endpoint+"<br>");

        Response response = given().headers(headers)
                .body(jsonObjectExp.toString())
                .when().post(baseURI+endpoint)
                .then().extract().response();
        apiResponse =response.asString();
        statusCode=Integer.toString(response.getStatusCode());

        apiRespInfo.put("StatusCode",statusCode);
        apiRespInfo.put("Response",apiResponse);
        apiRespInfo.put("payload",jsonObjectExp.toString());
        return apiRespInfo;
    }

    /**
     * @description method to send PUT request for an api
     * @param element
     * @param endpoint
     * @param primaryVal
     * @return
     * @throws Exception
     */
    public HashMap<String, String> sendPUTRequest(String element, String endpoint, String primaryVal)throws Exception{

        setConfig(element);
        apiRespInfo = new HashMap<String,String>();
        JSONObject jsonObjectExp = parseJSONFileToJSONObject(payloadFile);
        RestAssured.baseURI = baseURI;

        extentTest.log(LogStatus.INFO,"Sending PUT Request for URI =>  " + baseURI+endpoint);
        Reporter.log("Sending PUT Request for URI =>  " + baseURI+endpoint+"<br>");

        Response response = given().headers(headers)
                .body(jsonObjectExp.toString())
                .when().put(baseURI+endpoint+"/"+primaryVal)
                .then().extract().response();
        apiResponse =response.asString();
        statusCode=Integer.toString(response.getStatusCode());

        apiRespInfo.put("StatusCode",statusCode);
        apiRespInfo.put("Response",apiResponse);
        apiRespInfo.put("payload",jsonObjectExp.toString());
        return apiRespInfo;
    }

    /**
     * @description method to send PATCH request for an api
     * @param element
     * @param endpoint
     * @param primaryVal
     * @return
     * @throws Exception
     */
    public HashMap<String, String> sendPATCHRequest(String element, String endpoint, String primaryVal)throws Exception{

        setConfig(element);
        apiRespInfo = new HashMap<String,String>();
        JSONObject jsonObjectExp = parseJSONFileToJSONObject(payloadFile);
        RestAssured.baseURI = baseURI;

        extentTest.log(LogStatus.INFO,"Sending PATCH Request for URI =>  " + baseURI+endpoint);
        Reporter.log("Sending PATCH Request for URI =>  " + baseURI+endpoint+"<br>");

        Response response = given().headers(headers)
                .body(jsonObjectExp.toString())
                .when().patch(baseURI+endpoint+"/"+primaryVal)
                .then().extract().response();
        apiResponse =response.asString();
        statusCode=Integer.toString(response.getStatusCode());

        apiRespInfo.put("StatusCode",statusCode);
        apiRespInfo.put("Response",apiResponse);
        apiRespInfo.put("payload",jsonObjectExp.toString());
        return apiRespInfo;
    }

    /**
     * @description method to send DELETE request for an api
     * @param element
     * @param endpoint
     * @param primaryVal
     * @return
     * @throws Exception
     */
    public HashMap<String, String> sendDELETERequest(String element, String endpoint, String primaryVal)throws Exception{

        setConfig(element);
        apiRespInfo = new HashMap<String,String>();

        RestAssured.baseURI = baseURI;

        extentTest.log(LogStatus.INFO,"Sending DELETE Request for URI =>  " + baseURI+endpoint);
        Reporter.log("Sending DELETE Request for URI =>  " + baseURI+endpoint+"<br>");

        Response response = given().headers(headers)
                .when().delete(baseURI+endpoint+"/"+primaryVal)
                .then().extract().response();
        apiResponse =response.asString();
        statusCode=Integer.toString(response.getStatusCode());

        apiRespInfo.put("StatusCode",statusCode);
        apiRespInfo.put("Response",apiResponse);
        return apiRespInfo;
    }


}
