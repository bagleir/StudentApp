import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.util.logging.Logger;
import java.util.logging.Level;


public class MaximizedWindow extends Application {

    private TableView<Student> tableView;
    public boolean searchN = true;
    public boolean searchI = false;
    public boolean searchD = false;
    public boolean searchE = false;  
    private ObservableList<Student> studentData;
    private static final Logger LOGGER = Logger.getLogger(MaximizedWindow.class.getName());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Student Application");

        studentData = FXCollections.observableArrayList();
        
        Button button1 = new Button("ADD STUDENT");
        Button button2 = new Button("FILE MANAGING");
        Button button4 = new Button("Update table");
        Button buttonclose = new Button("Close");

        Button buttonN = new Button("By Name");
        Button buttonI = new Button("By ID");
        Button buttonD = new Button("By Date");
        Button buttonE = new Button("By email");

        button2.setOnAction(event -> {
            LOGGER.log(Level.INFO, "Secondary window opened"); // Log the event
            SecondaryWindow.show(); // Call the show() method of SecondaryWindow class
        });

        button4.setOnAction(event -> {
            LOGGER.log(Level.INFO, "Table update"); // Log the event
            for(int i =0;i<App.allStudent.size();i++){
                addStudentToTableIfNotExists(App.allStudent.get(i));
            }
            resetFilter();
        });

        button1.setOnAction(event -> {
            LOGGER.log(Level.INFO, "Add Student opened"); // Log the event
            AddStudent.show(); // Call the show() method of SecondaryWindow class
        });

        buttonN.setOnAction(event -> {
            searchN = true;
            searchI = false;
            searchD = false;
            searchE = false;
        });
        buttonI.setOnAction(event -> {
            searchN = false;
            searchI = true;
            searchD = false;
            searchE = false;
        });
        buttonD.setOnAction(event -> {
            searchN = false;
            searchI = false;
            searchD = true;
            searchE = false;
        });
        buttonE.setOnAction(event -> {
            searchN = false;
            searchI = false;
            searchD = false;
            searchE = true;
        });

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search");
        Button searchButton = new Button("Search");
        
        searchButton.setOnAction(event -> {
            String searchQuery = searchTextField.getText().toLowerCase();
            if (searchN){
                filterStudentsByName(searchQuery);
            }
            if (searchI){
                filterStudentsByID(searchQuery);
            }
            if (searchD){
                filterStudentsByDate(searchQuery);
            }
            if (searchE){
                filterStudentsByEmail(searchQuery);
            }

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

        Student Jaen = new Student(1, "John", "1990-01-01", "john@example.com");
        App.allStudent.add(Jaen);
        

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(searchTextField, searchButton,button1, buttonN,buttonI,buttonD,buttonE,button2,button4, tableView,buttonclose);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);

        buttonclose.setOnAction(event -> {
            primaryStage.close();
            Platform.exit();
            System.exit(0);
        });

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
                    Student student = getTableRow().getItem();
                    if (student != null) {
                        openNewPage(student);
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

    private void openNewPage(Student student) {
        Stage newStage = new Stage();
        newStage.setTitle("Student Grades: " + student.GetName());

        TableView<StudentGrades<Double>> gradesTable = new TableView<>();
        TableColumn<StudentGrades<Double>, Double> gradeColumn = new TableColumn<>("Grade");
        gradeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().GetGrade()));

        ObservableList<StudentGrades<Double>> gradesData = FXCollections.observableArrayList();

        ListGrades grades = student.GetGrades();
        for (int i = 0; i < grades.NumberGrade(); i++) {
            StudentGrades<Double> grade = grades.GetI(i);
            gradesData.add(grade);
        }

        gradesTable.setItems(gradesData);
        gradesTable.getColumns().add(gradeColumn);

        Button addButton = new Button("Add Grade");
        Button fileButton = new Button("Generate a file");
        Button closeButton = new Button("Close");
        TextField gradeTextField = new TextField();

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(gradesTable,gradeTextField, addButton,fileButton, closeButton);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 400, 300);
        newStage.setScene(scene);
        newStage.show();

        addButton.setOnAction(event -> {
            String input = gradeTextField.getText().trim();
            if (isValidGrade(input)) {
                double gradeValue = Double.parseDouble(input);
                StudentGrades<Double> newGrade = new StudentGrades<>(gradeValue);
                gradesData.add(newGrade);
                student.AddGrade(newGrade);
                gradeTextField.clear();
            } else {
                LOGGER.log(Level.INFO, "Invalid Grade : Please enter a valid grade (0-100).");
            }
        });

        fileButton.setOnAction(event -> {
            String filename = App.Createfile(student);
            try {
                App.Builtin(filename,student);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        closeButton.setOnAction(event -> {
            newStage.close();
        });
    }
    
    private boolean isValidGrade(String input) {
        // Verify that the input is a valid grade (0-100)
        // You can customize the validation logic based on your requirements
        // Here, we use a regular expression pattern to match a number between 0 and 100
        String gradePattern = "\\d{1,2}([.]\\d)?|100([.]0)?";
        return Pattern.matches(gradePattern, input);
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
                    System.out.println("Command not recognized. Please try again.");
                }
            }
        }).start();
    }

    private void filterStudentsByName(String searchQuery) {
        List<Student> filteredList = studentData.stream()
                .filter(student -> student.GetName().toLowerCase().contains(searchQuery))
                .collect(Collectors.toList());

        tableView.setItems(FXCollections.observableArrayList(filteredList));
    }
    
    private void filterStudentsByID(String searchQuery) {
        List<Student> filteredList = studentData.stream()
                .filter(student -> String.valueOf(student.GetID()).toLowerCase().contains(searchQuery))
                .collect(Collectors.toList());

        tableView.setItems(FXCollections.observableArrayList(filteredList));
    }

    private void filterStudentsByDate(String searchQuery) {
        List<Student> filteredList = studentData.stream()
                .filter(student -> student.GetDate().toLowerCase().contains(searchQuery))
                .collect(Collectors.toList());

        tableView.setItems(FXCollections.observableArrayList(filteredList));
    }

    private void filterStudentsByEmail(String searchQuery) {
        List<Student> filteredList = studentData.stream()
                .filter(student -> student.GetEmail().toLowerCase().contains(searchQuery))
                .collect(Collectors.toList());

        tableView.setItems(FXCollections.observableArrayList(filteredList));
    }
    private void resetFilter() {
        tableView.setItems(studentData);
    }
}