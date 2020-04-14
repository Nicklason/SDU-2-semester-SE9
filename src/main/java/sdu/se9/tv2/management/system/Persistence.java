package sdu.se9.tv2.management.system;

import java.io.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Persistence {
    private File file;

    public Persistence(String fileName) {
        this.file  = new File(fileName);
    }

    public JSONObject read() throws ParseException {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(this.file))
        {
            //Read JSON file
            Object obj = parser.parse(reader);

            return (JSONObject) obj;
        } catch (FileNotFoundException e) {
            // e.printStackTrace();
        } catch (IOException e) {
            // e.printStackTrace();
        }

        // Return null if there was an error

        return null;
    }

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
