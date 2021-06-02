package publicholidays.model.holiday;

/**
 * Implements the Holiday interface
 */
public class HolidayImpl implements Holiday {

    private final String name;
    private final int year;
    private final int month;
    private final int day;

    public HolidayImpl(String name, int year, int month, int day) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getMonth() {
        return month;
    }

    @Override
    public int getDay() {
        return day;
    }

}
