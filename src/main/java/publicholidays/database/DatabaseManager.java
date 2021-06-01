package publicholidays.database;

import publicholidays.model.holiday.Holiday;

import java.time.LocalDate;

public interface DatabaseManager {

    Holiday getHoliday(LocalDate date, String countryCode);

    void commitHoliday(LocalDate date, String name, String countryCode);

}
