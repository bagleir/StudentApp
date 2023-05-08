import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

import java.util.logging.Logger;
import java.util.logging.Level;


public class MaximizedWindow extends Application {

    private TableView<Student> tableView;
    private ObservableList<Student> studentData;
    private static final Logger LOGGER = Logger.getLogger(MaximizedWindow.class.getName());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Application");
        
        Button button1 = new Button("ADD STUDENT");
        Button button2 = new Button("FILE MANAGING");
        Button button3 = new Button("SEARCH");
        Button button4 = new Button("Update table");

        button2.setOnAction(event -> {
            LOGGER.log(Level.INFO, "Secondary window opened"); // Log the event
            SecondaryWindow.show(); // Call the show() method of SecondaryWindow class
        });

        button4.setOnAction(event -> {
            LOGGER.log(Level.INFO, "Table update"); // Log the event
            for(int i =0;i<App.allStudent.size();i++){
                addStudentToTableIfNotExists(App.allStudent.get(i));
            }
        });

        button1.setOnAction(event -> {
            LOGGER.log(Level.INFO, "Add Student opened"); // Log the event
            AddStudent.show(); // Call the show() method of SecondaryWindow class
        });


        tableView = new TableView<>();
        TableColumn<Student, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Student, String> dobColumn = new TableColumn<>("Date of Birth");
        TableColumn<Student, String> emailColumn = new TableColumn<>("Email");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("date_of_birth"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        idColumn.prefWidthProperty().set(100);
        nameColumn.prefWidthProperty().set(300);
        dobColumn.prefWidthProperty().set(200);
        emailColumn.prefWidthProperty().set(400);

        tableView.getColumns().addAll(idColumn, nameColumn, dobColumn, emailColumn);
        addRowButton();
        studentData = FXCollections.observableArrayList();
        tableView.setItems(studentData);

        Student Jaen = new Student(1, "John Doe", "1990-01-01", "john@example.com");
        App.allStudent.add(Jaen);
        

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(button1, button2, button3,button4, tableView);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);

        primaryStage.show();
        startCommandLineInterface();
    }

    private void addStudentToTableIfNotExists(Student st) {
        // Check if student with the same ID already exists in the table
        boolean studentExists = false;
        for (Student student : studentData) {
            if (student.GetID() == st.GetID()) {
                studentExists = true;
                break;
            }
        }
        if (!studentExists) {
            // Student does not exist, add to the table
            studentData.add(st);
        }
    }
    
    private void addRowButton() {
        TableColumn<Student, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(param -> new TableCell<Student, Void>() {
            private final Button button = new Button("See Grades");
            
            {
                button.setOnAction(event -> {
                    Student student = getTableView().getItems().get(getIndex());
                    if (student != null) {
                        System.out.println("Button clicked for " + student.GetName());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
        tableView.getColumns().add(actionColumn);
    }

    private void startCommandLineInterface() {
        Scanner scanner = new Scanner(System.in);
        new Thread(() -> {
            System.out.println("Welcom on my application");
            while (true) {
                String command = scanner.nextLine().trim();
                if (command.equalsIgnoreCase("exit")) {
                    Platform.exit();
                    System.exit(0);
                } else {
                    // Handle other commands as needed
                    System.out.println("Command not recognized. Please try again.");
                }
            }
        }).start();
    }
}