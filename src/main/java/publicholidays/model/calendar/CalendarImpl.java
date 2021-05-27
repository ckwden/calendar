package publicholidays.model.calendar;

import publicholidays.model.holiday.PublicHoliday;
import publicholidays.model.twilio.Messenger;
import publicholidays.model.holiday.Holiday;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarImpl implements Calendar {

    private Map<LocalDate, Holiday> holidays;
    private List<LocalDate> notHolidays;
    private PublicHoliday input;
    private Messenger output;

    public CalendarImpl(PublicHoliday publicHoliday, Messenger output) {
        this.holidays = new HashMap<>();
        this.notHolidays = new ArrayList<>();
        this.input = publicHoliday;
        this.output = output;
    }

    @Override
    public Map<LocalDate, Holiday> getHolidays() {
        return this.holidays;
    }

    @Override
    public List<LocalDate> getNotHolidays() {
        return this.notHolidays;
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        Holiday holiday = input.getHoliday(date);
        if (holiday == null) {
            notHolidays.add(date);
            return false;
        }
        holidays.put(date, holiday);
        return true;
    }

    @Override
    public void sendReport(int month) {
        output.sendReport(makeReport(month));
    }

    @Override
    public void setCountry(String countryCode) {
        input.setCountryCode(countryCode);
    }

    /**
     * Creates the report data to send for the output API
     * @param month The month of the known public holidays in the report represented as its number in a year
     * @return A string representation of the report to send
     */
    private String makeReport(int month) {
        StringBuilder report = new StringBuilder();
        report.append("Known holidays in ").append(Month.of(month).name()).append(":\n");
        for (Holiday hol : holidays.values()) {
            if (hol.getMonth() == month) {
                report.append(hol.getName()).append("\n");
            }
        }
        return report.toString().stripTrailing();
    }
}
