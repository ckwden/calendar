package publicholidays.model.holiday;

import publicholidays.model.JsonManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

/**
 * Implements the PublicHoliday interface
 */
public class PublicHolidayImpl implements PublicHoliday {

    private final String url;
    private String countryCode;
    private final String key;

    public PublicHolidayImpl(String key) {
        this.url = "https://holidays.abstractapi.com/v1/";
        this.key = key;
    }

    @Override
    public Holiday getHoliday(LocalDate date) {
        String response = getData(url, key, countryCode, date.getDayOfMonth(), date.getMonth().getValue(),
                date.getYear());
        return JsonManager.getHoliday(response);
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
    public void makeCall(int dayOfMonth, int value, int year) {

    }

    /**
     * Sends the request to the Holiday API for a given date
     * @param link the API url
     * @param key the user's authentication key for the API
     * @param countryCode the ISO Alpha-2 code for the country for the query
     * @param dayOfMonth the day of the month for the query
     * @param month the number of the month in the year for the query
     * @param year the year for the query
     * @return the response after sending the request to the API
     */
    private String getData(String link, String key, String countryCode, int dayOfMonth, int month, int year) {
        BufferedReader reader;
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(link + "?api_key=" + key + "&country=" + countryCode + "&year=" + year +
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
        return response.toString();
    }
}
