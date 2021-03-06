package publicholidays.model;

import org.json.simple.JSONArray;
import publicholidays.model.holiday.Holiday;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import publicholidays.model.holiday.HolidayImpl;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the response in JSON format from the APIs
 */
public class JsonManager {

    private static JSONParser parser = new JSONParser();

    /**
     * Creates a list of Holiday objects modelling a response from a valid request to the Holiday API
     * @param response the response from the request to the Holiday API
     * @return a list of Holiday objects modelling the response in JSON format
     */
    public static List<Holiday> getHoliday(String response) {
        List<Holiday> holidaysList = new ArrayList<>();
        try {
            JSONArray holidays = (JSONArray) parser.parse(response);
            for (Object holiday : holidays) {
                JSONObject obj = (JSONObject) holiday;
                if (obj != null) {
                    String name = (String) obj.get("name");
                    String year = (String) obj.get("date_year");
                    String month = (String) obj.get("date_month");
                    String day = (String) obj.get("date_day");
                    holidaysList.add(new HolidayImpl(name, Integer.parseInt(year), Integer.parseInt(month),
                            Integer.parseInt(day)));
                }
            }
        } catch (ParseException e) {
            System.out.println("Wrong json format");
        }
        return holidaysList;
    }

    /**
     * Retrieves the error message from the Holiday API after sending a request
     * @param response the error response from the API
     * @return the error message from the response, null if there is no response
     */
    public static String getHolidayErrorMessage(String response) {
        if (response == null) {
            return null;
        }
        String message = "";
        try {
            JSONObject obj = (JSONObject) parser.parse(response);
            JSONObject errorObj = (JSONObject) obj.get("error");
            message = (String) errorObj.get("message");
        } catch (ParseException ignored) {

        }
        return message;
    }

    /**
     * Retrieves the message from an error response of the Twilio API after posting a request
     * @param response the response from the API
     * @return the message inside the error response, null if successfully sent the SMS
     */
    public static String getMessengerMessage(String response) {
        if (response == null) {
            return null;
        }
        String message = null;
        try {
            JSONObject obj = (JSONObject) parser.parse(response);
            message = (String) obj.get("message");
        } catch (ParseException ignored) {

        }
        return message;
    }
}