package publicholidays;

import publicholidays.controller.ConfigReader;
import publicholidays.controller.JSONManagerImpl;
import publicholidays.database.DatabaseManager;
import publicholidays.database.DatabaseManagerDummy;
import publicholidays.database.DatabaseManagerImpl;
import publicholidays.model.calendar.Calendar;
import publicholidays.model.calendar.CalendarImpl;
import publicholidays.model.holiday.PublicHoliday;
import publicholidays.model.holiday.PublicHolidayDummy;
import publicholidays.model.holiday.PublicHolidayImpl;
import publicholidays.model.twilio.Messenger;
import publicholidays.model.twilio.MessengerDummy;
import publicholidays.model.twilio.MessengerImpl;

public class CalendarMaker {

    private ConfigReader configReader;

    public CalendarMaker(ConfigReader reader) {
        this.configReader = reader;
    }

    public Calendar makeCalendar(String input, String output, String dbFile) {
        return new CalendarImpl(inputMode(input), outputMode(output), databaseMode(input, dbFile));
    }

    private PublicHoliday inputMode(String mode) {
        if (mode.equalsIgnoreCase("offline")) {
            return new PublicHolidayDummy();
        } else if (mode.equalsIgnoreCase("online")) {
            return new PublicHolidayImpl(configReader.getHolidayKey(), new JSONManagerImpl());
        }
        return null;
    }

    private Messenger outputMode(String mode) {
        if (mode.equalsIgnoreCase("offline")) {
            return new MessengerDummy(configReader.getTwilioSID(),
                    configReader.getTwilioToken(),
                    configReader.getTwilioNumberTo(),
                    configReader.getTwilioNumberFrom());
        } else if (mode.equalsIgnoreCase("online")) {
            return new MessengerImpl(configReader.getTwilioSID(),
                    configReader.getTwilioToken(),
                    configReader.getTwilioNumberTo(),
                    configReader.getTwilioNumberFrom());
        }
        return null;
    }

    private DatabaseManager databaseMode(String mode, String dbFile) {
        if (mode.equalsIgnoreCase("offline")) {
            return new DatabaseManagerDummy();
        } else if (mode.equalsIgnoreCase("online")) {
            return new DatabaseManagerImpl(dbFile);
        }
        return null;
    }
}
