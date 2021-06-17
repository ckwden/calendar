package publicholidays.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import publicholidays.controller.calendar.CalendarController;
import publicholidays.model.calendar.Calendar;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;

import java.time.LocalDate;
import java.time.Month;

/**
 * Implements the CalendarView interface
 */
public class CalendarViewImpl implements CalendarView {

    private Scene scene;
    private BorderPane layout;
    private CalendarController calendarController;
    private DatePicker datePicker;
    private Node skinContent;

    public CalendarViewImpl(Calendar cal, int width, int height) {
        layout = new BorderPane();
        scene = new Scene(layout, width, height);
        datePicker = new DatePicker(LocalDate.now());
        calendarController = new CalendarController(this, cal);
        skinContent = null;
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
        skinContent = skin.getPopupContent();
        layout.setCenter(skinContent);

        HBox reportSection = new HBox();
        ComboBox<Month> months = new ComboBox<>();
        months.getItems().addAll(Month.values());

        Button sendReport = new Button("Send report");
        sendReport.setOnAction(e -> {
            if (months.getValue() != null) {
                calendarController.sendReport(months.getValue().getValue());
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

    /**
     * Starts a short animation to cause the window background to blink
     */
    @Override
    public void blink() {
        Timeline blink = new Timeline(new KeyFrame(Duration.ZERO, event -> {
                    layout.setBackground(new Background(
                            new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                    skinContent.setStyle("-fx-background-color: #808080;");
                }),
                new KeyFrame(Duration.seconds(0.1), event -> {
                    layout.setBackground(new Background(
                            new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                    skinContent.setStyle("-fx-background-color: #FFFFFF;");
                }));
        blink.setAutoReverse(true);
        blink.setCycleCount(3);
        blink.play();
    }

    @Override
    public DatePicker getDatePicker() {
        return this.datePicker;
    }
}
