package publicholidays.model.holiday;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implements the PublicHoliday interface as a dummy
 */
public class PublicHolidayDummy implements PublicHoliday {

    private String countryCode;

    /**
     * Returns a holiday based on a random number generated to simulate holidays/not holidays
     * @param date the date used for the query
     * @return a dummy Holiday object if the number generated is 0, null if it is 1
     */
    @Override
    public List<Holiday> getHoliday(LocalDate date) {
        int num = new Random().nextInt(2);
        if (num == 0) {
            List<Holiday> holidays = new ArrayList<>();
            holidays.add(new HolidayImpl("fake", date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
            holidays.add(new HolidayImpl("fake2", date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
            return holidays;
        }
        return null;
    }

    @Override
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String getCountryCode() {
        return this.countryCode;
    }

    @Override
    public void makeCall(int dayOfMonth, int value, int year) {

    }

    @Override
    public String getResponse() {
        return "Success";
    }
}
