package publicholidays;

import publicholidays.controller.ConfigReader;
import publicholidays.controller.ConfigReaderImpl;
import publicholidays.controller.JSONManagerImpl;
import publicholidays.model.calendar.Calendar;
import publicholidays.model.holiday.PublicHolidayImpl;
import publicholidays.model.twilio.Messenger;
import publicholidays.model.twilio.MessengerDummy;
import publicholidays.model.twilio.MessengerImpl;
import publicholidays.view.CountryInput;
import publicholidays.view.CountryInputImpl;
import publicholidays.model.calendar.CalendarImpl;
import publicholidays.model.holiday.PublicHoliday;
import publicholidays.model.holiday.PublicHolidayDummy;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

public class HolidaysApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Holidays");
        List<String> params = getParameters().getRaw();
        PublicHoliday input = null;
        Calendar calendar = null;
        Messenger output = null;
        if (params.size() < 2) {
            System.out.println("Please specify the modes of the application.");
        } else {
            ConfigReader config = new ConfigReaderImpl("src/main/resources/config.json");
            if (params.get(0).equals("online") && params.get(1).equals("online")) {
                input = new PublicHolidayImpl(config.getHolidayKey(), new JSONManagerImpl());
                output = new MessengerImpl(config.getTwilioSID(), config.getTwilioToken(), config.getTwilioNumberTo(),
                        config.getTwilioNumberFrom());
            } else if (params.get(0).equals("offline") && params.get(1).equals("offline")){
                input = new PublicHolidayDummy();
                output = new MessengerDummy(config.getTwilioSID(), config.getTwilioToken(), config.getTwilioNumberTo(),
                        config.getTwilioNumberFrom());
            } if (params.get(0).equals("online") && params.get(1).equals("offline")) {
                input = new PublicHolidayImpl(config.getHolidayKey(),  new JSONManagerImpl());
                output = new MessengerDummy(config.getTwilioSID(), config.getTwilioToken(), config.getTwilioNumberTo(),
                        config.getTwilioNumberFrom());
            } else if (params.get(0).equals("offline") && params.get(1).equals("online")){
                input = new PublicHolidayDummy();
                output = new MessengerImpl(config.getTwilioSID(), config.getTwilioToken(), config.getTwilioNumberTo(),
                        config.getTwilioNumberFrom());
            }
            calendar = new CalendarImpl(input, output);
        }
        CountryInput country = new CountryInputImpl(primaryStage, 300, 100, calendar);
        country.setScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
