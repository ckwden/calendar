package publicholidays.controller.calendar;

import publicholidays.model.calendar.Calendar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import publicholidays.view.CalendarViewImpl;
import publicholidays.view.MessageWindowImpl;
import publicholidays.view.SecondaryWindow;

import java.time.LocalDate;

/**
 * Acts as the intermediate object between the Calendar model and its view
 */
public class CalendarController implements ChangeListener<LocalDate> {

    private CalendarViewImpl view;
    private Calendar calendar;

    public CalendarController(CalendarViewImpl view, Calendar calendar) {
        this.view = view;
        this.calendar = calendar;
        setColourChange();
    }

    /**
     * Configures the colour changes for the cells to indicate whether the dates are a public holiday or not
     */
    public void setColourChange() {
        // https://stackoverflow.com/questions/50552075/datepicker-only-mark-certain-days-with-the-color-red
        Callback<DatePicker, DateCell> factory = new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        // Update colour according to the whether it is holiday or not
                        if (calendar.getHolidays().containsKey(item)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #008000;");
                        } else if (calendar.getNotHolidays().contains(item)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        view.getDatePicker().setDayCellFactory(factory);
    }

    /**
     * Observes changes to user input when clicking on calendar cells to update the calendar model and view
     * @param observable The {@code ObservableValue} which value changed
     * @param oldValue   The old value
     * @param newValue   The new value
     */
    @Override
    public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
        if (newValue.getYear() != LocalDate.now().getYear()) {
            SecondaryWindow window = new MessageWindowImpl("Error", "Must select date in current year");
            window.display();
        }
        boolean inDatabase = calendar.getFromDatabase(newValue);
        if (inDatabase) {
            view.requestLoadMethod(calendar, newValue);
        } else {
            calendar.getFromAPI(newValue);
            displayResult(newValue);
        }
    }

    /**
     * Manipulates the view to display the result of clicking on a date cell
     * @param date the date of the cell that has been clicked
     */
    public void displayResult(LocalDate date) {
        if (calendar.getNotHolidays().contains(date)) {
            view.showResult(false);
        } else if (calendar.getHolidays().containsKey(date)) {
            view.showResult(true);
        }
    }
}