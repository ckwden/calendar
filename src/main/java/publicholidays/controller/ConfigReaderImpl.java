package publicholidays.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

/**
 * Implements the ConfigReader interface methods
 */
public class ConfigReaderImpl implements ConfigReader {

    private final JSONObject obj;
    private final String filePath;

    public ConfigReaderImpl(String filePath) {
        this.filePath = filePath;
        this.obj = getConfigDetails();
    }

    /**
     * Retrieves the required user data for configuration of the application
     * @return A JSONObject storing the user configuration data
     */
    private JSONObject getConfigDetails() {
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(new FileReader(this.filePath));
        }
        catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public String getHolidayKey() {
        return (String) obj.get("holidayKey");
    }

    @Override
    public String getTwilioSID() {
        JSONObject twilioObj = (JSONObject) obj.get("twilio");
        return (String) twilioObj.get("sid");
    }

    @Override
    public String getTwilioToken() {
        JSONObject twilioObj = (JSONObject) obj.get("twilio");
        return (String) twilioObj.get("token");
    }

    @Override
    public String getTwilioNumberTo() {
        JSONObject twilioObj = (JSONObject) obj.get("twilio");
        return (String) twilioObj.get("numberTo");
    }

    @Override
    public String getTwilioNumberFrom() {
        JSONObject twilioObj = (JSONObject) obj.get("twilio");
        return (String) twilioObj.get("numberFrom");
    }
}
