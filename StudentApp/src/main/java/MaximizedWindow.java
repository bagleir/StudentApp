import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.scene.layout.Region;

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

    private TableView<Student> tableView; // TableView to display student data
    public boolean searchN = true; // Flag for searching by name
    public boolean searchI = false; // Flag for searching by ID
    public boolean searchD = false; // Flag for searching by date of birth
    public boolean searchE = false; // Flag for searching by email
    private ObservableList<Student> studentData; // ObservableList to hold student data
    private static final Logger LOGGER = Logger.getLogger(MaximizedWindow.class.getName());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Student Application");

        studentData = FXCollections.observableArrayList(); // Create observable list for student data

        Button button1 = new Button("Add a Student"); // Button to add a student
        Button button2 = new Button("File Managing"); // Button for file management
        Button button4 = new Button("Update the Table"); // Button to update the table
        Button buttonclose = new Button("Close"); // Button to close the application
        buttonclose.setStyle("-fx-font-size: 14px; -fx-min-width: 80px;");

        Button buttonN = new Button("By Name"); // Button for searching by name
        Button buttonI = new Button("By ID"); // Button for searching by ID
        Button buttonD = new Button("By Date of Birth"); // Button for searching by date of birth
        Button buttonE = new Button("By Email"); // Button for searching by email

        button2.setOnAction(event -> {
            LOGGER.log(Level.INFO, "Secondary window opened");
            SecondaryWindow.show(); // Open secondary window
        });

        button4.setOnAction(event -> {
            LOGGER.log(Level.INFO, "Table update");
            for (int i = 0; i < App.allStudent.size(); i++) {
                addStudentToTable(App.allStudent.get(i)); // Add all students to the table
            }
            resetFilter(); // Reset the search filter
        });

        button1.setOnAction(event -> {
            LOGGER.log(Level.INFO, "Add Student opened");
            AddStudent.show(); // Open add student window
        });

        // Event handlers for search buttons
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

        TextField searchTextField = new TextField(); // Text field for search input
        searchTextField.setPromptText("Search");
        searchTextField.setPrefWidth(150);

        searchTextField.setStyle("-fx-pref-height: 40px;");

        Button searchButton = new Button("Search"); // Button to perform search

        searchButton.setOnAction(event -> {
            String searchQuery = searchTextField.getText().toLowerCase();
            if (searchN) {
                filterStudentsByName(searchQuery); // Filter students by name
            }
            if (searchI) {
                filterStudentsByID(searchQuery); // Filter students by ID
            }
            if (searchD) {
                filterStudentsByDate(searchQuery); // Filter students by date of birth
            }
            if (searchE) {
                filterStudentsByEmail(searchQuery); // Filter students by email
            }
        });

        tableView = new TableView<>(); // Create TableView
        tableView.setPrefHeight(700);
        TableColumn<Student, Integer> idColumn = new TableColumn<>("ID"); // ID column
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name"); // Name column
        TableColumn<Student, String> dobColumn = new TableColumn<>("Date of Birth"); // Date of Birth column
        TableColumn<Student, String> emailColumn = new TableColumn<>("Email"); // Email column

        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID")); // Bind ID data to ID column
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // Bind name data to Name column
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("date_of_birth")); // Bind date of birth data to Date of Birth column
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email")); // Bind email data to Email column

        idColumn.prefWidthProperty().set(100); // Set preferred width for ID column
        nameColumn.prefWidthProperty().set(300); // Set preferred width for Name column
        dobColumn.prefWidthProperty().set(200); // Set preferred width for Date of Birth column
        emailColumn.prefWidthProperty().set(400); // Set preferred width for Email column

        tableView.getColumns().addAll(idColumn, nameColumn, dobColumn, emailColumn);
        addRowButton(); // Add "See Grades" button column
        addRowButton2(); // Add "Remove" button column
        studentData = FXCollections.observableArrayList();
        tableView.setItems(studentData);

        Student Jaen = new Student(1, "John", "1990-01-01", "john@example.com");
        App.allStudent.add(Jaen);
        for (int i = 0; i < App.allStudent.size(); i++) {
            addStudentToTable(App.allStudent.get(i)); // Add all students to the table
        }
        resetFilter(); // Reset the search filter

        HBox.setHgrow(searchTextField, Priority.ALWAYS);
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 10));

        HBox searchBox = new HBox(10, searchTextField, searchButton);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        BorderPane root = new BorderPane(); // Create BorderPane for layout
        root.setPadding(new Insets(10));
        root.setTop(searchBox);
        BorderPane.setAlignment(searchBox, Pos.TOP_LEFT);
        BorderPane.setMargin(searchBox, new Insets(10, 0, 10, 0));
        root.setCenter(tableView);
        BorderPane.setMargin(tableView, new Insets(0, 0, 10, 0));
        root.setBottom(buttonclose);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        spacer.setMaxHeight(100);
        spacer.setMaxWidth(100);

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(buttonN, buttonE, buttonI, buttonD); // Add search buttons
        buttonBox.setAlignment(Pos.CENTER);
        root.setRight(buttonBox);

        Region spacer2 = new Region();
        VBox.setVgrow(spacer2, Priority.ALWAYS);
        spacer2.setMaxHeight(40);
        spacer2.setMaxWidth(40);

        HBox buttonsBox2 = new HBox(10);
        buttonsBox2.getChildren().addAll(button1, button2, button4); // Add action buttons
        buttonsBox2.setAlignment(Pos.CENTER);
        buttonsBox2.setPadding(new Insets(10));
        root.setRight(buttonsBox2);

        HBox closeBox = new HBox(10);
        closeBox.getChildren().addAll(buttonclose);
        closeBox.setAlignment(Pos.CENTER);
        closeBox.setPadding(new Insets(10));
        root.setRight(closeBox);

        VBox rootBox = new VBox();
        rootBox.getChildren().addAll(spacer, searchBox, buttonBox, spacer2, buttonsBox2, tableView, closeBox); // Add all elements to rootBox

        Scene scene = new Scene(rootBox, 800, 600); // Create the scene
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true); // Maximize the window

        buttonclose.setOnAction(event -> {
            primaryStage.close(); // Close the primary stage
            Platform.exit(); // Exit the JavaFX application
            System.exit(0); // Terminate the Java Virtual Machine
        });

        primaryStage.show(); // Show the primary stage
        startCommandLineInterface(); // Start the command-line interface
    }

    private void addStudentToTable(Student st) {
        boolean studentExists = false;
        for (Student student : studentData) {
            if (student.GetID() == st.GetID()) { // Check if student with the same ID exists
                studentExists = true;
                break;
            }
        }
        if (!studentExists) {
            studentData.add(st); // Add the student to the table if it doesn't already exist
        }
    }

    private void addRowButton() {
        TableColumn<Student, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(param -> new TableCell<Student, Void>() {
            private final Button button = new Button("See Grades"); // Create "See Grades" button
            {
                button.setOnAction(event -> {
                    Student student = getTableRow().getItem();
                    if (student != null) {
                        openNewPage(student); // Open new page for the selected student
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button); // Set the button as the graphic if the cell is not empty
                }
            }
        });
        tableView.getColumns().add(actionColumn); // Add the action column to the table
    }

    private void addRowButton2() {
        TableColumn<Student, Void> actionColumn = new TableColumn<>("Remove");
        actionColumn.setCellFactory(param -> new TableCell<Student, Void>() {
            private final Button button = new Button("Remove"); // Create "Remove" button
            {
                button.setOnAction(event -> {
                    Student student = getTableRow().getItem();
                    if (student != null) {
                        studentData.remove(student); // Remove the student from the table
                        tableView.getItems().remove(student);
                        App.allStudent.remove(student); // Remove the student from the allStudent list
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button); // Set the button as the graphic if the cell is not empty
                }
            }
        });
        tableView.getColumns().add(actionColumn); // Add the action column to the table
    }// Method to open a new page for displaying student grades
private void openNewPage(Student student) {
    Stage newStage = new Stage();
    newStage.setTitle("Student Grades: " + student.GetName());

    // Create a TableView to display grades
    TableView<StudentGrades<Double>> gradesTable = new TableView<>();
    TableColumn<StudentGrades<Double>, Double> gradeColumn = new TableColumn<>("Grade");
    gradeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().GetGrade()));

    // Create an ObservableList to store grades data
    ObservableList<StudentGrades<Double>> gradesData = FXCollections.observableArrayList();

    // Retrieve grades for the student and add them to the ObservableList
    ListGrades grades = student.GetGrades();
    for (int i = 0; i < grades.NumberGrade(); i++) {
        StudentGrades<Double> grade = grades.GetI(i);
        gradesData.add(grade);
    }

    // Set the data and columns in the TableView
    gradesTable.setItems(gradesData);
    gradesTable.getColumns().add(gradeColumn);

    // Create buttons, text field, and configure layout
    Button addButton = new Button("Add Grade");
    Button fileButton = new Button("Generate a file");
    Button closeButton = new Button("Close");
    TextField gradeTextField = new TextField();

    VBox vbox = new VBox(10);
    vbox.getChildren().addAll(gradesTable, gradeTextField, addButton, fileButton, closeButton);
    vbox.setPadding(new Insets(10));

    Scene scene = new Scene(vbox, 400, 300);
    newStage.setScene(scene);
    newStage.show();

    // Event handlers for buttons
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
        // Generate a file with student information
        String filename = App.Createfile(student);
        try {
            App.Builtin(filename, student);
        } catch (IOException e) {
            e.printStackTrace();
        }
    });

    closeButton.setOnAction(event -> {
        // Close the new stage
        newStage.close();
    });
}

