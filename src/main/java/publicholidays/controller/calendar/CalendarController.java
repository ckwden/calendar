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

public class CalendarController implements ChangeListener<LocalDate> {

    private CalendarViewImpl view;
    private Calendar calendar;

    public CalendarController(CalendarViewImpl view, Calendar calendar) {
        this.view = view;
        this.calendar = calendar;
        setColourChange();
    }

    // https://stackoverflow.com/questions/50552075/datepicker-only-mark-certain-days-with-the-color-red
    public void setColourChange() {
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
     * This method needs to be provided by an implementation of
     * {@code ChangeListener}. It is called if the value of an
     * {@link ObservableValue} changes.
     * <p>
     * In general, it is considered bad practice to modify the observed value in
     * this method.
     *
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

    public void displayResult(LocalDate date) {
        if (calendar.getNotHolidays().contains(date)) {
            view.showResult(false);
        } else if (calendar.getHolidays().containsKey(date)) {
            view.showResult(true);
        }
    }
}
