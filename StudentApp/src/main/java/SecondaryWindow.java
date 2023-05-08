import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class SecondaryWindow {

    public static void show() {
        Stage stage = new Stage();
        stage.setTitle("File interaction");

        Button actionButton = new Button("Add a Student with grades");
        Button closeButton = new Button("Close");

        actionButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select a File");

            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                System.out.println("Selected File: " + selectedFile.getAbsolutePath());
            }
        });

        // Define the action for the close button
        closeButton.setOnAction(event -> stage.close());

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(actionButton, closeButton);

        Scene scene = new Scene(vbox, 200, 200);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }
}