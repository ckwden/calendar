package publicholidays.controller;

import org.json.simple.JSONArray;
import publicholidays.model.holiday.Holiday;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import publicholidays.model.holiday.HolidayImpl;
import org.json.simple.parser.ParseException;

/**
 * Handles the response in JSON foramt from the Holiday API call
 */
public class JsonManager {

    public static Holiday getHoliday(String response) {
        JSONParser parser = new JSONParser();
        HolidayImpl holiday = null;
        try {
            JSONArray holidays = (JSONArray) parser.parse(response);
            if (holidays.size() > 0) {
                JSONObject obj = (JSONObject) holidays.get(0);
                if (obj != null) {
                    String name = (String) obj.get("name");
                    String year = (String) obj.get("date_year");
                    String month = (String) obj.get("date_month");
                    String day = (String) obj.get("date_day");
                    holiday = new HolidayImpl(name, Integer.parseInt(year), Integer.parseInt(month),
                            Integer.parseInt(day));
                }
            }
        } catch (ParseException e) {
            System.out.println("Wrong json format");
        }
        return holiday;
    }
}