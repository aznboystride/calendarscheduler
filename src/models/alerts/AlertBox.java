package models.alerts;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    public AlertBox(String title) {
        display(title);
    }
    private void display(String title) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(350);
        window.setMinHeight(100);

        Button btnClose = new Button("Close");
        btnClose.setOnAction(event -> {
            window.close();
        });

        VBox vBox = new VBox(10);

        vBox.getChildren().add(btnClose);
        vBox.setAlignment(Pos.CENTER);

        window.setScene(new Scene(vBox));
        window.show();
    }
}