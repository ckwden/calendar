package publicholidays.database;

import publicholidays.model.holiday.Holiday;
import publicholidays.model.holiday.HolidayImpl;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseManagerImpl implements publicholidays.database.DatabaseManager {

    private String filePath;
    private Connection conn;

    public DatabaseManagerImpl(String filePath) {
        this.filePath = filePath;
        setUpConnection();
    }

    private void setUpConnection() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:" + filePath);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Holiday getHoliday(LocalDate date, String countryCode) {
        return null;
    }

    @Override
    public void commitHoliday(LocalDate date, String name, String countryCode) {

    }

}
