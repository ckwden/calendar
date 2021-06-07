package publicholidays.model;

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
        String key = (String) obj.get("holidayKey");
        if (key == null || key.isEmpty()) {
            System.out.println("Please configure your key for the Holiday API");
            System.exit(0);
        }
        return key;
    }

    @Override
    public String getTwilioSID() {
        JSONObject twilioObj = (JSONObject) obj.get("twilio");
        String sid = (String) twilioObj.get("sid");
        if (sid == null || sid.isEmpty()) {
            System.out.println("Please configure your Twilio SID");
            System.exit(0);
        }
        return sid;
    }

    @Override
    public String getTwilioToken() {
        JSONObject twilioObj = (JSONObject) obj.get("twilio");
        String token = (String) twilioObj.get("token");
        if (token == null || token.isEmpty()) {
            System.out.println("Please configure your Twilio token");
            System.exit(0);
        }
        return token;
    }

    @Override
    public String getTwilioNumberTo() {
        JSONObject twilioObj = (JSONObject) obj.get("twilio");
        String number = (String) twilioObj.get("numberTo");
        if (number == null || number.isEmpty()) {
            System.out.println("Please configure a number to send the report to");
            System.exit(0);
        } else if (!(number.charAt(0) == '+')) {
            System.out.println("The number to send the report to is not in the correct format");
            System.exit(0);
        }
        try {
            Integer.parseInt(number.substring(1));
        } catch (Exception e) {
            System.out.println("Please ensure that the number to send the report to are composed only of digits");
            System.exit(0);
        }
        return number;
    }

    @Override
    public String getTwilioNumberFrom() {
        JSONObject twilioObj = (JSONObject) obj.get("twilio");
        String number = (String) twilioObj.get("numberFrom");
        if (number == null || number.isEmpty()) {
            System.out.println("Please configure a number to send the report from");
            System.exit(0);
        } else if (!(number.charAt(0) == '+')) {
            System.out.println("The number to send the report from is not in the correct format");
            System.exit(0);
        }
        try {
            Integer.parseInt(number.substring(1));
        } catch (Exception e) {
            System.out.println("Please ensure that the number to send the report from are composed only of digits");
            System.exit(0);
        }
        return number;
    }
}
