package publicholidays.model.holiday;

import publicholidays.controller.JSONManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

public class PublicHolidayImpl implements PublicHoliday {

    private String url;
    private String countryCode;
    private String key;
    private JSONManager json;

    public PublicHolidayImpl(String key, JSONManager json) {
        this.url = "https://holidays.abstractapi.com/v1/";
        this.key = key;
        this.json = json;
    }

    @Override
    public Holiday getHoliday(LocalDate date) {
        String response = getData(url, key, countryCode, date.getDayOfMonth(), date.getMonth().getValue(),
                date.getYear());
        Holiday hol = json.getHoliday(response);
        return hol;
    }

    @Override
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String getCountryCode() {
        return this.countryCode;
    }

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
