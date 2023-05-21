public class StudentGrades<G> {
    private G Grade;

    // Constructor to initialize the grade
    public StudentGrades(G grade) {
        this.Grade = grade;
    }

    // Getter method to retrieve the grade
    public G GetGrade() {
        return this.Grade;
    }
}
