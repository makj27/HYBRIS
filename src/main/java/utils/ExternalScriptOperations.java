package utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class ExternalScriptOperations {


    /**
     * Description: Method to kill the process. Exiting cmd after command completion
     * @param p
     * @throws Exception
     */
    public void stopProcess(Process p) throws Exception{

        p.destroy();
        Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
    }


    /**
     * @description: method to run external batch files
     * @param location
     * @param file
     * @return
     * @throws Exception
     */
    public Process runCommand(String location, String file) throws Exception
    {
        try
        {
            ProcessBuilder build = new ProcessBuilder("cmd", "/C","start /wait", file);

            // checking the command in list
            System.out.println("command: " + build.command());
            build.redirectErrorStream(true);

            build.directory(new File(location));
            Process p = build.start();

            p.waitFor(2, TimeUnit.MINUTES);
            return p;

        }
        catch (IOException e)
        {
            System.out.println(e.getStackTrace());
            return null;
        }
    }

    /**
     * Description: Method to get filestream from a file
     * @param fLoc
     * @return
     * @throws IOException
     */
    public String getFileStream(String fLoc) throws IOException{

        StringBuilder fileStream = new StringBuilder();
        String line=null;
        File file = new File(fLoc);
        BufferedReader br = new BufferedReader(new FileReader(file));
        while ((line = br.readLine()) != null) {
            fileStream.append(line+"<br>");
        }
        return fileStream.toString();
    }

    /**
     * description: method to get output stream of a process
     * @param p
     * @throws IOException
     */
    public void getOutputStream(Process p) throws IOException
    {
        StringBuilder buildOutput = new StringBuilder();
        String outputLine = null;
        String output =null;

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(p.getInputStream()));

        // read the output from the command
        System.out.println("Here is the standard output of the command:\n");

        while ((outputLine = stdInput.readLine()) != null) {
            buildOutput.append(outputLine);
            buildOutput.append(System.getProperty("line.separator"));
        }
        output = buildOutput.toString();
        System.out.println(output);
    }

    /**
     * Description: method to get error stream from a process
     * @param p
     * @throws IOException
     */
    public void getErrorStream(Process p) throws IOException
    {
        StringBuilder buildError = new StringBuilder();
        String errorLine = null;
        String error =null;

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(p.getErrorStream()));

        // read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((errorLine = stdError.readLine()) != null) {
            buildError.append(errorLine);
            buildError.append(System.getProperty("line.separator"));
        }
        error = buildError.toString();
        System.out.println(error);
    }

    /**
     * Description: Method to parse json file into json object
     * @param filename
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }


}





