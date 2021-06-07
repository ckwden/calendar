package publicholidays.database;

import publicholidays.model.holiday.Holiday;

import java.time.LocalDate;

/**
 * Handles the calls made to the database containing known holidays/not holidays
 */
public interface DatabaseManager {

    /**
     * Retrieves the matching record in the database
     * @param date The date of the holiday/not holiday record
     * @param countryCode The ISO Alpha-2 country code of the holiday/not holiday record
     * @return A Holiday object from the record in the database with the matching date and country code, null if
     *         there is no such record
     */
    Holiday getHoliday(LocalDate date, String countryCode);

    void commitHoliday(LocalDate date, String name, String countryCode);
}
