package publicholidays.controller.calendar;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import publicholidays.model.JsonManager;
import publicholidays.model.calendar.Calendar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import publicholidays.view.*;

import java.time.LocalDate;

/**
 * Acts as the intermediate object between the Calendar model and its view
 */
public class CalendarController implements ChangeListener<LocalDate> {

    private CalendarView view;
    private Calendar calendar;
    private MessengerService messengerThread;
    private HolidayService holidayThread;
    private DatabaseService dbThread;

    public CalendarController(CalendarView view, Calendar calendar) {
        this.view = view;
        this.calendar = calendar;
        this.messengerThread = new MessengerService();
        this.holidayThread = new HolidayService();
        this.dbThread = new DatabaseService();
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
        else {
            getFromDatabase(newValue);
        }
    }

    /**
     * Creates a new window to display the result of clicking on a date cell
     * @param date the date of the cell that has been clicked
     */
    public void displayResult(LocalDate date) {
        String title = "Result";
        String message = "Error";
        if (calendar.getNotHolidays().contains(date)) {
            message = "This date is not a holiday";
        } else if (calendar.getHolidays().containsKey(date)) {
            if (calendar.getHolidays().get(date).size() > calendar.getThresholdCount()) {
                blinkWindow();
            }
            message = "This date is a holiday";
        }
        new MessageWindowImpl(title, message).display();
    }

    /**
     * Uses another thread to send the report through the Twilio API
     * @param month the month the report is based on
     */
    public void sendReport(int month) {
        messengerThread.month = month;
        messengerThread.restart();
    }

    /**
     * Uses another thread to make the call to the Holiday API to retrieve information about the date
     * @param date the date to be used for the query
     */
    public void getFromAPI(LocalDate date) {
        holidayThread.clickedDate = date;
        holidayThread.restart();
    }

    /**
     * Uses another thread to retrieve information about the date from the database
     * @param date the date to be used for the query
     */
    private void getFromDatabase(LocalDate date) {
        dbThread.clickedDate = date;
        dbThread.restart();
    }

    private void blinkWindow() {
        view.blink();
    }

    /**
     * Creates a new window to ask how the user would like to load the information if it already exists in the database
     * @param date the date picked by the user
     */
    public void requestLoadMethod(LocalDate date) {
        new LoadMethodWindow(date, this).display();
    }

    /**
     * Creates a new Task allowing for calls to the Holiday API to run on a separate thread to the GUI
     */
    private class HolidayService extends Service<Void> {

        private LocalDate clickedDate;

        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    calendar.getFromAPI(clickedDate);
                    return null;
                }

                @Override
                protected void succeeded() {
                    String response = calendar.getPublicHoliday().getResponse();
                    if (response.contains("error")) {
                        String message = JsonManager.getHolidayErrorMessage(response);
                        new MessageWindowImpl("Error", message).display();
                    } else {
                        calendar.determineHoliday(clickedDate, calendar.getPublicHoliday().getHoliday(clickedDate));
                        displayResult(clickedDate);
                    }
                }
            };
        }
    }

    /**
     * Creates a new Task allowing for reports sent via Twilio to be run on a separate thread to the GUI
     */
    private class MessengerService extends Service<Void> {

        private int month;

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    calendar.sendReport(month);
                    return null;
                }

                @Override
                protected void succeeded() {
                    String message = JsonManager.getMessengerMessage(calendar.getMessenger().getResponse());
                    if (message == null) {
                        new MessageWindowImpl("Success", "Report sent").display();
                    } else {
                        new MessageWindowImpl("Error", message).display();
                    }
                }
            };
        }
    }

    /**
     * Creates a new Task allowing for database caching to be done on a separate thread to the GUI
     */
    private class DatabaseService extends Service<Boolean> {

        private LocalDate clickedDate;

        @Override
        protected Task<Boolean> createTask() {
            return new Task<>() {
                @Override
                protected Boolean call() throws Exception {
                    return calendar.getFromDatabase(clickedDate);
                }
            };
        }

        @Override
        protected void succeeded() {
            boolean inDatabase = getValue();
            if (inDatabase) {
                requestLoadMethod(clickedDate);
            } else {
                getFromAPI(clickedDate);
            }
        }
    }
}