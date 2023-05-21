import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;

import java.util.logging.Logger;
import java.util.logging.Level;

public class AddStudent {

    private static final Logger LOGGER = Logger.getLogger(MaximizedWindow.class.getName());

    // Method to display the "Add Student" window
    public static void show() {
        Stage stage = new Stage();
        stage.setTitle("Add Student");

        // Create text fields and buttons
        TextField textFieldN = new TextField("Name");
        TextField textFieldI = new TextField("ID");
        TextField textFieldD = new TextField("Date of Birth");
        TextField textFieldE = new TextField("Email");
        Button actionButton = new Button("Validate");
        Button closeButton = new Button("Close");

        // Action to be performed when the "Validate" button is clicked
        actionButton.setOnAction(event -> {
            if(Is_Valid(textFieldN.getText(),textFieldI.getText(),textFieldE.getText(),textFieldD.getText())){
                LOGGER.log(Level.INFO, "New Student Saved");
                int id = Integer.parseInt(textFieldI.getText());
                if(id == 0){
                    id = Pick_ID();
                }
                // Create a new Student object and add it to the list of all students
                Student student = new Student(id,textFieldN.getText(),textFieldD.getText(),textFieldE.getText());
                App.allStudent.add(student);
                stage.close();
            }
            else{
                LOGGER.log(Level.INFO, "Invalid student, please try again");
            }
        });

        // Action to be performed when the "Close" button is clicked
        closeButton.setOnAction(event -> stage.close());

        // Create a vertical box layout and add the elements to it
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(actionButton,textFieldN,textFieldI,textFieldD,textFieldE,closeButton);

        // Create a scene with the vertical box layout and set it to the stage
        Scene scene = new Scene(vbox, 200, 200);
        stage.setScene(scene);

        // Show the stage
        stage.show();
    }

    // Method to validate the input fields for a new student
    public static boolean Is_Valid(String Name, String ID, String Email, String Date){
        if (Name.length() <2 || Email.length() <2 || Date.length() <2){
            return false;
        }
        try {
            Integer.parseInt(ID);
        } catch (NumberFormatException e) {
            return false;
        }
        int id = Integer.parseInt(ID);
        if(id == 0){
            return true;
        }
        for (Student student : App.allStudent) {
            if (student.GetID() == id){
                return false;
            }
        }
        return true;
    }

    // Method to generate a unique ID for a new student
    public static int Pick_ID(){
        int id = 0;
        boolean is_unique = false;
        boolean b = true;
        while(!is_unique){
            id = id+1;
            for (Student student : App.allStudent){
                if (student.GetID() != id){
                    b = true;
                }
                else{
                    b = false;
                    break;
                }
            }
            is_unique = b;
        }
        return id;
        
    }
}