package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DataUtils {


    private static final String testDataPath = "src/test/resources/TestData/";

    /**
     * @param fileName
     * @param field
     * @return
     */
    //TODO : reading data from json file
 /*   public static String getGsonData(String fileName, String field) throws FileNotFoundException {
        FileReader reader = new FileReader(testDataPath + fileName + ".json");
        JsonElement jsonElement = JsonParser.parseReader(reader);
        return jsonElement.getAsJsonObject().get(field).getAsString();

    }*/
    public static String getGsonData(String fileName, String field) throws FileNotFoundException {
        FileReader reader = new FileReader(testDataPath + fileName + ".json");
        JsonElement jsonElement = JsonParser.parseReader(reader);

        // Check if the field exists in the JSON file to prevent NullPointerException
        if (jsonElement.getAsJsonObject().has(field)) {
            return jsonElement.getAsJsonObject().get(field).getAsString();
        } else {
            throw new FileNotFoundException("Field " + field + " not found in " + fileName + ".json");
        }
    }


    public static String getPropertiesData(String fileName, String key) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(testDataPath + fileName + ".properties"));
        return properties.getProperty(key);

    }


}
