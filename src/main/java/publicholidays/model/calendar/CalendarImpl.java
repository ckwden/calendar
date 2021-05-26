package publicholidays.model.calendar;

import publicholidays.model.holiday.PublicHoliday;
import publicholidays.model.twilio.Messenger;
import publicholidays.model.holiday.Holiday;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CalendarImpl implements Calendar {

    public CalendarImpl(PublicHoliday publicHoliday, Messenger output) {

    }

    @Override
    public Map<LocalDate, Holiday> getHolidays() {
        return null;
    }

    @Override
    public List<LocalDate> getNotHolidays() {
       return null;
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        return false;
    }

    @Override
    public void sendReport(int month) {

    }

    @Override
    public void setCountry(String countryCode) {

    }
}
