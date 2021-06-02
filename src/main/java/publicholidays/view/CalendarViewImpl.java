package publicholidays.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import publicholidays.controller.calendar.CalendarController;
import publicholidays.model.calendar.Calendar;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;
import java.time.Month;

public class CalendarViewImpl implements CalendarView {

    private Scene scene;
    private BorderPane layout;
    private Calendar model;
    private CalendarController calendarController;
    private DatePicker datePicker;

    public CalendarViewImpl(Calendar cal, int width, int height) {
        this.model = cal;
        layout = new BorderPane();
        scene = new Scene(layout, width, height);
        datePicker = new DatePicker(LocalDate.now());
        calendarController = new CalendarController(this, cal);
        setScene();
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public void setScene() {
        // https://stackoverflow.com/questions/34681975/javafx-extract-calendar-popup-from-datepicker-only-show-popup/34684268
        DatePickerSkin skin = new DatePickerSkin(datePicker);
        Node content = skin.getPopupContent();
        layout.setCenter(content);

        HBox reportSection = new HBox();
        ComboBox<Month> months = new ComboBox<>();
        months.getItems().addAll(Month.values());

        Button sendReport = new Button("Send report");
        sendReport.setOnAction(e -> {
            if (months.getValue() != null) {
                model.sendReport(months.getValue().getValue());
            } else {
                SecondaryWindow message = new MessageWindowImpl("Error", "Please select a month");
                message.display();
            }
        });

        reportSection.getChildren().addAll(months, sendReport);

        HBox closeWindowSection = new HBox();
        closeWindowSection.setAlignment(Pos.CENTER_RIGHT);
        Button close = new Button("Exit");
        close.setOnAction(e -> scene.getWindow().hide());
        closeWindowSection.getChildren().add(close);

        layout.setBottom(reportSection);
        layout.setTop(closeWindowSection);

        datePicker.valueProperty().addListener(calendarController);
    }

    @Override
    public DatePicker getDatePicker() {
        return this.datePicker;
    }

    @Override
    public void showResult(boolean isHoliday) {
        String title = "Result";
        String message;
        if (isHoliday) {
            message = "This date is a holiday";
        } else {
            message = "This date is not a holiday";
        }
        SecondaryWindow window = new MessageWindowImpl(title, message);
        window.display();
    }

    @Override
    public void requestLoadMethod(Calendar cal, LocalDate date) {
        SecondaryWindow load = new LoadMethodWindow(cal, date, calendarController);
        load.display();
    }
}
