package publicholidays.database;

import publicholidays.model.holiday.Holiday;
import publicholidays.model.holiday.HolidayImpl;

import java.time.LocalDate;

public class DatabaseManagerDummy implements DatabaseManager {

    @Override
    public Holiday getHoliday(LocalDate date, String countryCode) {
        return new HolidayImpl("Fake", 2021, 12, 12);
    }

    @Override
    public void commitHoliday(LocalDate date, String name, String countryCode) {
        System.out.println("Committing: " + date.toString() + ", " + name + ", " + countryCode + " into database");
    }
}
