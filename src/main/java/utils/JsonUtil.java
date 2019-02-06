package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class JsonUtil {

    //----------Below are the methods implemented-----------------------------------//

    /**
     * @description method to parse json file to a json object
     * @param filename
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static JSONObject parseJSONFileToJSONObject(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }

    /**
     * @description method to parse json file to a string
     * @param filename
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static String parseJSONFileToString(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return content;
    }

    /**
     * @description method to parse json file to a json array
     * @param filename
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static JSONArray parseJSONFileToJSONArray(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONArray(content);
    }

    /**
     * @description overloaded method to a specific json object from a json array
     * @param jsonArray
     * @param key
     * @param value
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static JSONObject getJSONObject(JSONArray jsonArray, String key, String value)throws JSONException, IOException{

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonobject = jsonArray.getJSONObject(i);
            if(jsonobject.getString(key).equalsIgnoreCase(value)){
                return jsonobject;
            }
        }
        return null;
    }

    /**
     * @description overloaded method to a specific json object from a json file
     * @param filename
     * @param key
     * @param value
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static JSONObject getJSONObject(String filename, String key, String value)throws JSONException, IOException{

        JSONArray jsonArray = parseJSONFileToJSONArray(filename);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonobject = jsonArray.getJSONObject(i);
            if(jsonobject.getString("key").equalsIgnoreCase(value)){
                return jsonobject;
            }
        }
        return null;
    }








}
