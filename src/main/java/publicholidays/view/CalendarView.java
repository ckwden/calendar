package publicholidays.view;

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

public class CalendarView {

    private Scene scene;
    private BorderPane layout;
    private Calendar model;
    private CalendarController calendarController;
    private DatePicker datePicker;

    public CalendarView(Calendar cal, int width, int height) {
        this.model = cal;
        layout = new BorderPane();
        scene = new Scene(layout, width, height);
        datePicker = new DatePicker(LocalDate.now());
        calendarController = new CalendarController(datePicker, cal);
        setScene();
    }

    public Scene getScene() {
        return this.scene;
    }

    public void setScene() {
        DatePickerSkin skin = new DatePickerSkin(datePicker);
        Node content = skin.getPopupContent();
        layout.setCenter(content);

        HBox reportSection = new HBox();
        ComboBox<Month> months = new ComboBox<>();
        months.getItems().addAll(Month.values());

        Button sendReport = new Button("Send report");
        reportSection.getChildren().addAll(months, sendReport);
        layout.setBottom(reportSection);

        datePicker.valueProperty().addListener(calendarController);
    }
}
