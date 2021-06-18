package publicholidays.view;

import javafx.scene.Scene;
import javafx.scene.control.DatePicker;

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
     * Changes the window background a few times to simulate 'blinking' of the window
     */
    void blink();
}
