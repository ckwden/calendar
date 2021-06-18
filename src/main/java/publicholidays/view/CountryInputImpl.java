package publicholidays.view;

import javafx.scene.control.ComboBox;
import publicholidays.model.calendar.Calendar;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Locale;

/**
 * Implements the SecondaryWindow interface to ask for which country and threshold count the user would query on
 * for the Holiday API upon start up of the application
 */
public class CountryInputImpl implements SecondaryWindow {

    private Scene scene;
    private HBox pane;
    private Stage stage;
    private Calendar calendar;

    public CountryInputImpl(Stage primary, int width, int height, Calendar calendar) {
        pane = new HBox();
        scene = new Scene(pane, width, height);
        stage = primary;
        this.calendar = calendar;
    }

    @Override
    public void display() {
        ComboBox<String> codes = new ComboBox<>();
        codes.getItems().addAll(Locale.getISOCountries());
        codes.setPromptText("Country code");

        ComboBox<Integer> count = new ComboBox<>();
        count.getItems().addAll(1,2,3,4,5);
        count.setPromptText("Threshold holiday count");

        Button enter = new Button("Enter");
        enter.setOnAction(e -> {
            if (codes.getValue() != null && count.getValue() != null) {
                calendar.setCountry(codes.getValue());
                calendar.setThresholdCount(count.getValue());
                CalendarViewImpl view = new CalendarViewImpl(calendar, 600, 480);
                stage.setScene(view.getScene());
            }
        });
        pane.getChildren().addAll(codes, count, enter);
        stage.setScene(this.scene);
    }
}
