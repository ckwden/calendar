package publicholidays.model.holiday;

import publicholidays.controller.ConnectionManager;
import publicholidays.controller.JSONManager;

import java.time.LocalDate;

public class PublicHolidayImpl implements PublicHoliday {

    public PublicHolidayImpl(String holidayKey, JSONManager jsonManager, ConnectionManager connectionManager) { }

    @Override
    public Holiday getHoliday(LocalDate date) {
        return null;
    }

    @Override
    public void setCountryCode(String countryCode) {

    }
}
