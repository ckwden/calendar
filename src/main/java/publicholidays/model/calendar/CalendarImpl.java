package publicholidays.model.calendar;

import publicholidays.database.DatabaseManager;
import publicholidays.model.holiday.PublicHoliday;
import publicholidays.model.twilio.Messenger;
import publicholidays.model.holiday.Holiday;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements the Calendar interface
 */
public class CalendarImpl implements Calendar {

    private Map<LocalDate, Holiday> holidays;
    private List<LocalDate> notHolidays;
    private PublicHoliday input;
    private Messenger output;
    private DatabaseManager db;

    public CalendarImpl(PublicHoliday publicHoliday, Messenger output, DatabaseManager db) {
        this.holidays = new HashMap<>();
        this.notHolidays = new ArrayList<>();
        this.input = publicHoliday;
        this.output = output;
        this.db = db;
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
    public void sendReport(int month) {
        output.sendReport(makeReport(month));
    }

    @Override
    public void setCountry(String countryCode) {
        input.setCountryCode(countryCode);
    }

    @Override
    public void setThresholdCount(int count) {}

    @Override
    public void getFromAPI(LocalDate date) {
        input.makeCall(date.getDayOfMonth(), date.getMonth().getValue(), date.getYear());
    }

    @Override
    public void determineHoliday(LocalDate date, List<Holiday> holiday) {
        if (holiday == null) {
            if (!notHolidays.contains(date)) {
                notHolidays.add(date);
            }
            holidays.remove(date);
            db.commitHoliday(date, "", input.getCountryCode());
        } else {
//            if (holidays.containsKey(date)) {
//                holidays.replace(date, holiday);
//            } else {
//                holidays.put(date, holiday);
//            }
//            db.commitHoliday(date, holiday.getName(), input.getCountryCode());
        }
    }

    @Override
    public boolean getFromDatabase(LocalDate date) {
        Holiday holiday = db.getHoliday(date, input.getCountryCode());
        if (holiday == null) {
            return false;
        } else {
            if (holiday.getName().equals("")) {
                if (!notHolidays.contains(date)) {
                    notHolidays.add(date);
                }
            } else {
                if (!holidays.containsKey(date)) {
                    holidays.put(date, holiday);
                }
            }
            return true;
        }
    }

    @Override
    public Messenger getMessenger() {
        return this.output;
    }

    @Override
    public PublicHoliday getPublicHoliday() {
        return this.input;
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