// Method to validate the input grade
private boolean isValidGrade(String input) {
    String gradePattern = "\\d{1,2}([.]\\d)?|100([.]0)?";
    return Pattern.matches(gradePattern, input);
}

// Method to start the command-line interface for additional functionality
private void startCommandLineInterface() {
    Scanner scanner = new Scanner(System.in);
    new Thread(() -> {
        System.out.println("Welcome to my application");
        while (true) {
            String command = scanner.nextLine().trim();
            if (command.equalsIgnoreCase("exit")) {
                Platform.exit();
                System.exit(0);
            } else if (startsWithIgnoreCase(command, "add grade")) {
                // Process command to add a grade
                String[] parts = command.split(" ");
                if (parts.length == 4) {
                    int studentID = Integer.parseInt(parts[2]);
                    Student st = filterStudentByIDInteger(studentID);
                    if (isValidGrade(parts[3])) {
                        double gradeValue = Double.parseDouble(parts[3]);
                        StudentGrades<Double> newGrade = new StudentGrades<>(gradeValue);
                        st.AddGrade(newGrade);
                    } else {
                        LOGGER.log(Level.INFO, "Invalid Grade: Please enter a valid grade (0.0-100.0).");
                    }
                } else {
                    LOGGER.log(Level.INFO, "Invalid command. Please use the format: add grade <studentID> <grade>");
                }
            } else if (startsWithIgnoreCase(command, "add student")) {
                // Process command to add a student
                String[] parts = command.split(" ");
                if (parts.length == 6) {
                    if (AddStudent.Is_Valid(parts[3], parts[2], parts[5], parts[4])) {
                        LOGGER.log(Level.INFO, "New Student Saved");
                        int id = Integer.parseInt(parts[2]);
                        if (id == 0) {
                            id = AddStudent.Pick_ID();
                        }
                        Student student = new Student(id, parts[3], parts[4], parts[5]);
                        App.allStudent.add(student);
                    } else {
                        LOGGER.log(Level.INFO, "Invalid student, please try again");
                    }
                } else {
                    LOGGER.log(Level.INFO, "Invalid command. Please use the format: add student <studentID> <studentName> <studentDate> <studentEmail>");
                }
            } else {
                LOGGER.log(Level.INFO, "Command not recognized. Please try again.");
            }
        }
    }).start();
}

