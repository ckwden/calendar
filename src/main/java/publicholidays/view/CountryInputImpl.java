package publicholidays.view;

import javafx.scene.control.TextFormatter;
import publicholidays.model.calendar.Calendar;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CountryInputImpl implements CountryInput {

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

    public Scene getScene() {
        return scene;
    }

    @Override
    public void setScene() {
        TextField countryCode = new TextField();
        countryCode.setPromptText("Enter country code");

        // https://stackoverflow.com/questions/47376362/javafx-force-input-from-textfield-to-be-uppercase/47376468
        countryCode.setTextFormatter(new TextFormatter<>((change -> {
            change.setText(change.getText().toUpperCase());
            return change;
        })));

        Button enter = new Button("Enter");
        enter.setOnAction(e -> {
            if (countryCode.getText() != null && !countryCode.getText().isEmpty()) {
                calendar.setCountry(countryCode.getText());
                CalendarView view = new CalendarView(calendar, 600, 480);
                stage.setScene(view.getScene());
            }
        });
        pane.getChildren().addAll(countryCode, enter);
        stage.setScene(this.scene);
    }
}
