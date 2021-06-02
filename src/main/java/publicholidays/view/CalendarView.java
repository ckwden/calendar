package publicholidays.view;

import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import publicholidays.model.calendar.Calendar;

import java.time.LocalDate;

public interface CalendarView {

    Scene getScene();

    void setScene();

    DatePicker getDatePicker();

    void showResult(boolean isHoliday);

    void requestLoadMethod(Calendar cal, LocalDate date);
}
