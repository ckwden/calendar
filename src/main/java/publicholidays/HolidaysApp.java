package publicholidays;

import publicholidays.controller.ConfigReaderImpl;
import publicholidays.model.calendar.Calendar;
import publicholidays.view.CountryInputImpl;
import javafx.application.Application;
import javafx.stage.Stage;
import publicholidays.view.SecondaryWindow;

import java.util.List;

/**
 * The main driver of the application
 */
public class HolidaysApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Holidays");
        List<String> params = getParameters().getRaw();
        if (params.size() < 2) {
            System.out.println("Please specify the modes of the application.");
            System.exit(0);
        } else {
            CalendarMaker maker = new CalendarMaker(new ConfigReaderImpl("src/main/resources/config.json"));
            Calendar calendar = maker.makeCalendar(params.get(0), params.get(1), "src/main/resources/holidays.db");
            SecondaryWindow country = new CountryInputImpl(primaryStage, 300, 100, calendar);
            country.display();
            primaryStage.show();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
