package publicholidays.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Creates a window displaying a message for the user (e.g. holiday/not holiday)
 */
public class MessageWindowImpl implements SecondaryWindow {

    private final String message;
    private final String title;

    public MessageWindowImpl(String title, String message) {
        this.message = message;
        this.title = title;
    }

    /**
     * Displays the window with the message for the user
     */
    @Override
    public void display() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);

        VBox pane = new VBox();
        pane.setPadding(new Insets(10, 10, 10, 10));

        Label label = new Label(this.message);
        label.setWrapText(true);
        Button close = new Button("close");
        close.setOnAction(e -> {
            window.close();
        });
        pane.getChildren().addAll(label, close);

        Scene scene = new Scene(pane, 150, 60);
        window.setScene(scene);
        window.show();
    }
}
