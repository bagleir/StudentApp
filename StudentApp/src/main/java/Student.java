import javafx.beans.property.*;

public class Student {

    private final IntegerProperty ID;
    private final StringProperty date_of_birth;
    private final StringProperty name;
    private final StringProperty email;
    private final ListGrades grades;

    public Student(int ID, String name, String date_of_birth, String email) {
        this.ID = new SimpleIntegerProperty(ID);
        this.date_of_birth = new SimpleStringProperty(date_of_birth);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.grades = new ListGrades();
    }

    public int GetID() {
        return ID.get();
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