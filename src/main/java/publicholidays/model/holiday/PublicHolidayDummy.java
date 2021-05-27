package publicholidays.model.holiday;

import java.time.LocalDate;

public class PublicHolidayDummy implements PublicHoliday {

    @Override
    public Holiday getHoliday(LocalDate date) {
        return new HolidayImpl("fake", date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }

    @Override
    public void setCountryCode(String countryCode) {

    }
}
