package publicholidays.model.calendar;

import publicholidays.model.holiday.Holiday;
import publicholidays.model.holiday.PublicHoliday;
import publicholidays.model.twilio.Messenger;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Class that models the calendar to be viewed
 */
public interface Calendar {

    /**
     * Retrieves the known public holidays of the calendar
     * @return the known holidays
     */
    Map<LocalDate, Holiday> getHolidays();

    /**
     * Retrieves the known dates that are not public holidays of the calendar
     * @return the known dates that are not public holidays
     */
    List<LocalDate> getNotHolidays();

    /**
     * Sends the report to the configured Twilio account
     * @param month the number of the month in the year that the report is based on
     */
    void sendReport(int month);

    /**
     * Sets the country which the calendar for the public holidays is based on
     * @param countryCode the ISO Alpha-2 code for the country
     */
    void setCountry(String countryCode);

    /**
     * Triggers the API call to retrieve information about the given date
     * @param date the date that is queried to the Holiday API
     * @return true if the date is a public holiday, false if not
     */
    void getFromAPI(LocalDate date);

    /**
     * Triggers the call to the database to retrieve the record, if it exists, about the given date
     * @param date the date that is queried to the database
     * @return true the record matching the date exists in the database, false if not
     */
    boolean getFromDatabase(LocalDate date);

    Messenger getMessenger();

    PublicHoliday getPublicHoliday();

    void determineHoliday(LocalDate now, List<Holiday> holiday);

    void setThresholdCount(int count);
}
