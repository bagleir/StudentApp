import javafx.beans.property.*;

public class Student {

    // Properties for storing student data
    private final IntegerProperty ID;              // Student ID
    private final StringProperty date_of_birth;    // Date of birth
    private final StringProperty name;             // Name
    private final StringProperty email;            // Email

    private final ListGrades grades;               // List of grades

    // Constructor for initializing student data
    public Student(int ID, String name, String date_of_birth, String email) {
        this.ID = new SimpleIntegerProperty(ID);
        this.date_of_birth = new SimpleStringProperty(date_of_birth);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.grades = new ListGrades();
    }

    // Getter methods for accessing student data

    public int GetID() {
        return ID.get();
    }

    public ListGrades GetGrades() {
        return grades;
    }

    public IntegerProperty IDProperty() {
        return ID;
    }

    public String GetDate() {
        return date_of_birth.get();
    }

    public StringProperty date_of_birthProperty() {
        return date_of_birth;
    }

    public String GetName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String GetEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    // Methods for managing grades

    public void AddGrade(StudentGrades<Double> grade) {
        grades.AddGrade(grade);
    }

    public void RemoveGrade(StudentGrades<Double> grade) {
        grades.RemoveGrade(grade);
    }

    public int NumberGrade() {
        return grades.NumberGrade();
    }

    public StudentGrades<Double> GetI(int i) {
        return grades.GetI(i);
    }
}