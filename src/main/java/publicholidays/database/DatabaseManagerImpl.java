package publicholidays.database;

import publicholidays.model.holiday.Holiday;
import publicholidays.model.holiday.HolidayImpl;

import java.sql.*;
import java.time.LocalDate;

/**
 * Class that implements the DatabaseManager interface
 */
public class DatabaseManagerImpl implements DatabaseManager {

    private final String filePath;
    private Connection conn;

    public DatabaseManagerImpl(String filePath) {
        this.filePath = filePath;
        setUpConnection();
    }

    /**
     * Opens the database for connection
     */
    private void setUpConnection() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:" + filePath);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Holiday getHoliday(LocalDate date, String countryCode) {
        int year = date.getYear();
        int month = date.getMonth().getValue();
        int day = date.getDayOfMonth();
        Holiday holiday = null;
        try {
            String query = String.format("SELECT * FROM holidays WHERE holidays.year=%s AND holidays.month=%s " +
                    "AND holidays.day=%s AND holidays.country='%s';", year, month, day, countryCode);
            Statement statement = conn.createStatement();
            ResultSet record = statement.executeQuery(query);
            while (record.next()) {
                int holidayYear = record.getInt("year");
                int holidayMonth = record.getInt("month");
                int holidayDay = record.getInt("day");
                String name = record.getString("name");
                holiday = new HolidayImpl(name, holidayYear, holidayMonth, holidayDay);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return holiday;
    }

    @Override
    public void commitHoliday(LocalDate date, String name, String countryCode) {
        int year = date.getYear();
        int month = date.getMonth().getValue();
        int day = date.getDayOfMonth();
        try {
            String query = String.format("INSERT OR REPLACE INTO holidays VALUES(%s, %s, %s, \"%s\", \"%s\");",
                    year, month, day,
                    name, countryCode);
            System.out.println(query);
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
