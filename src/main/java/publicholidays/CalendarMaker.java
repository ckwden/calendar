package publicholidays;

import publicholidays.model.ConfigReader;
import publicholidays.database.DatabaseManagerImpl;
import publicholidays.model.calendar.Calendar;
import publicholidays.model.calendar.CalendarImpl;
import publicholidays.model.holiday.PublicHoliday;
import publicholidays.model.holiday.PublicHolidayDummy;
import publicholidays.model.holiday.PublicHolidayImpl;
import publicholidays.model.twilio.Messenger;
import publicholidays.model.twilio.MessengerDummy;
import publicholidays.model.twilio.MessengerImpl;

/**
 * CLass that makes the calendar model depending on the given modes
 */
public class CalendarMaker {

    private ConfigReader configReader;

    public CalendarMaker(ConfigReader reader) {
        this.configReader = reader;
    }

    /**
     * Creates a new Calendar object given the modes of the input and output APIs
     * @param input the desired mode of the input API
     * @param output the desired mode of the output API
     * @param dbFile the file path of the database
     * @return
     */
    public Calendar makeCalendar(String input, String output, String dbFile) {
        return new CalendarImpl(inputMode(input), outputMode(output), new DatabaseManagerImpl(dbFile));
    }

    /**
     * Creates a new PublicHoliday model depending on the given mode
     * @param mode the mode of the input API model
     * @return a PublicHoliday object related to the mode
     */
    private PublicHoliday inputMode(String mode) {
        if (mode.equalsIgnoreCase("offline")) {
            return new PublicHolidayDummy();
        } else if (mode.equalsIgnoreCase("online")) {
            return new PublicHolidayImpl(configReader.getHolidayKey());
        }
        return null;
    }

    /**
     * Creates a new Messenger model depending on the given mode
     * @param mode the mode of the output API model
     * @return a Messenger object related to the mode
     */
    private Messenger outputMode(String mode) {
        if (mode.equalsIgnoreCase("offline")) {
            return new MessengerDummy("Fake number to",
                    "Fake number from");
        } else if (mode.equalsIgnoreCase("online")) {
            return new MessengerImpl(configReader.getTwilioSID(),
                    configReader.getTwilioToken(),
                    configReader.getTwilioNumberTo(),
                    configReader.getTwilioNumberFrom());
        }
        return null;
    }
}
