package publicholidays.view;

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
        Button enter = new Button("Enter");
        pane.getChildren().addAll(countryCode, enter);
        stage.setScene(this.scene);
    }
}
