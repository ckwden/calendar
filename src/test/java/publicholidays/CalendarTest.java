package publicholidays;

import org.junit.Before;
import org.junit.Test;
import publicholidays.database.DatabaseManager;
import publicholidays.model.calendar.Calendar;
import publicholidays.model.calendar.CalendarImpl;
import publicholidays.model.holiday.Holiday;
import publicholidays.model.holiday.HolidayImpl;
import publicholidays.model.holiday.PublicHoliday;
import publicholidays.model.twilio.Messenger;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CalendarTest {

    private Calendar calendar;
    private PublicHoliday ph;
    private Messenger twilio;
    private DatabaseManager db;

    @Before
    public void setup() {
        ph = mock(PublicHoliday.class);
        twilio = mock(Messenger.class);
        db = mock(DatabaseManager.class);
        calendar = new CalendarImpl(ph, twilio, db);
    }

    @Test
    public void testSendReport() {
        calendar.sendReport(1);
        verify(twilio).sendReport("Known holidays in " + Month.of(1).name() + ":");
    }

    @Test
    public void testGetFromAPI() {
        LocalDate date = LocalDate.now();
        calendar.getFromAPI(date);
        verify(ph).makeCall(date.getDayOfMonth(), date.getMonth().getValue(), date.getYear());
    }

    @Test
    public void testGetFromDatabase() {
        LocalDate date = LocalDate.now();
        when(ph.getCountryCode()).thenReturn("AU");
        calendar.getFromDatabase(date);
        verify(ph).getCountryCode();
        verify(db).getHoliday(date, ph.getCountryCode());
    }

    @Test
    public void testSendReportsWithHolidays() {
        LocalDate date = LocalDate.of(2021, 1, 1);
        Holiday holiday = new HolidayImpl("Random", 2021, 1, 1);
        when(ph.getCountryCode()).thenReturn("AU");
        List<Holiday> holidays = new ArrayList<>();
        holidays.add(holiday);
        when(ph.getHoliday(date)).thenReturn(holidays);

        calendar.determineHoliday(date, ph.getHoliday(date));
        assertEquals(1, calendar.getHolidays().values().size());
        verify(db).commitHoliday(date, holiday.getName(), ph.getCountryCode());

        calendar.sendReport(1);
        verify(twilio).sendReport("Known holidays in " + Month.of(1).name() + ":\n" + holiday.getName());

        LocalDate newDate = LocalDate.of(2021, 1, 26);
        Holiday newHoliday = new HolidayImpl("Australia Day", 2021, 1, 26);
        when(db.getHoliday(newDate, ph.getCountryCode())).thenReturn(newHoliday);
        boolean res = calendar.getFromDatabase(newDate);
        assertTrue(res);
        assertEquals(2, calendar.getHolidays().values().size());
        verify(db, times(0)).commitHoliday(date, newHoliday.getName(), ph.getCountryCode());

        calendar.sendReport(1);
        String report = "Known holidays in " + Month.of(1).name() + ":\n" + newHoliday.getName() + "\n" +
                holiday.getName();
        verify(twilio).sendReport(report);
    }

    @Test
    public void testHolidaysAndNotHolidays() {
        LocalDate date = LocalDate.of(2021, 1, 1);
        Holiday holiday = new HolidayImpl("Random", 2021, 1, 1);
        when(ph.getCountryCode()).thenReturn("AU");
        List<Holiday> holidays = new ArrayList<>();
        holidays.add(holiday);
        when(ph.getHoliday(date)).thenReturn(holidays);
        calendar.getFromAPI(date);
        verify(ph).makeCall(date.getDayOfMonth(), date.getMonth().getValue(), date.getYear());

        calendar.determineHoliday(date, ph.getHoliday(date));
        LocalDate newDate = LocalDate.of(2021, 2, 2);
        Holiday newHoliday = new HolidayImpl("Holiday", 2021, 2, 2);
        when(db.getHoliday(newDate, ph.getCountryCode())).thenReturn(newHoliday);
        calendar.getFromDatabase(newDate);

        LocalDate now = LocalDate.now();
        when(ph.getHoliday(now)).thenReturn(null);
        calendar.determineHoliday(now, ph.getHoliday(now));

        assertEquals(1, calendar.getNotHolidays().size());
        assertEquals(now, calendar.getNotHolidays().get(0));
        assertEquals(2, calendar.getHolidays().values().size());

        calendar.sendReport(1);
        verify(twilio).sendReport("Known holidays in " + Month.of(1).name() + ":\n" + holiday.getName());

        calendar.sendReport(2);
        verify(twilio).sendReport("Known holidays in " + Month.of(2).name() + ":\n" + newHoliday.getName());
    }

    @Test
    public void testOverThreshold() {

    }

    @Test
    public void testSetCountry() {
        calendar.setCountry("AU");
        verify(ph).setCountryCode("AU");
    }
}