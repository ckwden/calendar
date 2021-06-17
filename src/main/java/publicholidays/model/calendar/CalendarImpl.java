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

    private Map<LocalDate, List<Holiday>> holidays;
    private List<LocalDate> notHolidays;
    private PublicHoliday input;
    private Messenger output;
    private DatabaseManager db;
    private int thresholdCount;

    public CalendarImpl(PublicHoliday publicHoliday, Messenger output, DatabaseManager db) {
        this.holidays = new HashMap<>();
        this.notHolidays = new ArrayList<>();
        this.input = publicHoliday;
        this.output = output;
        this.db = db;
        this.thresholdCount = 0;
    }

    @Override
    public Map<LocalDate, List<Holiday>> getHolidays() {
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
    public void setThresholdCount(int count) { this.thresholdCount = count; }

    @Override
    public int getThresholdCount() { return this.thresholdCount; }

    @Override
    public void getFromAPI(LocalDate date) {
        input.makeCall(date.getDayOfMonth(), date.getMonth().getValue(), date.getYear());
    }

    @Override
    public void determineHoliday(LocalDate date, List<Holiday> holidays) {
        db.removeOldHolidays(date, input.getCountryCode());
        if (holidays == null || holidays.size() == 0) {
            if (!notHolidays.contains(date)) {
                notHolidays.add(date);
            }
            this.holidays.remove(date);
            db.commitHoliday(date, "", input.getCountryCode());
        } else {
            if (this.holidays.containsKey(date)) {
                this.holidays.replace(date, holidays);
            } else {
                this.holidays.put(date, holidays);
            }
            for (Holiday holiday : holidays) {
                db.commitHoliday(date, holiday.getName(), input.getCountryCode());
            }
            notHolidays.remove(date);
        }
    }

    @Override
    public boolean getFromDatabase(LocalDate date) {
        List<Holiday> holidays = db.getHoliday(date, input.getCountryCode());
        if (holidays == null || holidays.size() == 0) {
            return false;
        } else {
            if (holidays.get(0).getName().equals("")) {
                if (!notHolidays.contains(date)) {
                    notHolidays.add(date);
                }
            } else {
                if (!this.holidays.containsKey(date)) {
                    this.holidays.put(date, holidays);
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
        boolean overThreshold = false;
        report.append("Known holidays in ").append(Month.of(month).name()).append(":\n");
        for (LocalDate date : holidays.keySet()) {
            if (date.getMonth().getValue() == month) {
                if (holidays.get(date).size() > this.thresholdCount && !overThreshold) {
                    overThreshold = true;
                }
                for (Holiday hol : holidays.get(date)) {
                    report.append(hol.getName()).append("\n");
                }
            }
        }
        if (overThreshold) {
            report.insert(0, "*");
        }
        return report.toString().stripTrailing();
    }
}
