package publicholidays.model.holiday;

import java.time.LocalDate;
import java.util.List;

/**
 * Models the Holiday API
 */
public interface PublicHoliday {

    /**
     * Uses the given date to make a call to the Holiday API to see if it is a public holiday or not
     * @param date the date used for the query
     * @return A Holiday object modelling the response from the API, null if the date is not a public holiday
     */
    List<Holiday> getHoliday(LocalDate date);

    void setCountryCode(String countryCode);

    String getCountryCode();

    String getResponse();

    void makeCall(int dayOfMonth, int value, int year);
}