// Method to filter students by name
private void filterStudentsByName(String searchQuery) {
    List<Student> filteredList = studentData.stream()
            .filter(student -> student.GetName().toLowerCase().contains(searchQuery))
            .collect(Collectors.toList());

    tableView.setItems(FXCollections.observableArrayList(filteredList));
}

// Method to filter students by ID
private void filterStudentsByID(String searchQuery) {
    List<Student> filteredList = studentData.stream()
            .filter(student -> String.valueOf(student.GetID()).toLowerCase().contains(searchQuery))
            .collect(Collectors.toList());

    tableView.setItems(FXCollections.observableArrayList(filteredList));
}

// Method to filter students by date
private void filterStudentsByDate(String searchQuery) {
    List<Student> filteredList = studentData.stream()
            .filter(student -> student.GetDate().toLowerCase().contains(searchQuery))
            .collect(Collectors.toList());

    tableView.setItems(FXCollections.observableArrayList(filteredList));
}

// Method to filter students by email
private void filterStudentsByEmail(String searchQuery) {
    List<Student> filteredList = studentData.stream()
            .filter(student -> student.GetEmail().toLowerCase().contains(searchQuery))
            .collect(Collectors.toList());

    tableView.setItems(FXCollections.observableArrayList(filteredList));
}

// Method to reset the filter and display all students
private void resetFilter() {
    tableView.setItems(studentData);
}

// Method to filter a student based on ID and return the student object
private Student filterStudentByIDInteger(int studentID) {
    List<Student> filteredStudents = studentData.stream()
            .filter(student -> student.GetID() == studentID)
            .collect(Collectors.toList());

    long count = filteredStudents.size();
        if (count > 1) {
           LOGGER.log(Level.INFO, "Multiple students found with the same ID.");
        return null;
    } else if (count == 0) {
        LOGGER.log(Level.INFO, "No student found with the given ID.");
        return null;
    }

    return filteredStudents.get(0);
}

    // Method to check if a string starts with a specific prefix (case-insensitive)
    private boolean startsWithIgnoreCase(String str, String prefix) {
        if (str.length() < prefix.length()) {
            return false;
        }
        return str.regionMatches(true, 0, prefix, 0, prefix.length());
    }
}