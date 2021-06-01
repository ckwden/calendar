package publicholidays.controller.calendar;

import publicholidays.model.calendar.Calendar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import publicholidays.view.MessageWindow;
import publicholidays.view.MessageWindowImpl;

import java.time.LocalDate;

public class CalendarController implements ChangeListener<LocalDate> {

    private DatePicker picker;
    private Calendar calendar;

    public CalendarController(DatePicker picker, Calendar calendar) {
        this.picker = picker;
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
        picker.setDayCellFactory(factory);
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
            MessageWindow window = new MessageWindowImpl("Error", "Must select date in current year");
            window.display();
        }
        boolean isHoliday = calendar.isHoliday(newValue);
        String title = "Result";
        String message;
        if (isHoliday) {
            message = "This date is a holiday";
        } else {
            message = "This date is not a holiday";
        }
        MessageWindow window = new MessageWindowImpl(title, message);
        window.display();
    }

}
