package publicholidays.model.holiday;

import java.time.LocalDate;

public class PublicHolidayDummy implements PublicHoliday {

    @Override
    public Holiday getHoliday(LocalDate date) {
        return null;
    }

    @Override
    public void setCountryCode(String countryCode) {

    }
}
