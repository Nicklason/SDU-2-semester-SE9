package sdu.se9.tv2.management.system;

import java.io.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Read and write to JSON files
 */
public class Persistence {
    /**
     * The file to read / write from
     */
    private File file;

    /**
     * Crates a new instance of the Persistence class
     * @param fileName The name of the file, for example: `somefile.json`
     */
    public Persistence(String fileName) {
        this.file = new File(fileName);
    }

    /**
     * Reads the file and returns parsed JSON
     * @return
     * @throws ParseException If the format of the file is incorrect a ParseException will be thrown
     */
    public JSONObject read() throws ParseException {
        // Creates a new JSON parser
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(this.file))
        {
            // Parses JSON file using file reader
            Object obj = parser.parse(reader);

            // Returns JSONObject
            return (JSONObject) obj;
        } catch (FileNotFoundException e) {
            // e.printStackTrace();
        } catch (IOException e) {
            // e.printStackTrace();
        }

        // Return null if there was an error (failed to read / parse file)

        return null;
    }

    /**
     * Overwrites a file
     * @param obj The JSON object to write
     */
    public void write(JSONObject obj) {
        // Make file writer
        try (FileWriter file = new FileWriter(this.file)) {
            // Write to the file
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
