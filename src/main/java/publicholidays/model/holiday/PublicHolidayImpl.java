package publicholidays.model.holiday;

import publicholidays.model.JsonManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

/**
 * Implements the PublicHoliday interface
 */
public class PublicHolidayImpl implements PublicHoliday {

    private final String url;
    private String countryCode;
    private final String key;
    private String response;

    public PublicHolidayImpl(String key) {
        this.url = "https://holidays.abstractapi.com/v1/";
        this.key = key;
        this.response = null;
    }

    @Override
    public List<Holiday> getHoliday(LocalDate date) {
        List<Holiday> holidays = JsonManager.getHoliday(this.response);
        if (holidays.size() == 0) {
            return null;
        }
        return holidays;
    }

    @Override
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String getCountryCode() {
        return this.countryCode;
    }

    @Override
    public String getResponse() { return this.response; }

    /**
     * Sends the request to the Holiday API for a given date
     * @param dayOfMonth the day of the month for the query
     * @param month the number of the month in the year for the query
     * @param year the year for the query
     */
    @Override
    public void makeCall(int dayOfMonth, int month, int year) {
        BufferedReader reader;
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(this.url + "?api_key=" + key + "&country=" + countryCode + "&year=" + year +
                    "&month=" + month + "&day=" + dayOfMonth);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Accept", "application/json");

            String line;
            int status = http.getResponseCode();
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(http.getErrorStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
            }
            while((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            http.disconnect();
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.response = response.toString();
    }
}
