package publicholidays;

import org.junit.Before;
import org.junit.Test;
import publicholidays.model.calendar.Calendar;
import publicholidays.model.calendar.CalendarImpl;
import publicholidays.model.holiday.Holiday;
import publicholidays.model.holiday.HolidayImpl;
import publicholidays.model.holiday.PublicHoliday;
import publicholidays.model.twilio.Messenger;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CalendarTest {

    private Calendar calendar;
    private PublicHoliday ph;
    private Messenger twilio;

    @Before
    public void setup() {
        ph = mock(PublicHoliday.class);
        twilio = mock(Messenger.class);
        calendar = new CalendarImpl(ph, twilio);
    }

    @Test
    public void testSendReport() {
        calendar.sendReport(1);
        verify(twilio).sendReport("");
    }

    @Test
    public void testIsHoliday() {
        LocalDate date = LocalDate.now();
        calendar.isHoliday(date);
        verify(ph).getHoliday(date);
    }

    @Test
    public void testSendReportsWithHolidays() {
        LocalDate date = LocalDate.of(2021, 1, 1);
        Holiday holiday = new HolidayImpl("Random", 2021, 1, 1);
        when(ph.getHoliday(date)).thenReturn(holiday);
        calendar.isHoliday(date);
        calendar.sendReport(1);
        verify(twilio).sendReport("Known Holidays in January:\n" + holiday.getName());

        LocalDate newDate = LocalDate.of(2021, 1, 26);
        Holiday newHoliday = new HolidayImpl("Australia Day", 2021, 1, 26);
        when(ph.getHoliday(newDate)).thenReturn(newHoliday);
        calendar.isHoliday(newDate);
        calendar.sendReport(1);
        String report = "Known Holidays in January:\n" + holiday.getName() + "\n" + newHoliday.getName();
        verify(twilio).sendReport(report);
    }

    @Test
    public void testHolidaysAndNotHolidays() {
        LocalDate date = LocalDate.of(2021, 1, 1);
        Holiday holiday = new HolidayImpl("Random", 2021, 1, 1);
        when(ph.getHoliday(date)).thenReturn(holiday);
        calendar.isHoliday(date);

        LocalDate newDate = LocalDate.of(2021, 2, 2);
        Holiday newHoliday = new HolidayImpl("Holiday", 2021, 2, 2);
        when(ph.getHoliday(newDate)).thenReturn(newHoliday);
        calendar.isHoliday(newDate);

        LocalDate now = LocalDate.now();
        when(ph.getHoliday(now)).thenReturn(null);
        calendar.isHoliday(now);

        assertEquals(1, calendar.getNotHolidays().size());
        assertEquals(now, calendar.getNotHolidays().get(0));
        assertEquals(2, calendar.getHolidays().values().size());

        calendar.sendReport(1);
        verify(twilio).sendReport("Known Holidays in JANUARY:\n" + holiday.getName());

        calendar.sendReport(2);
        verify(twilio).sendReport("Known Holidays in FEBRUARY:\n" + newHoliday.getName());
    }

    @Test
    public void testSetCountry() {
        calendar.setCountry("AU");
        verify(ph).setCountryCode("AU");
    }
}

