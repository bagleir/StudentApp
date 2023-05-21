import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;


public class SecondaryWindow {

    public static void show() {
        Stage stage = new Stage();
        stage.setTitle("File interaction");

        Button actionButton1 = new Button("Add a Student with grades");
        Button actionButton2 = new Button("Add a lot of Student");
        Button actionButton3 = new Button("Generate a rapport");
        Button closeButton = new Button("Close");

        actionButton1.setOnAction(event -> {
            // Create a FileChooser object
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select a File");

            // Show the file chooser dialog and get the selected file
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                System.out.println("Selected File : " + selectedFile.getAbsolutePath());
                try {
                    // Read the student information from the selected file
                    App.ReadFileStudent(selectedFile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        actionButton2.setOnAction(event -> {
            // Create a FileChooser object
            FileChooser fileChooser2 = new FileChooser();
            fileChooser2.setTitle("Select a File");

            // Show the file chooser dialog and get the selected file
            File selectedFile2 = fileChooser2.showOpenDialog(stage);
            if (selectedFile2 != null) {
                System.out.println("Selected File : " + selectedFile2.getAbsolutePath());
                try {
                    // Read and add multiple students from the selected file
                    App.ReadStudentsAdd(selectedFile2.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        actionButton3.setOnAction(event -> {
            // Generate the student info file
            App.generateStudentInfoFile();
        });

        closeButton.setOnAction(event -> stage.close());

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(actionButton1, actionButton2, actionButton3, closeButton);

        Scene scene = new Scene(vbox, 200, 200);
        stage.setScene(scene);

        stage.show();
    }
}