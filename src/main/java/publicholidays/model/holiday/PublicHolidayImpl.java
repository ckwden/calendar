package publicholidays.model.holiday;

import publicholidays.controller.ConnectionManagerImpl;
import publicholidays.controller.JSONManagerImpl;

import java.time.LocalDate;

public class PublicHolidayImpl implements PublicHoliday {

    public PublicHolidayImpl(String holidayKey, JSONManagerImpl jsonManager, ConnectionManagerImpl connectionManager) { }

    @Override
    public Holiday getHoliday(LocalDate date) {
        return null;
    }

    @Override
    public void setCountryCode(String countryCode) {

    }
}
