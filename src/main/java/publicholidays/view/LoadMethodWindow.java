package publicholidays.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import publicholidays.controller.calendar.CalendarController;
import publicholidays.model.calendar.Calendar;

import java.time.LocalDate;

/**
 * Implements the SecondaryWindow interface to display a window asking for whether the user would like to load data from
 * the database or make a new call to the Holiday API
 */
public class LoadMethodWindow implements SecondaryWindow {

    private LocalDate date;
    private CalendarController controller;

    public LoadMethodWindow(LocalDate date, CalendarController controller) {
        this.date = date;
        this.controller = controller;
    }

    @Override
    public void display() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Cache Method");
        window.setMinWidth(300);

        VBox pane = new VBox();
        pane.setPadding(new Insets(10, 10, 10, 10));

        Label alert = new Label("Information about this date already exists. " +
                "Would you like to load from the database or make a new API call?");
        alert.setWrapText(true);

        Button db = new Button("Database");
        db.setOnAction(e -> {
            controller.displayResult(date);
            window.close();
        });
        Button api = new Button("Make new API call");
        api.setOnAction(e -> {
            controller.getFromAPI(date);
            window.close();
        });

        pane.getChildren().addAll(alert, db, api);
        Scene scene = new Scene(pane, 300, 200);
        window.setScene(scene);
        window.show();
    }
}
