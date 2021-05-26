package publicholidays.model.holiday;

import java.time.LocalDate;

public interface PublicHoliday {

    Holiday getHoliday(LocalDate date);

    void setCountryCode(String countryCode);
}
