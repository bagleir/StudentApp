import java.util.*;

public class ListGrades {
    private List<StudentGrades<Double>> gradelist;   // List to store student grades

    public ListGrades() {
        this.gradelist = new ArrayList<>();          // Initialize the grade list as an empty ArrayList
    }

    public void AddGrade(StudentGrades<Double> grade) {
        this.gradelist.add(grade);                   // Add a grade to the grade list
    }

    public void RemoveGrade(StudentGrades<Double> grade) {
        this.gradelist.remove(grade);                // Remove a grade from the grade list
    }

    public int NumberGrade() {
        return this.gradelist.size();                // Return the number of grades in the grade list
    }

    public StudentGrades<Double> GetI(int i) {
        return this.gradelist.get(i);                // Get the grade at index i from the grade list
    }

    public StudentGrades<Double> GetGrade(int i) {
        return gradelist.get(i);                     // Get the grade at index i from the grade list
    }
}