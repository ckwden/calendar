package publicholidays.view;

import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import publicholidays.model.calendar.Calendar;

import java.time.LocalDate;

/**
 * The main GUI view for the users, displays the calendar to be interacted with by the user
 */
public interface CalendarView {

    Scene getScene();

    /**
     * Sets up the required elements for the display
     */
    void setScene();

    DatePicker getDatePicker();

    /**
     * Creates new window to display the result of the user interacting with the calendar dates
     * @param isHoliday true if the date clicked on is a public holiday, false otherwise
     */
    void showResult(boolean isHoliday);

    /**
     * Creates a new window to ask how the user would like to load the information if it already exists in the database
     * @param cal the calendar modelled in the view
     * @param date the date picked by the user
     */
    void requestLoadMethod(Calendar cal, LocalDate date);
}
