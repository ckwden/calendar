package publicholidays.model.calendar;

import publicholidays.model.holiday.Holiday;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Calendar {

    Map<LocalDate, Holiday> getHolidays();

    List<LocalDate> getNotHolidays();

    boolean isHoliday(LocalDate date);

    void sendReport(int month);

    void setCountry(String countryCode);
}
