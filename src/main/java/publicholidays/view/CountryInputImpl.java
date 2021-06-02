package publicholidays.view;

import javafx.scene.control.ComboBox;
import publicholidays.model.calendar.Calendar;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Locale;

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

        Button enter = new Button("Enter");
        enter.setOnAction(e -> {
            if (codes.getValue() != null) {
                calendar.setCountry(codes.getValue());
                CalendarViewImpl view = new CalendarViewImpl(calendar, 600, 480);
                stage.setScene(view.getScene());
            }
        });
        pane.getChildren().addAll(codes, enter);
        stage.setScene(this.scene);
    }
}
