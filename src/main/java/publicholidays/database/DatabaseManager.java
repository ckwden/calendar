package publicholidays.database;

import publicholidays.model.holiday.Holiday;

import java.time.LocalDate;
import java.util.List;

/**
 * Handles the calls made to the database containing known holidays/not holidays
 */
public interface DatabaseManager {

    /**
     * Retrieves the matching record in the database
     * @param date The date of the holiday/not holiday record
     * @param countryCode The ISO Alpha-2 country code of the holiday/not holiday record
     * @return A list of Holiday objects from the record in the database with the matching date and country code
     */
    List<Holiday> getHoliday(LocalDate date, String countryCode);

    /**
     * Commits a holiday into the database cache
     * @param date the date of the holiday
     * @param name the name of the holiday
     * @param countryCode the country code of the holiday
     */
    void commitHoliday(LocalDate date, String name, String countryCode);

    /**
     * Removes old cached data in the database of the date given
     * @param date the date of the record
     * @param countryCode the country code of the record
     */
    void removeOldHolidays(LocalDate date, String countryCode);
}
